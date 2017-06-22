package com.robothand.highqualitybot.command;

import net.dv8tion.jda.core.JDA;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ethan on 6/22/17.
 */
public class Commands {
    private static Commands instance = new Commands();

    private final Hashtable<String, Command> commands;
    private final ArrayList<String[]> aliases;

    public static Commands getInstance() {
        return instance;
    }

    private Commands() {
        commands = new Hashtable<>();
        aliases = new ArrayList<>();
    }

    public void addListeners(JDA jda) {
        for (String key : commands.keySet()) {
            jda.addEventListener(commands.get(key));
        }
    }

    public void addCommand(Command command) {
        aliases.add(command.getNames());
        commands.put(command.getNames()[0], command);
    }

    // TODO allow getCommand to find commands using aliases
    public Command getCommand(String name) {
        return commands.get(name);
    }
}
