package com.robothand.highqualitybot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Created by ethan on 6/16/17.
 */
public class MusicManager {
    public final AudioPlayer player;
    public final AudioPlayerSendHandler sendHandler;

    public MusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();
        sendHandler = new AudioPlayerSendHandler(player);
    }
}
