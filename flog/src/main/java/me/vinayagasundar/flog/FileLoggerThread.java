/*
 *    Copyright 2017  vinayagasundar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.vinayagasundar.flog;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * We use this thread for Lazy logging.
 *
 * @author vinayagasundar
 */

final class FileLoggerThread extends Thread {

    private static final String TAG = "FileLoggerThread";

    /**
     * Default size of Blocking Queue
     */
    private static final int DEFAULT_SIZE = 50;

    /**
     * String value used to stop the Blocking Queue
     */
    private static final String FINISH = "finish";


    private static FileLoggerThread mInstance;

    /**
     * Queue contain the Logging string
     */
    private BlockingQueue<String> mLogQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);


    /**
     * Writer used to write on File
     */
    private BufferedWriter mWriter;


    private volatile boolean mIsInterrupted = false;

    private volatile boolean mIsShutDown = false;


    private FileLoggerThread() {
        // To avoid Object creation
    }


    private FileLoggerThread(File file) {
        this();
        if (file == null) {
            throw new IllegalArgumentException("file should not be null");
        }

        OutputStreamWriter outStreamWriter;

        try {
            outStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            mWriter = new BufferedWriter(outStreamWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        start();
    }


    /*package*/
    static void init(File file) {
        if (mInstance == null) {
            mInstance = new FileLoggerThread(file);
        }
    }


    /*package*/
    static FileLoggerThread getInstance() throws IllegalAccessException {
        if (mInstance == null) {
            throw new IllegalAccessException("You should call " +
                    "FileLoggerThread.init() before this");
        }
        return mInstance;
    }


    @Override
    public void run() {
        super.run();

        String log;

        try {
            while ((log = mLogQueue.take()) != FINISH) {
                mWriter.newLine();
                mWriter.write(log);
                mWriter.flush();
            }

            mIsShutDown = true;

            Log.i(TAG, "run: Shutdown");

        } catch (InterruptedException ex) {
            mIsInterrupted = true;
            Log.e(TAG, "run: InterruptedException ", ex);
        } catch (IOException ex) {
            Log.e(TAG, "run: IOException ", ex);
        } finally {
            try {
                mWriter.close();
            } catch (IOException ignored) {
            }
        }
    }


    /**
     * Log the value into file
     *
     * @param log Content of the log
     */
    void log(String log) {
        if (TextUtils.isEmpty(log) || mIsInterrupted || mIsShutDown) {
            return;
        }

        try {
            mLogQueue.put(log);
        } catch (InterruptedException ignored) {
        }
    }


    /**
     * Stop the logger Thread
     */
    void shutdown() {
        log(FINISH);
    }
}
