package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.command.Command;
import com.robothand.highqualitybot.command.Commands;
import net.dv8tion.jda.core.entities.Member;

import java.io.FileNotFoundException;
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

    public boolean hasPermission(Member member, Command command) {
        // check for owner
        if (member.getUser().getId().equals(Bot.config.OWNERID)) {
            return true;
        }

        boolean permission = false;

        // start with default permissions
        int def = 0;
        if (Bot.config.ALLOWED.contains(command)) {
            def = 1;
        }

        if (Bot.config.DISALLOWED.contains(command) && (def == 0 || !Bot.config.ALLOWEDHASPRECEDENCE)) {
            def = -1;
        }

        switch (def) {
            case -1:

        }

        // check PermissionGroups
        int priority = 0;
        for (PermissionGroup group : groups) {
            if (group.appliesTo(member) && group.getPriority() > priority) {
                priority = group.getPriority();
                switch (group.hasPermission(command)) {
                    case -1:
                        permission = false;
                        break;
                    case 0:
                        break;
                    case 1:
                        permission = true;
                        break;
                }
            }
        }

        return permission;
    }

    public void readGroups() {
        for (String filename : Bot.config.PERMGROUPS) {
            try {
                groups.add(new PermissionGroup(filename));
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: could not find file " + filename);
            }
        }
    }
}
