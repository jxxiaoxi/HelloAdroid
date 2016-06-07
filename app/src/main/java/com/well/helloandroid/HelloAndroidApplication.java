package com.well.helloandroid;

import android.app.Application;

import com.well.helloandroid.utils.FileUtils;

/**
 * Created by liuwei on 6/6/16.
 */
public class HelloAndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileUtils.createFileFolder();
    }
}


