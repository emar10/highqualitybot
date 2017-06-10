package com.robothand.highqualitybot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 * main class
 */
public class Bot {
    static JDA api;
    static boolean running;

    public static void main(String[] args) {
        System.out.println("Connecting to Discord...");
        try {
            api = new JDABuilder(AccountType.BOT).setToken("MjY4MjExODI2MzU1ODYzNTUz.DByjTQ.1ycw5crEGVZGo-ckyGakydsno0o").buildAsync();
        } catch (Exception e) {
            System.err.println("FATAL: Could not connect to Discord");
            System.exit(1);
        }

        api.addEventListener(new TestCommand());
    }

    public static void shutdown() {
        api.shutdown();
    }

}
