package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
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
                "playlist. \n Usage: " + Bot.PREFIX + "play [link]";
    }

    @Override
    public String getShortDesc() {
        return "Resume the player, or provide a link to add a song to the queue!";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
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

            musicPlayer.playTrack(search, channel);
        } else { // Resume the paused track
            if (musicPlayer.isPaused()) {
                musicPlayer.setPaused(false);
                channel.sendMessage("Player resumed.").queue();
            } else {
                channel.sendMessage("The player is already running!").queue();
            }
        }
    }

    private void joinVoiceChannel(Guild guild, GuildMusicPlayer musicPlayer, MessageReceivedEvent event) {
        if (musicPlayer.isInChannel()) {
            return;
        }

        VoiceChannel voice;
        if (event.getMember().getVoiceState().inVoiceChannel()) {
            // Join sender's channel
            voice = event.getMember().getVoiceState().getChannel();
        } else {
            // Otherwise just join the first channel
            voice = guild.getVoiceChannels().get(0);
        }

        musicPlayer.joinChannel(voice);
    }

}