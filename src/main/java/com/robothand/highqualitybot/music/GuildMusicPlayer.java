package com.robothand.highqualitybot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.Hashtable;

/**
 * Created by ethan on 6/18/17.
 */
public class GuildMusicPlayer {

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static Hashtable<String, GuildMusicPlayer> guilds;
    private final AudioPlayer player;
    private final AudioPlayerSendHandler handler;
    private final TrackScheduler scheduler;
    private final Guild guild;

    static void setupSources() {
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
        playerManager.registerSourceManager(new LocalAudioSourceManager());
    }

    static void addPlayer(String guildID, GuildMusicPlayer gmp) {
        guilds.put(guildID, gmp);
    }

    public static GuildMusicPlayer getPlayer(String guildID) {
        return guilds.get(guildID);
    }

    public GuildMusicPlayer(Guild guild) {
        player = playerManager.createPlayer();
        handler = new AudioPlayerSendHandler(player);
        scheduler = new TrackScheduler(player);
        this.guild = guild;

        player.addListener(scheduler);
        GuildMusicPlayer.addPlayer(guild.getId(), this);
    }

    public void joinChannel(VoiceChannel channel) {
        guild.getAudioManager().setSendingHandler(handler);
        guild.getAudioManager().openAudioConnection(channel);
    }

    public void leaveChannel() {
        guild.getAudioManager().setSendingHandler(null);
        guild.getAudioManager().closeAudioConnection();
    }

    public boolean isPaused() {
        return player.isPaused();
    }

    public void playTrack(String search) {
        playTrack(search, null);
    }

    public void playTrack(String search, MessageChannel channel) {

        playerManager.loadItemOrdered(player, search, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                scheduler.add(track);

                if (channel != null) {
                    String message = "Added \"" + track.getInfo().title + "\" to the queue.";
                    channel.sendMessage(message).queue();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    scheduler.add(track);
                }

                if (channel != null) {
                    String message = "Added " + playlist.getTracks().size() + " tracks from \"" + playlist.getName()
                                   + "\" to the queue.";
                    channel.sendMessage(message).queue();
                }
            }

            @Override
            public void noMatches() {
                if (channel != null) {
                    String message = "Could not find any matches for \"" + search + "\"";
                    channel.sendMessage(message);
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }
}
