package me.vinayagasundar.flog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * {@link Flog} is used to save all your {@link android.util.Log} message in a file on SD Card
 *
 * @author vinayagasundar
 */

public final class Flog {

    private static final String TAG = "Flog";


    /**
     * Default Prefix used in the Log file
     */
    private static final String DEFAULT_LOG_FILE_PREFIX = "Flog";

    /**
     * Default No of log files can be stored in SD card
     */
    private static final int DEFAULT_LOG_FILE_COUNT = 10;


    /**
     * Priority constant for the println method; use Log.v.
     */
    private static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    private static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    private static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    private static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    private static final int ERROR = 6;


    /**
     * Default format used in Each Log statement
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";


    /**
     * To format date in log file
     */
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT,
            Locale.getDefault());


    /**
     * Flag value set enable / disable log
     * By default it'll be <code>false</code>
     */
    private static boolean mIsLoggable = false;


    /**
     * Flag value to write Log value into File
     * By default it'll be <code>true</code>
     */
    private static boolean mIsWriteToFile = true;


    /**
     * Log file Prefix String. it'll followed by current timestamp
     * default value is {@link #DEFAULT_LOG_FILE_PREFIX}
     */
    private static String mLogFilePrefix = DEFAULT_LOG_FILE_PREFIX;


    /**
     * Max No of Log file stored on the SD cards
     */
    private static int mLogFileCount = DEFAULT_LOG_FILE_COUNT;


    /**
     * It'll hold the Application {@link Context}
     */
    private static Context mAppContext;


    /**
     * File which is store the Log information
     */
    private static File mFile;



    private Flog() {
        // To avoid object creation
    }


    /**
     * Initialize the {@link Flog} in the {@link android.app.Application} of the your application
     *
     * <pre>
     *       public void onCreate() {
     *           super.onCreate();
     *           // Initialize Flog library
     *           Flog.init(this);
     *       }
     * </pre>
     * @param context {@link android.app.Application} context used to initialize library
     */
    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context should not be null");
        }

        Log.i(TAG, "init: ");

        mAppContext = context.getApplicationContext();
    }


    /**
     * Set the Log file Prefix by default
     * it'll use the default prefix {@link #DEFAULT_LOG_FILE_PREFIX}
     * @param prefix String used as prefix for Log file
     */
    public static void setLogFilePrefix(String prefix) {
        Log.i(TAG, "setLogFilePrefix: " + prefix);

        if (TextUtils.isEmpty(prefix)) {
            Log.e(TAG, "setLogFilePrefix: prefix is null");
            return;
        }

        mLogFilePrefix = prefix;
    }


    /**
     * Set Loggable is enabled or not for App.
     * Use the logging only in debug build
     * @param enable boolean value decided logging enabled for the application or not
     */
    public static void setLoggable(boolean enable) {
        Log.i(TAG, "setLoggable: " + enable);
        mIsLoggable = enable;
    }


    /**
     * To set Log need to write on file or not if it's false
     * we won't create a file for current session
     * @param enable <code>true</code> means it'll write log in file otherwise skip File write
     */
    public static void setWriteToFile(boolean enable) {
        Log.i(TAG, "setWriteToFile: " + enable);
        mIsWriteToFile = enable;
    }


    /**
     * Shut down the logging
     */
    public static void shutdown() {
        if (mFile == null) {
            return;
        }

        try {
            FileLoggerThread.getInstance().shutdown();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        log(VERBOSE, tag, msg);
    }

    /**
     * Send a {@link Log#VERBOSE} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        log(VERBOSE, tag, msg, tr);
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        log(DEBUG, tag, msg);
    }

    /**
     * Send a {@link Log#DEBUG} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        log(DEBUG, tag, msg, tr);
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        log(INFO, tag, msg);
    }

    /**
     * Send a {@link Log#INFO} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        log(INFO, tag, msg, tr);
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        log(WARN, tag, msg);
    }

    /**
     * Send a {@link Log#WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        log(WARN, tag, msg, tr);
    }


    /**
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        log(ERROR, tag, msg);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        log(ERROR, tag, msg, tr);
    }


    private static void log(int logType, String tag, String message) {
        log(logType, tag, message, null);
    }

    private static void log(int logType, String tag, String message, Throwable tr) {
        if (!mIsLoggable) {
            return;
        }

        switch (logType) {
            case VERBOSE:
                if (tr == null) {
                    Log.v(tag, message);
                } else {
                    Log.v(tag, message, tr);
                }
                break;

            case DEBUG:
                if (tr == null) {
                    Log.d(tag, message);
                } else {
                    Log.d(tag, message, tr);
                }
                break;

            case INFO:
                if (tr == null) {
                    Log.i(tag, message);
                } else {
                    Log.i(tag, message, tr);
                }
                break;

            case WARN:
                if (tr == null) {
                    Log.w(tag, message);
                } else {
                    Log.w(tag, message, tr);
                }
                break;

            case ERROR:
                if (tr == null) {
                    Log.e(tag, message);
                } else {
                    Log.e(tag, message, tr);
                }
                break;
        }


        if (!canWriteOnFile()) {
            return;
        }


        if (mFile == null) {
            mFile = FileHelper.createLogFile(mAppContext, mLogFilePrefix);
            FileLoggerThread.init(mFile);


            String packageName = mAppContext.getApplicationContext().getPackageName();

            try {
                PackageInfo packageInfo = mAppContext.getPackageManager().getPackageInfo(packageName,
                        0);

                if (packageInfo != null) {
                    String appStrBuilder = ("===============================================" +
                            "========\n") +
                            "App Package : " +
                            packageName +
                            "\n" +
                            "Version Code : " +
                            packageInfo.versionCode +
                            "\n" +
                            "Version Name : " +
                            packageInfo.versionName +
                            "\n" +
                            "Date & Time  : " +
                            mDateFormat.format(new Date()) +
                            "\n" +
                            "===============================================" +
                            "========\n";


                    FileLoggerThread.getInstance().log(appStrBuilder);
                }

            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "log: PackageManager.NameNotFoundException ", e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "log: IllegalAccessException ", e);
            }
        }


        StringBuilder builder = new StringBuilder();
        builder.append(mDateFormat.format(new Date()));
        builder.append(" ");
        builder.append(tag);
        builder.append(" ");
        builder.append(message);

        if (tr != null) {
            builder.append(" ");
            builder.append(tr.toString());
        }

        try {
            FileLoggerThread.getInstance().log(builder.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * It'll check app has the write Permission on the SD card and user Enable the File Write
     * @return true if both Write permission & file write enabled otherwise false
     */
    private static boolean canWriteOnFile() {
        boolean hasWritePermission = ActivityCompat.checkSelfPermission(mAppContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        Log.i(TAG, "canWriteOnFile: Write Permission " + hasWritePermission);

        return hasWritePermission && mIsWriteToFile;
    }

}
