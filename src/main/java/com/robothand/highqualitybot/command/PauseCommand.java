package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * PauseCommand.java
 * Pauses the music player in the current Guild.
 */
public class PauseCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"pause"};
    }

    @Override
    public String getDescription() {
        return "Pauses playback until resumed with the play command. Does nothing if the player is already paused.\n" +
                "Usage: " + Config.PREFIX + "pause";
    }

    @Override
    public String getShortDesc() {
        return "Pauses the player.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (musicPlayer.isPaused()) {
            channel.sendMessage("The player is already paused!").queue();
        } else {
            if (musicPlayer.getPlayingTrack() == null) {    // nothing playing
                channel.sendMessage("There's nothing in the queue to pause!").queue();
            } else {
                musicPlayer.setPaused(true);
                channel.sendMessage("Player paused!").queue();
            }
        }
    }
}
