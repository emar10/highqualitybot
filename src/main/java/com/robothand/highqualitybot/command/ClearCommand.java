package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Queue;

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
                "Usage: " + Bot.config.PREFIX + "clear";
    }

    @Override
    public String getShortDesc() {
        return "Clears the playlist and stops the player.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        String message;

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (musicPlayer.getPlayingTrack() == null) {
            message = "The player is already stopped!";
        } else {
            musicPlayer.getQueue().clear();
            musicPlayer.skipTrack();

            message = "The queue has been emptied and the player stopped!";
        }

        channel.sendMessage(message).queue();
    }
}
