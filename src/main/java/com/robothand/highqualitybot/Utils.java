package com.robothand.highqualitybot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Static class that stores various utility methods and sundries.
 */
public class Utils {

    // Loads the properties from a given .cfg file into a Hashtable
    public static Hashtable<String,String> readCFG(String filename) throws FileNotFoundException {
        Hashtable<String,String> properties = new Hashtable<>();
        File file = new File(filename);

        Scanner s = new Scanner(file);

        while (s.hasNextLine()) {
            String[] split = s.nextLine().split("=");
            String key, value;

            if (split.length < 2 || split[0].trim().startsWith("#")) {
                continue;
            }

            key = split[0].trim();
            value = split[1].trim();

            properties.put(key, value);
        }

        return properties;
    }
}
