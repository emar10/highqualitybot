package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.robothand.highqualitybot.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
 */
public class RepeatCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"repeat"};
    }

    @Override
    public String getDescription() {
        return "With no arguments, shows the repeat mode. OFF disables repeating, SINGLE will repeat the current song" +
                "until manually skipped, and ALL will repeat the entire playlist indefinitely.\n" +
                "Usage: " + Bot.config.PREFIX + "repeat [off, single all]";
    }

    @Override
    public String getShortDesc() {
        return "Show or change the repeat mode for the queue.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (args.length == 1) {
            String message = "Current repeat mode is: " + musicPlayer.getRepeat();

//            switch (musicPlayer.getRepeat()) {
//                case OFF:
//                    message.concat("OFF");
//                    break;
//                case SINGLE:
//                    message.concat("SINGLE");
//                    break;
//                case ALL:
//                    message.concat("ALL");
//                    break;
//            }

            message = message.concat("\nUse " + Bot.config.PREFIX + "repeat <off, single, all> to set.");

            channel.sendMessage(message).queue();

        } else {
            switch (args[1]) {
                case "off":
                    musicPlayer.setRepeat(TrackScheduler.Repeat.OFF);
                    break;
                case "single":
                    musicPlayer.setRepeat(TrackScheduler.Repeat.SINGLE);
                    break;
                case "all":
                    musicPlayer.setRepeat(TrackScheduler.Repeat.ALL);
                    break;
            }

            channel.sendMessage("Repeat mode set to " + musicPlayer.getRepeat()).queue();
        }
    }
}
