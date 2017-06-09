package com.mj.libcommon.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuwei on 6/8/17.
 */

public class BaseCache {
    private SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    public BaseCache() {
    }

    public BaseCache(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name,
                context.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象
        mEditor = mSharedPreferences.edit();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);

    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);

    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, 0);
        mEditor.commit();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);

    }

}
