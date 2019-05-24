package com.aphrodite.demo.utils;

import com.aphrodite.demo.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by Aphrodite on 2016/9/29.
 */

public final class LogUtils {
    /**
     * Send a DEBUG log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.d(tag, msg);
        }
    }

    /**
     * Send a DEBUG log message.
     */
    public static void d(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.d(msg);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String tag, String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.d(tag, msg, thr);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.d(msg, thr);
        }
    }

    /**
     * Send a ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(tag, msg);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(msg);
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String tag, String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(tag, msg, thr);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(msg, thr);
        }
    }

    /**
     * Send a INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.i(tag, msg);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.i(msg);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String tag, String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.i(tag, msg, thr);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.i(msg, thr);
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.v(tag, msg);
        }
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.v(msg);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String tag, String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.v(tag, msg, thr);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.v(msg, thr);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.w(tag, msg);
        }
    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Logger.w(msg);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String tag, String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.w(tag, msg, thr);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr) {
        if (BuildConfig.IS_DEBUG) {
            Logger.w(msg, thr);
        }
    }
}
