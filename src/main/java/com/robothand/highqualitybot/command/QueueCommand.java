package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Queue;

/**
 * QueueCommand.java
 * Lists up to 10 songs in the player's queue, as well as the total number of tracks.
 */
public class QueueCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"list", "queue"};
    }

    @Override
    public String getDescription() {
        return "Prints out a list of all songs currently in the music queue.\n" +
                "Usage: " + Config.PREFIX + "list";
    }

    @Override
    public String getShortDesc() {
        return "List all of the songs currently in the queue.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        StringBuilder message = new StringBuilder();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);
        Queue<AudioTrack> queue = musicPlayer.getQueue();

        if (queue.isEmpty()) {
            log.debug("{}: queue is empty, doing nothing", guild.getName());
            message.append("The queue is empty!");
        } else {
            message.append("Now Playing: **").append(musicPlayer.getPlayingTrack().getInfo().title).append("**\n");
            message.append("Current Queue (").append(queue.size()).append(" tracks):\n");
            int currentTrack = 1;

            for (AudioTrack track : queue) {
                message.append(currentTrack).append(". ").append(track.getInfo().title).append("\n");

                if (currentTrack++ > 10) {
                    break;
                }
            }
        }

        channel.sendMessage(message.toString()).queue();
    }
}
