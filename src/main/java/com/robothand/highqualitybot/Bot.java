package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.*;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.robothand.highqualitybot.permission.PermissionManager;
import com.robothand.highqualitybot.util.SimpleLogWrapper;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.io.FileNotFoundException;

/**
 * main class
 */
public class Bot {
    private static JDA api;
    public static final String VERSION = "0.2";

    public static void main(String[] args) {
        // mute JDA's logging and plug in our wrapper
        SimpleLog.LEVEL = SimpleLog.Level.OFF;
        SimpleLog.addListener(new SimpleLogWrapper());

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

        System.out.println("Attempting to read \"config.cfg\"");

        try {
            Config.loadConfig("config.cfg");


        } catch (FileNotFoundException e) {
            System.err.println("FATAL: could not find file \"config.cfg\" in " + System.getProperty("user.dir"));
            System.exit(1);
        }

        System.out.println("Connecting to Discord...");
        try {
            api = new JDABuilder(AccountType.BOT).setToken(Config.TOKEN).buildBlocking();
        } catch (Exception e) {
            System.err.println("FATAL: Could not connect to Discord");
            System.exit(1);
        }

        // read permissions
        PermissionManager.instance().readGroups();

        // setup listeners
        commands.setupListeners(api);

    }

    public static JDA getAPI() {
        return api;
    }
}

