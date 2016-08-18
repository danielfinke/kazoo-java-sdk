package org.finke.kazoo_java_sdk.net;

/**
 * @author Daniel Finke
 * @since 2016-08-06
 */
public enum Scheme {
    HTTP,
    HTTPS;

    public static Scheme betterValueOf(String name) throws NullPointerException, IllegalArgumentException {
        if(name == null) {
            throw new NullPointerException();
        }

        String lower = name.toLowerCase();
        if(lower.equals("http")) {
            return Scheme.HTTP;
        }
        else if(lower.equals("https")) {
            return Scheme.HTTPS;
        }

        // Invalid scheme name
        throw new IllegalArgumentException(name + " is not a valid scheme");
    }
}
