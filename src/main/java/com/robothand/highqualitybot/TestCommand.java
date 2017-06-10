package com.robothand.highqualitybot;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * basic test commands
 */
public class TestCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getRawContent();
        MessageChannel channel = message.getChannel();

        switch (content) {
            case ".ping":
                channel.sendMessage("Pong!").queue();
                break;

            case ".killswitch":
                channel.sendMessage("Going down!").complete();
                Bot.shutdown();
                break;

            default:
                break;
        }
    }

}
