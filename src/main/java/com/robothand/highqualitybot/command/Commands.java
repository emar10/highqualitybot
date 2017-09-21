package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ethan on 6/22/17.
 */
public class Commands extends ListenerAdapter {
    private static Commands instance = new Commands();

    private final Hashtable<String, Command> commands;

    public static Commands getInstance() {
        return instance;
    }

    private Commands() {
        commands = new Hashtable<>();
    }

    public void setupListeners(JDA jda) {
        for (String key : commands.keySet()) {
            jda.addEventListener(commands.get(key));
        }
    }

    public void addCommand(Command command) {
        if (command != null) {
            for (String current : command.getNames()) {
                commands.put(current, command);
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // grab basic info from event
        Message message = event.getMessage();
        String content = message.getRawContent();

        // check for command prefix
        if (content.startsWith(Config.PREFIX)) {
            // strip it off and split it
            String[] args = content.replaceFirst(Config.PREFIX, "").split(" ");

            // check for command in aliases
            for (String current : commands.keySet()) {
                if (args[0].equals(current)) {
                    commands.get(current).requestExecute(event, args);
                    break;
                }
            }
        }
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    public ArrayList<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
