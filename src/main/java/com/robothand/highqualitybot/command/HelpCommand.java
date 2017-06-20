package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Hashtable;

/**
 * Created by ethan on 6/19/17.
 */
public class HelpCommand extends Command {
    private final Hashtable<String, Command> commands;

    @Override
    public String[] getNames() {
        return new String[] {"help", "commands", "h"};
    }

    @Override
    public String getDescription() {
        return "Lists all available commands. Provide the name of another command to get more information as well as" +
                "usage.\n" +
                "Usage: " + Bot.PREFIX + "help [command]";
    }

    @Override
    public String getShortDesc() {
        return "Lists all available commands. Provide a specific command for more info.";
    }

    public HelpCommand() {
        commands = new Hashtable<>();
    }

    public Command addCommand(Command command) {
        commands.put(command.getNames()[0], command);
        return command;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();

        if (args.length < 2) {
            StringBuilder message = new StringBuilder();
            message.append("Available commands:\n");

            for (String key : commands.keySet()) {
                Command command = commands.get(key);
                String[] names = command.getNames();
                String shortDesc = command.getShortDesc();

                message.append("**").append(names[0]).append("** - ");
                message.append((shortDesc != null) ? shortDesc : "Short description not available.").append("\n");
            }

            channel.sendMessage(message.toString()).queue();
        } else {
            StringBuilder message = new StringBuilder();
            Command command = commands.get(args[1]);

            if (command == null) {
                message.append("Command \"**").append(args[1]).append("**\" is unknown.");
            } else {
                String[] names = command.getNames();
                String description = command.getDescription();

                message.append("**");
                int index = 0;
                for (String alias : command.getNames()) {
                    if (index == 0) {
                        message.append(alias);
                        if (names.length != 1) {
                            message.append(" (");
                        }
                    } else if (index == names.length - 1) {
                        message.append(alias).append(") ");
                    } else {
                        message.append(alias).append(", ");
                    }

                    index++;
                }
                message.append("**: ");
                message.append((description != null) ? description : "Description not available.");
            }

            channel.sendMessage(message.toString()).queue();
        }
    }
}
