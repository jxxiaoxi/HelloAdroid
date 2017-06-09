package com.mj.helloapp.cache;

import android.content.Context;

import com.mj.libcommon.cache.BaseCache;

/**
 * Created by liuwei on 6/8/17.
 */

public class DataCache extends BaseCache {
    private static DataCache mDataCache = null;

    public static synchronized DataCache getInstens(Context context) {
        if (mDataCache == null) {
            mDataCache = new DataCache(context, context.getApplicationInfo().name);
        }

        return mDataCache;
    }

    private DataCache(Context context, String name) {
        super(context, name);
    }

}
