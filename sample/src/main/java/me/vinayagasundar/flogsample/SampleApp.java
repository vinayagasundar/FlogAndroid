package me.vinayagasundar.flogsample;

import android.app.Application;

import me.vinayagasundar.flog.Flog;

/**
 * @author vinayagasundar
 */

public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Library and do the basic Setup
        Flog.init(this);
        Flog.setLogFilePrefix("Sample");
        Flog.setLoggable(BuildConfig.DEBUG);
        Flog.setWriteToFile(true);
    }
}
