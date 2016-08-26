package org.finke.kazoo_java_sdk.entity;

import com.google.gson.Gson;
import org.finke.kazoo_java_sdk.CrossBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Daniel Finke
 * @since 2016-08-25
 */
public class AbstractEntity implements EntityInterface {
    /**
     *
     * @param xbar
     * @param urlPart
     * @param id
     * @return
     * @throws IOException
     */
    protected static String getJSON(CrossBar xbar, String urlPart, String id) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)(new URL(xbar.getBaseUrl() + urlPart + "/" + id)).openConnection();
        urlConnection.setRequestProperty("X-Auth-Token", xbar.getAuthToken());

        InputStream contentStream = (InputStream)urlConnection.getContent();
        StringBuilder builder = new StringBuilder();
        InputStreamReader in = new InputStreamReader(contentStream, StandardCharsets.US_ASCII);
        char[] buffer = new char[1024];
        int rsz;
        while((rsz = in.read(buffer)) != -1) {
            builder.append(buffer, 0, rsz);
        }
        return builder.toString();
    }
}
