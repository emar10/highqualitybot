package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Queue;

/**
 * Created by ethan on 6/19/17.
 */
public class QueueCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"list", "queue"};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getShortDesc() {
        return "List all of the songs currently in the queue.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);
        Queue<AudioTrack> queue = musicPlayer.getQueue();

        String message = "Current Queue:\n";
        int currentTrack = 1;

        for (AudioTrack track : queue) {
            message = message.concat(currentTrack + ". " + track.getInfo().title + "\n");
            currentTrack++;
        }

        channel.sendMessage(message).queue();
    }
}
