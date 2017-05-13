package me.vinayagasundar.flog;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * A helper class for working with File in the application
 * @author vinayagasundar
 */

final class FileHelper {

    private static final String TAG = "FileHelper";


    private static final String LOG_FOLDER = "log";
    private static final String LOG_FILE_STRING_FORMAT = "/%s/%s/%s_%s.log";
    private static final String LOG_DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss";


    /*package*/ @SuppressWarnings("ResultOfMethodCallIgnored")
    static File createLogFile(Context context, String prefix) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }

        if (TextUtils.isEmpty(prefix)) {
            throw new IllegalArgumentException("prefix is null");
        }

        File logFile = null;

        String folderName = Util.getAppName(context);

        if (folderName == null) {
            return null;
        }

        String filePath = String.format(Locale.ENGLISH, LOG_FILE_STRING_FORMAT, folderName, LOG_FOLDER,
                prefix, DateFormat.format(LOG_DATE_FORMAT, new Date()));

        File externalStorageDir = Environment.getExternalStorageDirectory();

        final String fullFilePath = externalStorageDir.toString() + filePath;

        Log.i(TAG, "createLogFile: " + fullFilePath);

        File file = new File(fullFilePath);

        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }

            logFile = file;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return logFile;
    }
}
