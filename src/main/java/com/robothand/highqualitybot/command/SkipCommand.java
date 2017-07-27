package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
 */
public class SkipCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"skip"};
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
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        musicPlayer.skipTrack();
        channel.sendMessage("Skipping to next track...").queue();
    }
}
