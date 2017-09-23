package com.robothand.highqualitybot;

import com.robothand.highqualitybot.command.*;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import com.robothand.highqualitybot.permission.PermissionManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

/**
 * Bot.java
 * Main class, drives all the fun
 */
public class Bot {
    private static JDA api;
    private static String CONFIGNAME = "config.cfg";
    public static final String VERSION = "0.2";

    public static void main(String[] args) {
        // get logger
        Logger log = LoggerFactory.getLogger(Bot.class);
        log.info("High Quality Bot v{}", VERSION);

        // prepare the audio sources
        log.info("Setting up audio sources...");
        GuildMusicPlayer.setupSources();

        // commands
        log.info("Initializing commands...");
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

        // load main configuration
        log.info("Attempting to read file \"{}\"...", CONFIGNAME);
        try {
            Config.loadConfig(CONFIGNAME);
        } catch (FileNotFoundException e) {
            log.error("FATAL: could not find file \"{}\" in {}", CONFIGNAME, System.getProperty("user.dir"));
            System.exit(1);
        }

        log.info("Connecting to Discord...");
        try {
            api = new JDABuilder(AccountType.BOT).setToken(Config.TOKEN).buildBlocking();
        } catch (Exception e) {
            log.error("FATAL: Could not connect to Discord");
            System.exit(2);
        }

        // read permissions
        log.info("Reading permissions...");
        PermissionManager.instance().readGroups();

        // setup listeners
        log.info("Setting up command listener...");
        api.addEventListener(commands);

        log.info("Finished starting up!");
    }

    public static JDA getAPI() {
        return api;
    }
}

