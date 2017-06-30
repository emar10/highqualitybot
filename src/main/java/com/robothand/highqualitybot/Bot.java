package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.*;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * main class
 */
public class Bot {
    static JDA api;
    public static String PREFIX;
    public static final String VERSION = "0.1";

    public static void main(String[] args) {
        File config;
        String token;

        System.out.println("Attempting to read \"config.txt\"");
        config = new File("config.txt");
        token = null;

        try {
            Scanner s = new Scanner(config);

            // token
            token = s.nextLine();

            // prefix
            PREFIX = s.nextLine();

        } catch (FileNotFoundException e) {
            System.err.println("FATAL: could not find file \"config.txt\"");
            System.exit(1);
        }

        System.out.println("Connecting to Discord...");
        try {
            api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
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
        commands.addListeners(api);

    }

    public static JDA getAPI() {
        return api;
    }
}

