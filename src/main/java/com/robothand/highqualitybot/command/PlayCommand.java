package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * PlayCommand.java
 * Resumes the player, or adds a new song to the queue.
 */
public class PlayCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[]{"play"};
    }

    @Override
    public String getDescription() {
        return "With no arguments, resumes playback if the player is paused. Providing a valid audio source (YouTube " +
                "link, SoundCloud, etc.) as an argument will add it to the queue. You can also provide a link to a " +
                "playlist. \n Usage: " + Config.PREFIX + "play [link]";
    }

    @Override
    public String getShortDesc() {
        return "Resume the player, or provide a link to add a song to the queue!";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        // Get player for this guild
        GuildMusicPlayer musicPlayer = GuildMusicPlayer.getPlayer(guild);

        // Don't do anything if there's nothing in the queue
        if (musicPlayer.getPlayingTrack() == null && args.length < 2) {
            channel.sendMessage("There's nothing in the queue to resume!").queue();
            return;
        }

        joinVoiceChannel(guild, musicPlayer, event);

        if (args.length > 1) { // Attempt to play a new track
            String search = "";
            for (int i = 1; i < args.length; i++) {
                search = search.concat(args[i]);
            }

            log.debug("{}: Trying to load track with search \"{}\"...", guild.getName(), search);
            musicPlayer.playTrack(search, channel);
        } else { // Resume the paused track
            if (musicPlayer.isPaused()) {
                musicPlayer.setPaused(false);
                log.debug("{}: resuming playback...", guild.getName());
                channel.sendMessage("Player resumed.").queue();
            } else {
                log.debug("{}: player already running, doing nothing", guild.getName());
                channel.sendMessage("The player is already running!").queue();
            }
        }
    }

    // Attempts to automatically join a voice channel based on the user who ran the command
    private void joinVoiceChannel(Guild guild, GuildMusicPlayer musicPlayer, MessageReceivedEvent event) {
        // Do nothing if we're already in a channel
        if (musicPlayer.isInChannel()) {
            return;
        }

        VoiceChannel voice;
        if (event.getMember().getVoiceState().inVoiceChannel()) {
            // Join sender's channel
            log.debug("{}: getting voice channel from {}", guild.getName(), event.getAuthor().getName());
            voice = event.getMember().getVoiceState().getChannel();
        } else {
            // Otherwise just join the first channel
            log.debug("{}: author not in any voice channel, joining first in list", guild.getName());
            voice = guild.getVoiceChannels().get(0);
        }

        log.debug("{}: attempting to join voice channel {}", guild.getName(), voice.getName());
        musicPlayer.joinChannel(voice);
    }

}