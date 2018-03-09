package com.darkona.adventurebackpack.util.temp;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import com.darkona.adventurebackpack.reference.ModInfo;

/**
 * Created on 06.03.2018
 *
 * @author Ugachaga
 */
public class Log
{
    public static final Marker MOD_MARKER = MarkerManager.getMarker(ModInfo.MODID);

    private static Logger logger;

    public static void log(final Level level, final String format, final Object... data)
    {
        logger.printf(level, format, data);
    }

    public static void log(final Level level, final Throwable throwable, final String format, final Object... data)
    {
        if (logger.isEnabled(level))
        {
            logger.log(level, String.format(format, data), throwable);
        }
    }

    public static void log(final Level level, final Marker marker, final String format, final Object... data)
    {
        logger.printf(level, marker, format, data);
    }

    public static void log(final Level level, final Marker marker, final Throwable throwable, final String format, final Object... data)
    {
        if (logger.isEnabled(level, marker))
        {
            logger.log(level, marker, String.format(format, data), throwable);
        }
    }

    public static void error(final String format, final Object... data)
    {
        log(Level.ERROR, format, data);
    }

    public static void error(final Marker marker, final String format, final Object... data)
    {
        log(Level.ERROR, marker, format, data);
    }

    public static void error(final Throwable throwable, final String format, final Object... data)
    {
        log(Level.ERROR, throwable, format, data);
    }

    public static void error(final Marker marker, final Throwable throwable, final String format, final Object... data)
    {
        log(Level.ERROR, marker, throwable, format, data);
    }

    public static void warn(final String format, final Object... data)
    {
        log(Level.WARN, format, data);
    }

    public static void warn(final Marker marker, final String format, final Object... data)
    {
        log(Level.WARN, marker, format, data);
    }

    public static void info(final String format, final Object... data)
    {
        log(Level.INFO, format, data);
    }

    public static void info(final Marker marker, final String format, final Object... data)
    {
        log(Level.INFO, marker, format, data);
    }

    public static void info(final Throwable throwable, final String format, final Object... data)
    {
        log(Level.INFO, throwable, format, data);
    }

    public static void info(final Marker marker, final Throwable throwable, final String format, final Object... data)
    {
        log(Level.INFO, marker, throwable, format, data);
    }

    public static void setLogger(final Logger logger)
    {
        if (Log.logger != null)
        {
            throw new IllegalStateException("Attempt to replace logger");
        }

        Log.logger = logger;
    }
}
