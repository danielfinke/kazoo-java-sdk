package org.finke.kazoo_java_sdk.auth;

import java.io.IOException;

/**
 * @author Daniel Finke
 * @since 2016-08-08
 */
public abstract class AbstractAuthToken implements AuthTokenInterface {
    protected String authToken = null;
    protected String accountId = null;

    /**
     * Get the current auth token
     * @param baseUrl The base URL for auth requests
     * @return The token to use for requests
     * @throws IOException if subclass implementation throws exception
     */
    @Override
    public String getToken(String baseUrl) throws IOException { return authToken; }

    /**
     * Get the account ID of the currently authenticated user if already
     * authenticated
     * @return The account ID of the currently authenticated user
     */
    @Override
    public String getAuthAccountId() { return accountId; }
}
