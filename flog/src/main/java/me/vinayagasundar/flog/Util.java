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
