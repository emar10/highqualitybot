package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
 */
public class NowPlayingCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"nowplaying", "np"};
    }

    @Override
    public String getDescription() {
        return "Lists info on the song currently playing.\n" +
                "Usage: " + Bot.PREFIX + "nowplaying";
    }

    @Override
    public String getShortDesc() {
        return "Show the song currently playing.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        AudioTrack track;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        StringBuilder message = new StringBuilder();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);
        track = musicPlayer.getPlayingTrack();

        if (track == null) {
            message.append("Nothing is playing right now.");
        } else {
            message.append("Playing right now: ").append(track.getInfo().title);
        }
        channel.sendMessage(message.toString()).queue();
    }
}
