package com.well.helloandroid.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.well.helloandroid.codec.Encoder;
import com.well.helloandroid.R;

import java.io.File;

/**
 * Created by liuwei on 5/25/16.
 */
public class MuxerActivity extends BaseActivity implements View.OnClickListener {
    private EditText ed_audio, ed_video;
    private Button bt_muxer;
    private static File ROOTPATH = Environment.getExternalStorageDirectory();//获取sd卡路径
    public static String TAG = "wellvideo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muxer);
        bt_muxer = (Button) findViewById(R.id.bt_muxer);
        bt_muxer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_muxer:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startMuxer();
                    }
                }).start();
                break;
        }
    }

    private void startMuxer() {
        String input = ROOTPATH.getPath() + "/sample.mp4";
        String output = ROOTPATH.getPath() + "/noSound.mp4";
        new Encoder().statMediaExtractor(input, output);
    }


}
