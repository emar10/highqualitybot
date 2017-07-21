package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.command.Command;
import net.dv8tion.jda.core.entities.User;

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

    public boolean hasPermission(User user, Command command) {
        // check for owner
        if (user.getId() == Bot.config.OWNER) {
            return true;
        }

        // if all else fails, return false
        return false;
    }
}
