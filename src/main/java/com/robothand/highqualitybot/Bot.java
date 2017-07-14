package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.*;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.FileNotFoundException;

/**
 * main class
 */
public class Bot {
    private static JDA api;
    public static Config config;
    public static final String VERSION = "0.1";

    public static void main(String[] args) {

        System.out.println("Attempting to read \"config.cfg\"");

        try {
            config = new Config("config.cfg");


        } catch (FileNotFoundException e) {
            System.err.println("FATAL: could not find file \"config.txt\" in " + System.getProperty("user.dir"));
            System.exit(1);
        }

        System.out.println("Connecting to Discord...");
        try {
            api = new JDABuilder(AccountType.BOT).setToken(config.TOKEN).buildAsync();
        } catch (Exception e) {
            System.err.println("FATAL: Could not connect to Discord");
            System.exit(1);
        }

        // prepare the audio sources
        GuildMusicPlayer.setupSources();

        // commands
        Commands commands = Commands.getInstance();

        // utility commands
        commands.addCommand(new HelpCommand());
        commands.addCommand(new StatusCommand());
        commands.addCommand(new ShutdownCommand());
        commands.addCommand(new PingCommand());

        // music
        commands.addCommand(new PlayCommand());
        commands.addCommand(new PauseCommand());
        commands.addCommand(new NowPlayingCommand());
        commands.addCommand(new SkipCommand());
        commands.addCommand(new RepeatCommand());
        commands.addCommand(new QueueCommand());
        commands.addCommand(new ClearCommand());

        // setup listeners
        commands.setupListeners(api);

    }

    public static JDA getAPI() {
        return api;
    }
}

