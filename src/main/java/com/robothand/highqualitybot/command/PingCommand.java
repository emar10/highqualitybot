package com.robothand.highqualitybot.command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/18/17.
 */
public class PingCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"ping"};
    }

    // TODO add descriptions for command
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getShortDesc() {
        return null;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Pong!").queue();
    }
}
