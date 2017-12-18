package com.robothand.highqualitybot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TrackScheduler.java
 *
 * Maintains a list of songs to be played.
 */
public class TrackScheduler extends AudioEventAdapter {
    Logger log = LoggerFactory.getLogger(this.getClass());

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
            log.debug("Playing next track in queue...");
            player.startTrack(queue.poll(), false);
        } else if (repeating == Repeat.SINGLE) {
            log.debug("Repeating single track...");
            player.startTrack(player.getPlayingTrack().makeClone(), false);
        } else if (repeating == Repeat.ALL) {
            log.debug("Playing next track and adding previous to the queue...");
            queue.add(player.getPlayingTrack().makeClone());
            player.startTrack(queue.poll(), false);
        }

        if (player.getPlayingTrack() == null) {
            log.debug("Queue is empty, leaving AudioChannel...");
            player.setPaused(true);
            new Thread(guildPlayer::leaveChannel).start();
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
