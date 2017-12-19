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
            musicPlayer.skipTrack();
            channel.sendMessage("Skipping to next track...").queue();
        }
    }
}
