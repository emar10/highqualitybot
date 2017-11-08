package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"log", "logging"};
    }

    @Override
    public String getDescription() {
        return "Sets the global logging level for the bot. Available options are `TRACE`, `DEBUG`, `INFO`, `WARN`, " +
                "and `ERROR`.\n" +
                "Usage: " + Config.PREFIX + "log <level>";
    }

    @Override
    public String getShortDesc() {
        return "Sets console logging level.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        String message;
        ch.qos.logback.classic.Logger root =
                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        if (args.length > 2) {
            root.setLevel(ch.qos.logback.classic.Level.toLevel(args[1], root.getLevel()));
        }

        channel.sendMessage("Log level is " + root.getLevel()).queue();
    }
}
