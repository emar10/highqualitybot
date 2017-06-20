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

        // help command
        HelpCommand help = new HelpCommand();
        api.addEventListener(help.addCommand(help));

        // utility commands
        api.addEventListener(help.addCommand(new ShutdownCommand()));
        api.addEventListener(help.addCommand(new PingCommand()));

        // music
        api.addEventListener(help.addCommand(new PlayCommand()));
        api.addEventListener(help.addCommand(new PauseCommand()));
        api.addEventListener(help.addCommand(new NowPlayingCommand()));
        api.addEventListener(help.addCommand(new SkipCommand()));
        api.addEventListener(help.addCommand(new RepeatCommand()));
        api.addEventListener(help.addCommand(new QueueCommand()));

    }

    public static JDA getAPI() {
        return api;
    }
}

