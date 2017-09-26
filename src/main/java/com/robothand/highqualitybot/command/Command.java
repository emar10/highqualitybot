package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.permission.PermissionManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command.java
 * Abstract for all Command objects.
 */
public abstract class Command extends ListenerAdapter {
    Logger log = LoggerFactory.getLogger(this.getClass());

    // Returns an array of this Command's aliases
    public abstract String[] getNames();
    // Returns a full description of this Command's usage
    public abstract String getDescription();
    // Returns a shortened version of this Command's usage
    public abstract String getShortDesc();
    // Code to be executed when this Command is triggered
    public abstract void execute(MessageReceivedEvent event, String[] args);

    // Asks the PermissionManager if a user has permission to run this Command, executing if so
    public void requestExecute(MessageReceivedEvent event, String[] args) {
        // run command if we have permission
        if (PermissionManager.instance().hasPermission(event.getMember(), this)) {
            log.info("{} executing '{}'", event.getAuthor().getName(), this.getNames()[0]);
            execute(event, args);
        } else {
            log.info("{} failed executing '{}': insufficient permissions", event.getAuthor().getName(), this.getNames()[0]);
            event.getChannel().sendMessage("You do not have permission to run that command.").queue();
        }
    }
}
