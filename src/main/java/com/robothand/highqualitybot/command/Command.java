package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.permission.PermissionManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Abstract for all Command classes.
 */
public abstract class Command extends ListenerAdapter {

    public abstract String[] getNames();
    public abstract String getDescription();
    public abstract String getShortDesc();
    public abstract void onCommand(MessageReceivedEvent event, String[] args);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // grab basic info from event
        Message message = event.getMessage();
        String content = message.getRawContent();

        // check for command prefix
        if (content.startsWith(Bot.config.PREFIX)) {
            // strip it off and split it
            String[] args = content.replaceFirst(Bot.config.PREFIX, "").split(" ");

            // check with names
            for (String current : getNames()) {
                if (args[0].equals(current)) {
                    // run command if we have permission
                    if (PermissionManager.instance().hasPermission(event.getMember(), this)) {
                        onCommand(event, args);
                    } else {
                        event.getChannel().sendMessage("You do not have permission to run that command.").queue();
                    }
                    break;
                }
            }
        }
    }
}
