package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
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
        return "Used to test that the bot is working. Though if you're reading this, it's probably working.\n" +
                "Usage: " + Bot.PREFIX + "ping";
    }

    @Override
    public String getShortDesc() {
        return "Return a message to verify the bot is working.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Pong!").queue();
    }
}
