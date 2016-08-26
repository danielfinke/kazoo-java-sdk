package org.finke.kazoo_java_sdk.entity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.finke.kazoo_java_sdk.CrossBar;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Daniel Finke
 * @since 2016-08-25
 */
public class Account extends AbstractEntity {
    private static final String URL_PART = "/accounts";

    private Date        created;
    private String      id;
    private boolean     isReseller;
    private String      language;
    private String      name;
    private String      realm;
    private String      resellerId;
    private TimeZone    timezone;

    public static Account get(CrossBar xbar, String id) throws IOException {
        String json = getJSON(xbar, URL_PART, id);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson.fromJson(json, Account.class);
    }
}
