package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.Command;
import com.robothand.highqualitybot.command.Commands;
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
    public ArrayList<Command> COMMANDPERMS;
    public boolean WHITELIST;
    public String[] PERMGROUPS;


    public Config(String path) throws FileNotFoundException {
        // set default values
        PREFIX = ".";
        COMMANDPERMS = Commands.getInstance().getCommands();
        WHITELIST = true;
        PERMGROUPS = new String[] {"permissions/"};

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

                case "commandperms":
                    COMMANDPERMS = PermissionManager.instance().parseCommandList(value);
                    break;

                case "whitelist":
                    if (value.equals("true")) {
                        WHITELIST = true;
                    } else if (value.equals("false")) {
                        WHITELIST = false;
                    }
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
