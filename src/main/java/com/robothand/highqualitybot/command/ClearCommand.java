package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.robothand.highqualitybot.music.TrackScheduler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * ClearCommand.java
 * Command to stop the music player in the current Guild and clear the queue.
 */
public class ClearCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"clear", "stop"};
    }

    @Override
    public String getDescription() {
        return "Stops the player and wipes out the playlist. Will interrupt any currently playing.\n" +
                "Usage: " + Config.PREFIX + "clear";
    }

    @Override
    public String getShortDesc() {
        return "Clears the playlist and stops the player.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();
        String message;

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        // first check to see if the player is already stopped, otherwise empty queue
        if (musicPlayer.getPlayingTrack() == null) {
            log.debug("No playing track, taking no action");
            message = "The player is already stopped!";
        } else {
            log.debug("Clearing player queue...");
            musicPlayer.getQueue().clear();
            musicPlayer.setRepeat(TrackScheduler.Repeat.OFF);
            musicPlayer.skipTrack();

            message = "The queue has been emptied and the player stopped!";
        }

        channel.sendMessage(message).queue();
    }
}
