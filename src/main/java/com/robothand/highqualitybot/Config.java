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
    public String TOKEN;
    public String PREFIX;


    public Config(String path) throws FileNotFoundException {
        // set default values
        PREFIX = ".";

        // read config.cfg
        Hashtable<String,String> table = Utils.readCFG("config.cfg");
        for (String key : table.keySet()) {
            String value = table.get(key);

            // place the property if it is known
            switch (key) {
                case "token":
                    TOKEN = value;
                    break;

                case "prefix":
                    PREFIX = value;
                    break;

                default:
                    System.out.println("Info: Unknown property \"" + key + "\", ignoring.");
            }
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
