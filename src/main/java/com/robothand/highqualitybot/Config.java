package com.robothand.highqualitybot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Config.java
 *
 * Reads in properties from a specified configuration file, and provides access to the values.
 */
public class Config {
    private final File configFile;
    private final Hashtable<String, String> properties;
    private final String DEF_VALUE = "not_set";

    public Config(String path) throws FileNotFoundException {
        properties = new Hashtable<>();
        configFile = new File(path);

        // properties
        properties.put("token", DEF_VALUE);
        properties.put("prefix", DEF_VALUE);

        Scanner s = new Scanner(configFile);

        while (s.hasNextLine()) {
            String[] split = s.nextLine().split("=");
            String key, value;

            if (split.length < 2 || split[0].trim().startsWith("#")) {
                continue;
            }

            key = split[0].trim();
            value = split[1].trim();

            // place the property if it is known
            properties.replace(key, value);
        }
    }

    public String getProperty(String key) {
        return properties.get(key);
    }
}
