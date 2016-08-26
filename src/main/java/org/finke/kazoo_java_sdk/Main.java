package org.finke.kazoo_java_sdk;

import org.finke.kazoo_java_sdk.auth.AuthTokenInterface;
import org.finke.kazoo_java_sdk.auth.UserAndPasswordAuthToken;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by daniel on 2016-08-25.
 */
public class Main {
    public static void main(String[] args) {
        AuthTokenInterface authToken = new UserAndPasswordAuthToken("daniel", "df123!", "piston");
        try {
            CrossBar xbar = new CrossBar("http://devapi.voxter.com", authToken);
            xbar.getAccount("043a546e37a2e2caa151f91c50d701f5");
        }
        catch(URISyntaxException | IOException ex) {
            int t = 4;
        }
    }
}
