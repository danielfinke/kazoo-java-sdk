package org.finke.kazoo_java_sdk.auth;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Daniel Finke
 * @since 2016-08-08
 */
public class UserAndPasswordAuthToken extends AbstractAuthToken {
    protected String username;
    protected String md5;
    protected String accountId;
    protected String accountName = null;

    @SuppressWarnings("unused")
    private UserAndPasswordAuthToken() {}

    /**
     * Create a new auth token instance from username and password
     * @param username The auth username
     * @param password The auth password
     * @param accountIdOrName Account ID or Account Name to authenticate in
     * @param authUrl The URL to PUT auth credentials
     */
    public UserAndPasswordAuthToken(String username, String password, String accountIdOrName, String authUrl) {
        this.username = username;
        try {
            md5 = md5Encode(username + ":" + password);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException ex) {}
        accountId = accountIdOrName;
        this.authUrl = authUrl;
    }

    /**
     * Get the current auth token, generating it if it has not already been
     * @return The token to use for requests
     * @throws IOException if the token auth URL is not a valid URL or a
     *     connection cannot be established to the server
     */
    @Override
    public String getToken() throws IOException {
        if(authToken == null) {
            return getNewToken();
        }

        return authToken;
    }

    /**
     * Request a new auth token for requests. Replaces the previous token
     * @return The new token to use for requests
     * @throws IOException if the token auth URL is not a valid URL or a
     *     connection cannot be established to the server
     */
    @Override
    public String getNewToken() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)(new URL(authUrl).openConnection());
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        urlConnection.setRequestMethod("PUT");
        urlConnection.setDoOutput(true);

        OutputStream out = urlConnection.getOutputStream();
        out.write(buildTokenJson().getBytes("UTF-8"));
        out.close();

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

    }

    public String getUsername() { return username; }
    public String getMd5() { return md5; }
    public String getAccountId() { return accountId; }
    public String getAccountName() { return accountName; }

    /**
     * Return the md5 of a supplied string
     * @param s Produce md5 of this string
     * @return The md5 of s
     * @throws NoSuchAlgorithmException if MD5 is not a valid encryption
     *     algorithm. Should not be thrown
     * @throws UnsupportedEncodingException if UTF-8 is not a valid string
     *     encoding. Should not be thrown
     */
    private static String md5Encode(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(s.getBytes("UTF-8"));
        StringBuilder builder = new StringBuilder();
        for(byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * Create a JSON auth payload string from this instance
     * @return JSON auth payload string
     */
    private String buildTokenJson() {
        UserAndPasswordJson jsonObj = new UserAndPasswordJson(md5);

        // Use accountId first, try accountName second
        if(accountName == null) {
            jsonObj.setAccountId(accountId);
        }
        else {
            jsonObj.setAccountName(accountName);
        }

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson.toJson(jsonObj);
    }

    /**
     * Representation of JSON required for auth request payload
     */
    private class UserAndPasswordJson {
        @SerializedName("credentials") private final String md5;
        @SuppressWarnings("unused")
        private String accountId = null;
        @SuppressWarnings("unused")
        private String accountName = null;

        /**
         * Create a new JSON-serializable token payload
         * @param md5 Md5 credentials for auth
         */
        public UserAndPasswordJson(String md5) {
            this.md5 = md5;
        }

        /**
         * Set the account ID in the payload
         * @param accountId Account ID to authenticate in
         */
        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        /**
         * Set the account name in the payload
         * @param accountName Account Name to authenticate in
         */
        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }
    }
}
