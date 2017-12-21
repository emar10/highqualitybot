package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * SkipCommand.java
 * Skips the currently playing song. Stops playback if there is nothing left in the queue.
 */
public class SkipCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"skip", "next"};
    }

    @Override
    public String getDescription() {
        return "Skips to the next song in the queue. If there are no other songs, simply stops playback.\n" +
                "Usage: " + Config.PREFIX + "skip";
    }

    @Override
    public String getShortDesc() {
        return "Skips the currently playing song.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (musicPlayer.getPlayingTrack() == null) {
            log.debug("{}: No currently playing track, doing nothing", guild.getName());
            channel.sendMessage("There isn't anything playing to skip!").queue();
        } else {
            StringBuilder message = new StringBuilder();

            // skip the track
            message.append("Skipping to next track...");
            log.debug("{}: Skipping track...", guild.getName());
            musicPlayer.skipTrack();

            // output next track's name, if applicable
            if (musicPlayer.getPlayingTrack() == null) {
                message.append("\nReached end of playlist!");
            } else {
                message.append("\n**").append(musicPlayer.getPlayingTrack().getInfo().title).append("** is up next!");
            }

            channel.sendMessage(message.toString()).queue();
        }
    }
}
