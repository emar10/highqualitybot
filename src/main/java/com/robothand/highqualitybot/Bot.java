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

        // utility commands
        api.addEventListener(new ShutdownCommand());
        api.addEventListener(new PingCommand());

        // music
        api.addEventListener(new PlayCommand());
        api.addEventListener(new PauseCommand());
        api.addEventListener(new NowPlayingCommand());
        api.addEventListener(new SkipCommand());
        api.addEventListener(new RepeatCommand());
        api.addEventListener(new QueueCommand());
    }

    public static JDA getAPI() {
        return api;
    }
}

