package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * HelpCommand.java
 * Command used for printing the list of available Commands or providing details on a single Command
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
    public void execute(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        String message;

        if (args.length < 2) {
            log.debug("Printing Command list...");
            message = genCommandList(); // no args, print Command list
        } else {
            log.debug("Printing detailed help...");
            message = genDetailedHelp(args[1]); // we have args, try to print detailed help
        }

        channel.sendMessage(message).queue();
    }

    // Creates a formatted list of all commands
    private String genCommandList() {
        ArrayList<Command> commands = Commands.getInstance().getCommands();

        StringBuilder message = new StringBuilder();
        message.append("Available commands:\n");

        // iterate over all commands
        for (Command command : commands) {
            String[] names = command.getNames();
            String shortDesc = command.getShortDesc();

            message.append("**").append(names[0]).append("** - ");
            message.append((shortDesc != null) ? shortDesc : "Short description not available.").append("\n");
        }

        return message.toString();
    }

    // Creates a formatted detailed description of a given command
    private String genDetailedHelp(String name) {
        StringBuilder message = new StringBuilder();
        Command command = Commands.getInstance().getCommand(name);


        if (command == null) { // given command name is not known
            log.debug("Unknown command {}", name);
            message.append("Command \"**").append(name).append("**\" is unknown.");
        } else { // found it
            String[] names = command.getNames();
            String description = command.getDescription();

            // generate primary name and aliases
            message.append("**");
            int index = 0;
            for (String alias : command.getNames()) {
                if (index == 0) {   // primary name
                    message.append(alias);
                    if (names.length != 1) {
                        message.append(" (");
                    }
                } else if (index == names.length - 1) { // last alias
                    message.append(alias).append(") ");
                } else {
                    message.append(alias).append(", "); // middle
                }

                index++;
            }
            // add description
            message.append("**: ");
            message.append((description != null) ? description : "Description not available.");
        }

        return message.toString();
    }
}
