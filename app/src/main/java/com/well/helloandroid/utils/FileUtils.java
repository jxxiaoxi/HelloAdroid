package com.well.helloandroid.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by liuwei on 6/6/16.
 */
public class FileUtils {
    private static File mFile;

    public static void createFileFolder() {
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + "/WellVideo";
        mFile = new File(path);
        if (!mFile.exists())
            mFile.mkdir();
    }

    public static String getFileFolder() {
        return  mFile.getAbsolutePath();
    }
}
