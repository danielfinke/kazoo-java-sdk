package org.finke.kazoo_java_sdk_test;

import org.finke.kazoo_java_sdk.CrossBar;
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
    public static final String SCHEME_SEPARATOR = "://";
    public static final Scheme TEST_SCHEME = Scheme.HTTPS;
    public static final String TEST_HOST = "localhost";
    public static final int TEST_PORT = 8443;
    public static final String TEST_URL =
            TEST_SCHEME.name() + SCHEME_SEPARATOR + TEST_HOST;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getScheme() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL);
        assertEquals(TEST_SCHEME, xbar.getScheme());
        xbar = new CrossBar(TEST_HOST);
        assertEquals(Scheme.HTTP, xbar.getScheme());
    }

    @Test
    public void getHost() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL);
        assertEquals(TEST_HOST, xbar.getHost());
        xbar = new CrossBar(TEST_HOST);
        assertEquals(TEST_HOST, xbar.getHost());
    }

    @Test
    public void getPort() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL, TEST_PORT);
        assertEquals(TEST_PORT, xbar.getPort());
    }

    @Test
    public void setScheme() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL);
        xbar.setScheme("HTTP");
        assertEquals(Scheme.HTTP, xbar.getScheme());
        xbar.setScheme(null);
        assertEquals(Scheme.HTTP, xbar.getScheme());
    }

    @Test
    public void setHost() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL);
        xbar.setHost(TEST_HOST);
        assertEquals(TEST_HOST, xbar.getHost());
        xbar.setHost(TEST_URL);
        assertEquals(TEST_HOST, xbar.getHost());
    }

    @Test
    public void setPort() throws Exception {
        CrossBar xbar = new CrossBar(TEST_URL);
        xbar.setPort(TEST_PORT);
        assertEquals(TEST_PORT, xbar.getPort());
    }

    @Test
    public void getIndex() throws Exception {
        byte[] indexBytes = Files.readAllBytes(ResourceSupport.getResourcePath("kazoo.txt"));
        String indexText = new String(indexBytes, StandardCharsets.UTF_8);

        CrossBar xbar = new CrossBar("http://devapi.voxter.com", 8000);
        assertEquals(indexText, xbar.getIndex());
    }

}