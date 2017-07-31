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
    private final GuildMusicPlayer guildPlayer;
    private AudioTrack prevTrack;

    public TrackScheduler(AudioPlayer player, GuildMusicPlayer guildPlayer) {
        repeating = Repeat.OFF;
        this.player = player;
        this.guildPlayer = guildPlayer;
        queue = new LinkedList<>();
    }

    public void add(AudioTrack track) {
        if (player.startTrack(track, true)) {
            player.setPaused(false);
        } else {
            queue.offer(track);
        }
    }

    public Queue<AudioTrack> getQueue() {
        return queue;
    }

    public void nextTrack() {
        nextTrack(false);
    }

    public void nextTrack(boolean forceNext) {
        if (repeating == Repeat.OFF || forceNext) {
            player.startTrack(queue.poll(), false);
        } else if (repeating == Repeat.SINGLE) {
            player.startTrack(player.getPlayingTrack().makeClone(), false);
        } else if (repeating == Repeat.ALL) {
            queue.add(player.getPlayingTrack().makeClone());
            player.startTrack(queue.poll(), false);
        }

        if (player.getPlayingTrack() == null) {
            player.setPaused(true);
            guildPlayer.leaveChannel();
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason) {
        if (reason.mayStartNext) {
            prevTrack = track;
            nextTrack();
        }
    }

    public Repeat getRepeat() {
        return repeating;
    }

    public void setRepeat(Repeat repeating) {
        this.repeating = repeating;
    }
}
