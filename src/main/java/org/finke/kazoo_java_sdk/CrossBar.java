package org.finke.kazoo_java_sdk;

import org.finke.kazoo_java_sdk.auth.AuthTokenInterface;
import org.finke.kazoo_java_sdk.entity.Account;
import org.finke.kazoo_java_sdk.net.Scheme;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Finke
 * @since 2016-08-06
 */
public class CrossBar {
    protected static final Scheme DEFAULT_SCHEME = Scheme.HTTP;
    protected static final int DEFAULT_PORT = 8000;
    protected static final String INDEX_URL = "";

    protected Scheme scheme = DEFAULT_SCHEME;
    protected String host;
    protected int port = DEFAULT_PORT;
    protected int apiVersion = 1;
    protected AuthTokenInterface authToken;

    /**
     * Create a CrossBar instance that connects to the given host on the
     * default CrossBar port
     * @param host Server hostname or IP address
     * @param authToken An auth token generator for authentication when making
     *     requests
     * @throws URISyntaxException if the host is not a valid URI
     * @throws IOException if an auth token could not be acquired
     */
    public CrossBar(String host, AuthTokenInterface authToken) throws URISyntaxException, IOException {
        parseHost(host);
        this.authToken = authToken;
        // Seed the auth token
        getAuthToken();
    }

    /**
     * Create a CrossBar instance that connects to the given host and port
     * @param host Server hostname or IP address
     * @param port Port that is hosting CrossBar
     * @param authToken An auth token generator for authentication when making
     *     requests
     * @throws URISyntaxException if the host is not a valid URI
     * @throws IOException if an auth token could not be acquired
     */
    public CrossBar(String host, int port, AuthTokenInterface authToken) throws URISyntaxException, IOException {
        parseHost(host);
        this.port = port;
        this.authToken = authToken;
        // Seed the auth token
        getAuthToken();
    }

    public Scheme getScheme() { return scheme; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getApiVersion() { return "v" + apiVersion; }

    /**
     * Get the base URL that all requests should be based off of
     * @return The base URL
     * @throws MalformedURLException if the base URL is not a valid URL
     */
    public String getBaseUrl() throws MalformedURLException {
        return new URL(scheme.name(),
                       host,
                       port,
                       "/" + getApiVersion() + "/accounts/" + authToken.getAuthAccountId()).toString();
    }

    /**
     * Get the current auth token, generating it if it has not already been
     * @return The current auth token
     * @throws IOException if the base URL for auth requests is not a valid URL
     *     or a connection cannot be established to the server
     */
    public String getAuthToken() throws IOException {
        return authToken.getToken(new URL(scheme.name(),
                                          host,
                                          port,
                                          "/" + getApiVersion()).toString());
    }

    /**
     * Set the scheme to be used for CrossBar requests
     * @param schemeName The scheme (HTTP or HTTPS) to be used
     */
    public void setScheme(String schemeName) {
        // If scheme not specified, use the default
        if(schemeName == null) {
            scheme = DEFAULT_SCHEME;
        }
        else {
            scheme = Scheme.betterValueOf(schemeName);
        }
    }

    /**
     * Set the hostname or IP to make requests to
     * @param host Server hostname or IP address
     */
    public void setHost(String host) throws URISyntaxException {
        parseHost(host);
    }

    /**
     * Set the port to make requests to
     * @param port Port that is hosting CrossBar
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get index text from CrossBar
     * @return The index text
     */
    public String getIndex() throws IOException {
        URLConnection urlConnection = new URL(scheme.name(), host, port, INDEX_URL).openConnection();
        InputStream contentStream = (InputStream)urlConnection.getContent();
        StringBuilder builder = new StringBuilder();
        Reader in = new InputStreamReader(contentStream, StandardCharsets.US_ASCII);
        char[] buffer = new char[1024];
        int rsz;
        while((rsz = in.read(buffer)) != -1) {
            builder.append(buffer, 0, rsz);
        }
        return builder.toString();
    }

    public Account getAccount(String id) throws IOException {
        return Account.get(this, id);
    }

    /**
     * Parse a host string for scheme and host
     * @param host Hostname or IP address, possibly prefixed by scheme
     * @throws URISyntaxException if host is an invalid URI or specified
     */
    private void parseHost(String host) throws URISyntaxException {
        URI uri = new URI(host);
        setScheme(uri.getScheme());
        if(uri.getHost() == null) {
            this.host = host;
        }
        else {
            this.host = uri.getHost();
        }
    }
}
