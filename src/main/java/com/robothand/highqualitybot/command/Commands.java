package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Commands.java
 * Singleton that maintains a Hashtable of all available commands and attempts to execute the proper command when a
 * valid command message is received by the bot.
 */
public class Commands extends ListenerAdapter {
    private static Commands instance = new Commands();
    private final Hashtable<String, Command> commands;
    private Logger log = LoggerFactory.getLogger(Commands.class);

    public static Commands getInstance() {
        return instance;
    }

    private Commands() {
        commands = new Hashtable<>();
    }

    // Adds a new Command to the Hashtable of known commands
    public void addCommand(Command command) {
        if (command != null) {
            log.debug("Adding command: {}", command.getNames()[0]);
            for (String current : command.getNames()) {
                commands.put(current, command);
            }
        }
    }

    // When a message is received on a Guild this bot is a member of, attempts to run an applicable command.
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
            boolean found = false;
            for (String current : commands.keySet()) {
                if (args[0].equals(current)) {
                    commands.get(current).requestExecute(event, args);
                    found = true;
                    break;
                }
            }
            if (!found) {
                log.info("Ignoring message from {}. \"{}\" is not a known command.", event.getAuthor().getName(), args[0]);
            }
        }
    }

    // Gets a Command by one of its aliases
    public Command getCommand(String name) {
        return commands.get(name);
    }

    // Get the entire Hashtable of Commands
    public ArrayList<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
