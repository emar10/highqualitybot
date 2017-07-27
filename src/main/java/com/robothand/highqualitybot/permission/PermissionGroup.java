package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.Utils;
import com.robothand.highqualitybot.command.Command;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Represents a set of permissions for a group of Users
 */
public class PermissionGroup {
    private ArrayList<Command> allowed;
    private ArrayList<Command> disallowed;
    private boolean allowedHasPrecedence;
    private int priority;
    private Role role;

    public PermissionGroup(String filename) throws FileNotFoundException {
        // default values
        allowed = new ArrayList<>();
        disallowed = new ArrayList<>();
        allowedHasPrecedence = true;
        priority = 0;

        Hashtable<String,String> table = Utils.readCFG(filename);

        for (String key : table.keySet()) {
            String value = table.get(key);

            switch (key) {
                case "allowed":
                    allowed = PermissionManager.instance().parseCommandList(value);
                    break;

                case "disallowed":
                    disallowed = PermissionManager.instance().parseCommandList(value);

                case "allowedHasPrecedence":
                    allowedHasPrecedence = Boolean.parseBoolean(value);
                    break;

                case "priority":
                    priority = Integer.parseInt(value);
                    break;

                case "role":
                    role = Bot.getAPI().getRoleById(value);

                    if (role == null) {
                        System.out.println("ERROR: " + filename + ": invalid Role ID");
                    }

                    break;

                default:
                    System.out.println("WARN: " + filename + ": Unknown property \"" + key + "\"");
            }
        }
    }

    public boolean appliesTo(Member member) {
        for (Role current : member.getRoles()) {
            if (current == role) {
                return true;
            }
        }

        return false;
    }

    public int hasPermission(Command command) {
        int permission = 0;

        if (allowed.contains(command)) {
            permission = 1;
        }

        if (disallowed.contains(command) && (permission == 0 || !allowedHasPrecedence)) {
            permission = -1;
        }

        return permission;
    }

    public int getPriority() {
        return priority;
    }

}
