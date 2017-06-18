package com.robothand.highqualitybot.Command;

import com.robothand.highqualitybot.Bot;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ethan on 6/17/17.
 */
public abstract class Command extends ListenerAdapter {
    Message message;
    String content;
    MessageChannel channel;

    public abstract void onCommand(MessageReceivedEvent event);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // grab basic info from event
        message = event.getMessage();
        content = message.getRawContent();
        channel = message.getChannel();

        // check for command prefix
        if (content.startsWith(Bot.PREFIX)) {
            onCommand(event);
        }
    }
}
