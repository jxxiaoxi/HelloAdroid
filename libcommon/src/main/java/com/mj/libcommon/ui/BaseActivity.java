package com.mj.libcommon.ui;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mj.libcommon.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /*
    *
    *  6.0以上版本，动态申请权限
    *
    * */
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT,
                    Manifest.permission.CAPTURE_VIDEO_OUTPUT
            };
            requestPermissions(permissions, 123);
        }
    }
}
