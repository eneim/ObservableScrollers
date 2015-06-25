package im.ene.lab.observablescrollers.lib.util;

import android.util.Log;

import im.ene.lab.observablescrollers.lib.BuildConfig;

/**
 * Created by eneim on 5/29/15.
 */
public class LogHelper {

    /**
     * Don't allow to initialize this class. We use it statically
     */
    private LogHelper() {}

    /**
     * debug mode is ON by default
     */
    private static boolean D = BuildConfig.DEBUG;

    /**
     * LOG Prefix
     */
    private static final String LOG_PREFIX = "im.ene.lab.obs_";

    /**
     * Manually setup debug mode
     * @param debugMode
     */
    public static void debugMode(boolean debugMode) {
        D = debugMode;
    }

    /**
     * Create local log tag for class
     * @param clazz
     * @return
     */
    public static final String createLogTag(Class clazz) {
        return LOG_PREFIX + clazz.getSimpleName();
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (D) Log.d(tag, message);
    }

    /**
     *
     * @param tag
     * @param message
     * @param er
     */
    public static void d(String tag, String message, Throwable er) {
        if (D) Log.d(tag, message, er);
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (D) Log.i(tag, message);
    }

    /**
     *
     * @param tag
     * @param message
     * @param er
     */
    public static void i(String tag, String message, Throwable er) {
        if (D) Log.i(tag, message, er);
    }

    /**
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (D) Log.e(tag, message);
    }

    /**
     *
     * @param tag
     * @param message
     * @param er
     */
    public static void e(String tag, String message, Throwable er) {
        if (D) Log.e(tag, message, er);
    }
}
