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

    public String TOKEN;
    public String PREFIX;


    public Config(String path) throws FileNotFoundException {
        // set default values
        PREFIX = ".";

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

            // place the property if it is known
            switch (key) {
                case "token":
                    TOKEN = value;
                    break;

                case "prefix":
                    PREFIX = value;
            }

            // Check for values that would need user input
            if (TOKEN == null) {
                Scanner c = new Scanner(System.in);

                System.out.println("Warning: OAuth token not set in config. Please enter your token below:");
                System.out.print("> ");
                TOKEN = c.nextLine();

                System.out.println("Token set. Recommend setting in config file for future runs.");
            }
        }
    }
}
