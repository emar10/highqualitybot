package com.robothand.highqualitybot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ethan on 6/19/17.
 */
public class TrackScheduler extends AudioEventAdapter {
    public enum Repeat {
        OFF, SINGLE, ALL
    }

    Repeat repeating;
    final Queue<AudioTrack> queue;
    final AudioPlayer player;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        queue = new LinkedList<>();
    }

    // TODO implement scheduler methods
    public void add(AudioTrack track) {

    }

    public void nextTrack() {

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {

    }

    public Repeat getRepeat() {
        return repeating;
    }

    public void setRepeat(Repeat repeating) {
        this.repeating = repeating;
    }
}
