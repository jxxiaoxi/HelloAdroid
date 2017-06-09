package com.mj.libcommon.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by liuwei on 12/21/16.
 */

public class LogUtils{

    public static void e(String tag, String content) {
            Log.e(tag, content);
    }

    public static void e(Context context, String content) {
        Log.e(context.getClass().getName(), content);
    }
}
