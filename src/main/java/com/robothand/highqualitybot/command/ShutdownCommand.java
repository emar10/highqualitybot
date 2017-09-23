package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/18/17.
 */
public class ShutdownCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"shutdown", "killswitch"};
    }

    // TODO add description
    @Override
    public String getDescription() {
        return "Immediately shuts down the bot. If you don't have the ability to start it up again, you probably " +
                "shouldn't use this.\n" +
                "Usage: " + Config.PREFIX + "shutdown";
    }

    @Override
    public String getShortDesc() {
        return "Shuts down the bot immediately. Use with caution!";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Going down!").complete();
        Bot.getAPI().shutdown();
    }
}
