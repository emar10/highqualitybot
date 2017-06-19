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

    private Repeat repeating;
    private final Queue<AudioTrack> queue;
    private final AudioPlayer player;
    private AudioTrack prevTrack;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        queue = new LinkedList<>();
    }

    public void add(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    public void nextTrack() {
        nextTrack(false);
    }

    public void nextTrack(boolean forceNext) {
        if (repeating == Repeat.OFF || forceNext) {
            player.startTrack(queue.poll(), false);
        } else if (repeating == Repeat.SINGLE) {
            player.startTrack(prevTrack.makeClone(), false);
        } else if (repeating == Repeat.ALL) {
            if (prevTrack == null) {
                prevTrack = player.getPlayingTrack();
            }

            player.startTrack(queue.poll(), false);
            queue.add(prevTrack);
            prevTrack = null;
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {
        prevTrack = track;
        nextTrack();
    }

    public Repeat getRepeat() {
        return repeating;
    }

    public void setRepeat(Repeat repeating) {
        this.repeating = repeating;
    }
}
