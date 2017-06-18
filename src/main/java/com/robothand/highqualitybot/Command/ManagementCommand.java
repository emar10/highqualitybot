package com.robothand.highqualitybot.Command;

import com.robothand.highqualitybot.Bot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/18/17.
 */
public class ManagementCommand extends Command {

    public void onCommand(MessageReceivedEvent event) {
        switch (content) {
            case ".ping":
                channel.sendMessage("You rang?").queue();
                break;

            case ".killswitch":
                channel.sendMessage("Going down!").queue();
                Bot.getAPI().shutdown();
                break;
        }
    }
}
