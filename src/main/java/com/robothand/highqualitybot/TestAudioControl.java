package com.robothand.highqualitybot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by ethan on 6/12/17.
 */
public class TestAudioControl extends ListenerAdapter {

    private final AudioPlayerManager playerManager;
    private MusicManager musicManager;

    public TestAudioControl() {
        this.playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        musicManager = null;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String command[] = event.getMessage().getRawContent().split(" ");

        if (!command[0].startsWith(".")) return;

        Guild guild = event.getGuild();
        if (musicManager == null) {
            musicManager = new MusicManager(playerManager);
        }
        AudioPlayer player = musicManager.player;

        switch (command[0]) {
            case ".join":
                VoiceChannel channel = guild.getVoiceChannels().get(0);

                guild.getAudioManager().setSendingHandler(musicManager.sendHandler);

                guild.getAudioManager().openAudioConnection(channel);
                break;

            case ".leave":
                guild.getAudioManager().setSendingHandler(null);
                guild.getAudioManager().closeAudioConnection();
                break;

            case ".play":
                MessageChannel textChannel = event.getTextChannel();

                if (command.length < 2) {
                    textChannel.sendMessage("Usage: .play <YouTube URL>").queue();
                    break;
                }

                playerManager.loadItem(command[1], new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        textChannel.sendMessage("Playing \"" + track.getInfo().title).queue();
                        musicManager.player.startTrack(track, false);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        AudioTrack track = playlist.getSelectedTrack();
                        this.trackLoaded(track);
                    }

                    @Override
                    public void noMatches() {
                        textChannel.sendMessage("Could not find a match.").queue();
                    }

                    @Override
                    public void loadFailed(FriendlyException exception) {
                        textChannel.sendMessage("Error: " + exception.getMessage()).queue();
                    }
                });
        }
    }
}
