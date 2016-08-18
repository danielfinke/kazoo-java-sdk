package org.finke.kazoo_java_sdk_test.support;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Daniel Finke
 * @since 2016-08-06
 */
public class ResourceSupport {
    /**
     * Get the Path object representing the path to a resource file
     * @param resourceName The name of the desired resource
     * @return The Path object representing the path to the desired resource
     */
    public static Path getResourcePath(String resourceName) {
        URL resource = ResourceSupport.class.getClassLoader().getResource(resourceName);
        String pathStr = resource.getPath();

        // On Windows, the path is prefixed with '/' for some reason;
        // this causes problems in Paths.get
        if(pathStr.charAt(0) == '/' && pathStr.charAt(2) == ':') {
            pathStr = pathStr.substring(1);
        }

        return Paths.get(pathStr);
    }
}
