package com.well.helloandroid.utils;

import android.util.Log;

public class LogUtils {
    public static boolean islog = true;

    public static void e(String tag, String content) {
        if (islog)
            Log.e(tag, content);
    }

}
