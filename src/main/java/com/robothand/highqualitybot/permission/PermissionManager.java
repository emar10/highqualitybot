package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.command.Command;
import com.robothand.highqualitybot.command.Commands;
import net.dv8tion.jda.core.entities.User;
import sun.security.krb5.Config;

import java.util.ArrayList;

/**
 * Handles PermissionGroups, and provides methods for checking a user's permissions on a certain action.
 */
public class PermissionManager {
    private static final PermissionManager instance = new PermissionManager();
    private ArrayList<PermissionGroup> groups;

    private PermissionManager() {
        groups = new ArrayList<>();
    }

    public static PermissionManager instance() {
        return instance;
    }

    // parses a list of command names separated by commas into an ArrayList of Commands
    public ArrayList<Command> parseCommandList(String list) {
        ArrayList<Command> commands = new ArrayList<>();

        for (String name : list.split(",")) {
            if (name.trim().equals("*")) {
                commands = Commands.getInstance().getCommands();
                break;
            }

            Command command = Commands.getInstance().getCommand(name.trim());
            if (command != null) {
                commands.add(command);
            }
        }

        return commands;
    }

    public boolean hasPermission(User user, Command command) {
        // check for owner
        if (user.getId().equals(Bot.config.OWNERID)) {
            return true;
        }

        boolean permission = false;

        // start with default permissions
        permission = Bot.config.COMMANDPERMS.contains(command);

        // flip if in blacklist mode
        if (!Bot.config.WHITELIST) {
            permission = !permission;
        }

        // TODO check PermissionGroups

        return permission;
    }
}
