package com.robothand.highqualitybot.util;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimpleLogWrapper.java
 * Intercepts logging from JDA's SimpleLog and sends it through to SLF4J
 */
public class SimpleLogWrapper implements SimpleLog.LogListener {
    private final Logger logger = LoggerFactory.getLogger(JDA.class);

    @Override
    public void onLog(SimpleLog log, SimpleLog.Level logLevel, Object message) {
       switch (logLevel) {
           case TRACE:
               logger.trace(message.toString());
               break;

           case DEBUG:
               logger.debug(message.toString());
               break;

           case INFO:
               logger.info(message.toString());
               break;

           case WARNING:
               logger.warn(message.toString());
               break;

           case FATAL:
               logger.error(message.toString());
               break;
       }
    }

    @Override
    public void onError(SimpleLog log, Throwable err) {
        logger.error("Exception", err);
    }
}
