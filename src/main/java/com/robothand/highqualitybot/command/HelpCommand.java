package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ethan on 6/19/17.
 */
public class HelpCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"help", "commands", "h"};
    }

    @Override
    public String getDescription() {
        return "Lists all available commands. Provide the name of another command to get more information as well as" +
                "usage.\n" +
                "Usage: " + Config.PREFIX + "help [command]";
    }

    @Override
    public String getShortDesc() {
        return "Lists all available commands. Provide a specific command for more info.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        String message;

        if (args.length < 2) {
            message = genCommandList();
        } else {
            message = genDetailedHelp(args[1]);
        }

        channel.sendMessage(message).queue();
    }

    private String genCommandList() {
        ArrayList<Command> commands = Commands.getInstance().getCommands();

        StringBuilder message = new StringBuilder();
        message.append("Available commands:\n");

        for (Command command : commands) {
            String[] names = command.getNames();
            String shortDesc = command.getShortDesc();

            message.append("**").append(names[0]).append("** - ");
            message.append((shortDesc != null) ? shortDesc : "Short description not available.").append("\n");
        }

        return message.toString();
    }

    private String genDetailedHelp(String name) {
        StringBuilder message = new StringBuilder();
        Command command = Commands.getInstance().getCommand(name);

        if (command == null) {
            message.append("Command \"**").append(name).append("**\" is unknown.");
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

        return message.toString();
    }
}
