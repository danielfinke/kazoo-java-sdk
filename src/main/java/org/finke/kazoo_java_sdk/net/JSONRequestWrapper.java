package org.finke.kazoo_java_sdk.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Daniel Finke
 * @since 2016-08-25
 */
public class JSONRequestWrapper {
    public static String wrapJSON(JSONWrappableInterface wrappable, Gson gson) {
        return gson.toJson(new Wrapper(wrappable));
    }

    private static class Wrapper {
        public JSONWrappableInterface data;

        public Wrapper(JSONWrappableInterface wrappable) {
            this.data = wrappable;
        }
    }
}
