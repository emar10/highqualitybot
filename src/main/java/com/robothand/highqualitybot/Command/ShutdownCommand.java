package com.robothand.highqualitybot.Command;

import com.robothand.highqualitybot.Bot;
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
        return null;
    }

    @Override
    public String getShortDesc() {
        return null;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Going down!").complete();
        Bot.getAPI().shutdown();
    }
}
