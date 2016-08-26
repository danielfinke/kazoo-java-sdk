package org.finke.kazoo_java_sdk.auth;

import java.io.IOException;

/**
 * @author Daniel Finke
 * @since 2016-08-08
 */
public interface AuthTokenInterface {
    String getToken(String baseUrl) throws IOException;
    String getNewToken(String baseUrl) throws IOException;
}
