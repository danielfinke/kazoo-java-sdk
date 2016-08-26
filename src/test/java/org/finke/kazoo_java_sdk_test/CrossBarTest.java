package org.finke.kazoo_java_sdk_test;

import org.finke.kazoo_java_sdk.CrossBar;
import org.finke.kazoo_java_sdk.auth.AuthTokenInterface;
import org.finke.kazoo_java_sdk.auth.UserAndPasswordAuthToken;
import org.finke.kazoo_java_sdk.net.Scheme;
import org.finke.kazoo_java_sdk_test.support.ResourceSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Finke
 * @since 2016-08-06
 */
public class CrossBarTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACCOUNT_ID = "account_id";
    public static final String SCHEME_SEPARATOR = "://";
    public static final Scheme TEST_SCHEME = Scheme.HTTPS;
    public static final String TEST_HOST = "localhost";
    public static final int TEST_PORT = 8443;
    public static final String TEST_URL =
            TEST_SCHEME.name() + SCHEME_SEPARATOR + TEST_HOST;

    private AuthTokenInterface authToken;

    @Before
    public void setUp() throws Exception {
        authToken = new UserAndPasswordAuthToken(USERNAME, PASSWORD, ACCOUNT_ID);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getScheme() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, authToken);
        assertEquals(TEST_SCHEME, xbar.getScheme());
        xbar = new CrossBar(TEST_HOST, authToken);
        assertEquals(Scheme.HTTP, xbar.getScheme());
    }

    @Test
    public void getHost() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, authToken);
        assertEquals(TEST_HOST, xbar.getHost());
        xbar = new CrossBar(TEST_HOST, authToken);
        assertEquals(TEST_HOST, xbar.getHost());
    }

    @Test
    public void getPort() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, TEST_PORT, authToken);
        assertEquals(TEST_PORT, xbar.getPort());
    }

    @Test
    public void setScheme() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, authToken);
        xbar.setScheme("HTTP");
        assertEquals(Scheme.HTTP, xbar.getScheme());
        xbar.setScheme(null);
        assertEquals(Scheme.HTTP, xbar.getScheme());
    }

    @Test
    public void setHost() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, authToken);
        xbar.setHost(TEST_HOST);
        assertEquals(TEST_HOST, xbar.getHost());
        xbar.setHost(TEST_URL);
        assertEquals(TEST_HOST, xbar.getHost());
    }

    @Test
    public void setPort() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, authToken);
        xbar.setPort(TEST_PORT);
        assertEquals(TEST_PORT, xbar.getPort());
    }

    @Test
    public void getIndex() throws Exception {
        byte[] indexBytes = Files.readAllBytes(ResourceSupport.getResourcePath("kazoo.txt"));
        String indexText = new String(indexBytes, StandardCharsets.UTF_8);

        CrossBar xbar = new CrossBar("http://devapi.voxter.com", 8000, authToken);
        assertEquals(indexText, xbar.getIndex());
    }

}