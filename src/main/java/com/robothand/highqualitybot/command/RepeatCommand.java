package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.robothand.highqualitybot.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * RepeatCommand.java
 * Sets repeat mode for the current Guild's audio player
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
                "Usage: " + Config.PREFIX + "repeat [off, single, all]";
    }

    @Override
    public String getShortDesc() {
        return "Show or change the repeat mode for the queue.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (args.length == 1) {
            StringBuilder message = new StringBuilder();

            message.append("Current repeat mode is: ").append(musicPlayer.getRepeat());
            message = message.append("\nUse ").append(Config.PREFIX).append("repeat <off, single, all> to set.");

            channel.sendMessage(message.toString()).queue();

        } else {
            switch (args[1].toLowerCase()) {
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

            log.debug("{}: setting repeat mode to {}", guild.getName(), musicPlayer.getRepeat());
            channel.sendMessage("Repeat mode set to " + musicPlayer.getRepeat()).queue();
        }
    }
}
