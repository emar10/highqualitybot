package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Config;
import com.robothand.highqualitybot.permission.PermissionManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Abstract for all Command classes.
 */
public abstract class Command extends ListenerAdapter {

    public abstract String[] getNames();
    public abstract String getDescription();
    public abstract String getShortDesc();
    public abstract void execute(MessageReceivedEvent event, String[] args);

    public void requestExecute(MessageReceivedEvent event, String[] args) {
        // run command if we have permission
        if (PermissionManager.instance().hasPermission(event.getMember(), this)) {
            execute(event, args);
        } else {
            event.getChannel().sendMessage("You do not have permission to run that command.").queue();
        }
    }
}
