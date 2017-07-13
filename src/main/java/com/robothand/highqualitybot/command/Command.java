package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ethan on 6/17/17.
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
                    onCommand(event, args); // run command if we find the name
                    break;
                }
            }
        }
    }
}
