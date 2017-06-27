package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/27/17.
 */
public class ClearCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"clear", "stop"};
    }

    @Override
    public String getDescription() {
        return "Stops the player and wipes out the playlist. Will interrupt any currently playing.\n" +
                "Usage: " + Bot.PREFIX + "clear";
    }

    @Override
    public String getShortDesc() {
        return "Clears the playlist and stops the player.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {

    }
}
