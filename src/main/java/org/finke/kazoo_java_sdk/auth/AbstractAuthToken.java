package org.finke.kazoo_java_sdk.auth;

import java.io.IOException;

/**
 * @author Daniel Finke
 * @since 2016-08-08
 */
public abstract class AbstractAuthToken implements AuthTokenInterface {
    protected String authToken = null;

    /**
     * Get the current auth token
     * @return The token to use for requests
     */
    @Override
    public String getToken(String baseUrl) throws IOException { return authToken; }
}
