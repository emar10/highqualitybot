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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Queue;

/**
 * GuildMusicPlayer.java
 *
 * Represents the bot's interactions with voice channels. Handles all music
 * related requests from Commands.
 */
public class GuildMusicPlayer {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static final Hashtable<String, GuildMusicPlayer> guilds = new Hashtable<>();
    private final AudioPlayer player;
    private final AudioPlayerSendHandler handler;
    private final TrackScheduler scheduler;
    private final Guild guild;

    public static void setupSources() {
        Logger log = LoggerFactory.getLogger(GuildMusicPlayer.class);

        log.info("Setting up audio sources...");
        log.debug("YouTube...");
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        log.debug("SoundCloud...");
        playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
        log.debug("Local...");
        playerManager.registerSourceManager(new LocalAudioSourceManager());
    }

    static void addPlayer(String guildID, GuildMusicPlayer gmp) {
        guilds.put(guildID, gmp);
    }

    public static GuildMusicPlayer getPlayer(Guild guild) {
        // get static logger
        Logger log = LoggerFactory.getLogger(GuildMusicPlayer.class);

        // check to see if a player already exists
        GuildMusicPlayer musicPlayer = guilds.get(guild.getId());
        if (musicPlayer == null) {
            log.info("{}: Music Player does not exist for this guild. Creating...", guild.getName());
            musicPlayer = new GuildMusicPlayer(guild);
        }

        return musicPlayer;
    }

    public GuildMusicPlayer(Guild guild) {
        player = playerManager.createPlayer();
        handler = new AudioPlayerSendHandler(player);
        scheduler = new TrackScheduler(player, this);
        this.guild = guild;

        player.addListener(scheduler);
        player.setPaused(true);
        GuildMusicPlayer.addPlayer(guild.getId(), this);
    }

    public void joinChannel(VoiceChannel channel) {
        log.debug("{}: joining VoiceChannel \'{}\'...", guild.getName(), channel.getName());
        guild.getAudioManager().setSendingHandler(handler);
        guild.getAudioManager().openAudioConnection(channel);
    }

    public void leaveChannel() {
        log.debug("{}: leaving VoiceChannel \'{}\'...", guild.getName(),
                guild.getAudioManager().getConnectedChannel().getName());
        guild.getAudioManager().setSendingHandler(null);
        guild.getAudioManager().closeAudioConnection();
    }

    public boolean isInChannel() {
        if (guild.getAudioManager().getSendingHandler() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPaused() {
        return player.isPaused();
    }

    public void setPaused(boolean value) {
        player.setPaused(value);
    }

    public void skipTrack() {
        scheduler.nextTrack();
    }

    public TrackScheduler.Repeat getRepeat() {
        return scheduler.getRepeat();
    }

    public void setRepeat(TrackScheduler.Repeat repeat) {
        scheduler.setRepeat(repeat);
    }

    public Queue<AudioTrack> getQueue() {
        return scheduler.getQueue();
    }

    public AudioTrack getPlayingTrack() {
        return player.getPlayingTrack();
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
                    log.debug("{}: adding track {} to queue", guild.getName(), track.getInfo().title);
                    String message = "Added \"" + track.getInfo().title + "\" to the queue.";
                    channel.sendMessage(message).queue();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                StringBuilder messageBuilder = new StringBuilder("Added ");
                AudioTrack selected = playlist.getSelectedTrack();

                if (selected != null) {
                    trackLoaded(selected);
                    return;
                } else {
                    // Add the entire playlist
                    log.debug("{}: adding playlist \'{}\' to the queue", guild.getName(), playlist.getName());
                    for (AudioTrack track : playlist.getTracks()) {
                        scheduler.add(track);
                    }

                    messageBuilder.append(playlist.getTracks().size())
                            .append(" tracks from \"")
                            .append(playlist.getName())
                            .append("\"");
                }
                messageBuilder.append(" to the queue");

                if (channel != null) {
                    channel.sendMessage(messageBuilder.toString()).queue();
                }
            }

            @Override
            public void noMatches() {
                log.debug("{}: no matches for query \'{}\'", guild.getName(), search);
                if (channel != null) {
                    String message = "Could not find any matches for \"" + search + "\"";
                    channel.sendMessage(message).queue();
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                log.error("{}: exception trying to load track", guild.getName());
                if (channel != null) {
                    channel.sendMessage("Could not play").queue();
                }
            }
        });
    }
}
