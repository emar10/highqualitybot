package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.command.Command;
import com.robothand.highqualitybot.command.Commands;
import net.dv8tion.jda.core.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * PermissionManager.java
 *
 * Handles PermissionGroups, and provides methods for checking a user's permissions on a certain action.
 */
public class PermissionManager {
    private Logger log = LoggerFactory.getLogger(this.getClass());
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
        if (member.getUser().getId().equals(Config.OWNERID)) {
            return true;
        }

        boolean permission = false;

        // start with default permissions
        if (Config.ALLOWED.contains(command)) {
            permission = true;
        }

        if (Config.DISALLOWED.contains(command) && !Config.ALLOWED_HAS_PRECEDENCE) {
            permission = false;
        }

        // check PermissionGroups
        int priority = 0;
        for (PermissionGroup group : groups) {
            if (group.appliesTo(member, command) && group.getPriority() > priority) {
                priority = group.getPriority();
                permission = group.hasPermission(command);
            }
        }

        return permission;
    }

    public void readGroups() {
        for (String filename : Config.PERMGROUPS) {
            try {
                groups.add(new PermissionGroup(filename));
            } catch (FileNotFoundException e) {
                log.error("Could not find PermissionGroup config file \'{}\'", filename);
            }
        }
    }
}
