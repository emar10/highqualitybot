package com.robothand.highqualitybot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.local.LocalAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

import java.util.Hashtable;

/**
 * Created by ethan on 6/18/17.
 */
public class GuildMusicPlayer {

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static Hashtable<String, GuildMusicPlayer> guilds;
    private final AudioPlayer player;
    private final AudioPlayerSendHandler handler;

    static void setupSources() {
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
        playerManager.registerSourceManager(new LocalAudioSourceManager());
    }

    static void addPlayer(String guildName, GuildMusicPlayer gmp) {
        guilds.put(guildName, gmp);
    }

    static GuildMusicPlayer getPlayer(String guildName) {
        return guilds.get(guildName);
    }

    public GuildMusicPlayer() {
        player = playerManager.createPlayer();
        handler = new AudioPlayerSendHandler(player);
    }
}
