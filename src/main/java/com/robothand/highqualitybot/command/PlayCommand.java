package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
 */
public class PlayCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[] {"play"};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getShortDesc() {
        return null;
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        // Get player for this guild
        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        // join sender's channel if player is not already in one
        if (!musicPlayer.isInChannel()) {
            musicPlayer.joinChannel(event.getMember().getVoiceState().getChannel());
        }

        if (args.length > 1) {
            String search = "";
            for (int i = 1; i < args.length; i++) {
                search = search.concat(args[i]);
            }

            musicPlayer.playTrack(search, channel);
        } else {
            if (musicPlayer.isPaused()) {
                musicPlayer.setPaused(false);
                channel.sendMessage("Player resumed.").queue();
            } else {
                channel.sendMessage("The player is already running!").queue();
            }
        }
    }
}