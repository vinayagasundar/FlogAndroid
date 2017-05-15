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

package me.vinayagasundar.flogsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.vinayagasundar.flog.Flog;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flog.i(TAG, "onCreate: ");

        for (long i = 0; i < 1000000L; i++) {

            if (i == 100L) {
                Flog.shutdown();
            }

            Log.i(TAG, "onCreate: " + i);
            Flog.i(TAG, "{\"widget\": {\n" +
                    "    \"debug\": \"on\",\n" +
                    "    \"window\": {\n" +
                    "        \"title\": \"Sample Konfabulator Widget\",\n" +
                    "        \"name\": \"main_window\",\n" +
                    "        \"width\": 500,\n" +
                    "        \"height\": 500\n" +
                    "    },\n" +
                    "    \"image\": { \n" +
                    "        \"src\": \"Images/Sun.png\",\n" +
                    "        \"name\": \"sun1\",\n" +
                    "        \"hOffset\": 250,\n" +
                    "        \"vOffset\": 250,\n" +
                    "        \"alignment\": \"center\"\n" +
                    "    },\n" +
                    "    \"text\": {\n" +
                    "        \"data\": \"Click Here\",\n" +
                    "        \"size\": 36,\n" +
                    "        \"style\": \"bold\",\n" +
                    "        \"name\": \"text1\",\n" +
                    "        \"hOffset\": 250,\n" +
                    "        \"vOffset\": 100,\n" +
                    "        \"alignment\": \"center\",\n" +
                    "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
                    "    }\n" +
                    "}}   ");
        }
    }
}
