package me.vinayagasundar.flog;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Util method for library
 *
 * @author vinayagasundar
 */

final class Util {

    private static final String TAG = "Util";

    @Nullable
    /*package*/ static String getAppName(Context context) {
        if (context == null) {
            return null;
        }

        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo;

        try {
            applicationInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            CharSequence appName = pm.getApplicationLabel(applicationInfo);

            if (!TextUtils.isEmpty(appName)) {
                return appName.toString();
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getAppName: ", e);
        }

        return null;
    }
}
