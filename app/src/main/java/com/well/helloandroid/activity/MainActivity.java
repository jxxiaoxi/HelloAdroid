package com.well.helloandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.well.helloandroid.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button tv_muxer, bt_recordVideo, bt_mediarecorder, bt_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv_muxer = (Button) findViewById(R.id.tv_muxer);
        tv_muxer.setOnClickListener(this);
        bt_recordVideo = (Button) findViewById(R.id.bt_recordVideo);
        bt_recordVideo.setOnClickListener(this);
        bt_mediarecorder = (Button) findViewById(R.id.bt_mediarecorder);
        bt_mediarecorder.setOnClickListener(this);
        bt_home = (Button) findViewById(R.id.bt_home);
        bt_home.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_muxer:
                startActivity(new Intent(MainActivity.this, MuxerActivity.class));
                break;
            case R.id.bt_recordVideo:
                startActivity(new Intent(MainActivity.this, VideoRecordActivity.class));
                break;
            case R.id.bt_mediarecorder:
                startActivity(new Intent(MainActivity.this, MediaRecorderActivity.class));
                break;
            case R.id.bt_home:
                startActivity(new Intent(MainActivity.this, HomeFrameActivity.class));
                break;
        }

    }
}
