package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.Command;
import com.robothand.highqualitybot.permission.PermissionManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Config.java
 *
 * Reads in properties from a specified configuration file, and provides access to the values.
 */
public class Config {
    String TOKEN;
    public String PREFIX;
    public String OWNERID;
    public ArrayList<Command> ALLOWED;
    public ArrayList<Command> DISALLOWED;
    public boolean ALLOWEDHASPRECEDENCE;
    public ArrayList<String> PERMGROUPS;


    public Config(String path) throws FileNotFoundException {
        // set default values
        PREFIX = ".";
        ALLOWED = new ArrayList<>();
        DISALLOWED = new ArrayList<>();
        ALLOWEDHASPRECEDENCE = true;
        PERMGROUPS = new ArrayList<>();

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

                case "ownerid":
                    OWNERID = value;
                    break;

                case "allowed":
                    ALLOWED = PermissionManager.instance().parseCommandList(value);
                    break;

                case "disallowed":
                    DISALLOWED = PermissionManager.instance().parseCommandList(value);
                    break;

                case "allowedHasPrecedence":
                    if (value.equals("true")) {
                        ALLOWEDHASPRECEDENCE = true;
                    } else if (value.equals("false")) {
                        ALLOWEDHASPRECEDENCE = false;
                    }
                    break;

                case "permissiongroups":
                    for (String filename : value.split(",")) {
                        PERMGROUPS.add(filename.trim());
                    }
                    break;

                default:
                    System.out.println("Info: Unknown property \"" + key + "\", ignoring.");
            }
        }

        // TODO if default perms do not encompass every command, set all to disallowed

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
