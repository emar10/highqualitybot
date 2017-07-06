package com.robothand.highqualitybot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created by ethan on 7/6/17.
 */
public class Config {
    private File configFile;
    private Hashtable<String, String> properties;

    public Config(String path) throws FileNotFoundException {
        configFile = new File(path);

        Scanner s = new Scanner(configFile);

        while (s.hasNextLine()) {
            String[] split = s.nextLine().split("=");
            String key, value;

            if (split.length < 2 || split[0].trim().startsWith("#")) {
                continue;
            }

            key = split[0].trim();
            value = split[1].trim();

            // place the property, overwrite if already present
            if (properties.putIfAbsent(key, value) != null) {
                properties.replace(key, value);
            }
        }
    }
}
