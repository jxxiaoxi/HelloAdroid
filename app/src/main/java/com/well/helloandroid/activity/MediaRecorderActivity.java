package com.well.helloandroid.activity;

import android.media.MediaRecorder;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.well.helloandroid.R;
import com.well.helloandroid.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MediaRecorderActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static String TAG = "MediaRecorderActivity";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mMediaRecorderRecording = false;
    LocalServerSocket lss;
    LocalSocket receiver, sender;
    private MediaRecorder mMediaRecorder;
    private int videoWidth = 1920;    //352
    private int videoHeight = 1080;    //288
    private int videoRate = 24; //15
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        InitSurfaceView();
    }

    private void InitSurfaceView() {
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surface_camera);
        mSurfaceView.setVisibility(View.VISIBLE);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mSurfaceHolder = surfaceHolder;
        if (!mMediaRecorderRecording) {
            InitLocalSocket();
            initVideo();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startVideoRecording();
                }
            }).start();

        }
    }

    private void InitLocalSocket() {
        try {
            lss = new LocalServerSocket("H264");
            receiver = new LocalSocket();
            receiver.connect(new LocalSocketAddress("H264"));
            receiver.setReceiveBufferSize(500000);
            sender = lss.accept();
            sender.setSendBufferSize(500000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean initVideo() {
        mFile = new File(getRecordingPath());
        if (mFile.exists()) {
            LogUtils.e(TAG, "File Exists,delete");
            mFile.delete();
        }


        if (mSurfaceHolder == null) {
            return false;
        }

        mMediaRecorderRecording = true;

        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置录制视频源为Camera
        //mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录制音频源为MIC
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 设置录制完成后视频的封装格式
        mMediaRecorder.setVideoFrameRate(videoRate);// 设置录制的视频帧率
        mMediaRecorder.setVideoSize(videoWidth, videoHeight);// 设置视频录制的分辨率
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 设置录制的视频编码
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface()); //设置视频的预览界面
        mMediaRecorder.setMaxDuration(0);//设置最大记录时长，单位为毫秒
        mMediaRecorder.setMaxFileSize(Integer.MAX_VALUE);
        mMediaRecorder.setOutputFile(mFile.getAbsolutePath());// 设置视频文件输出的路径
        //mMediaRecorder.setOutputFile(sender.getFileDescriptor());

        try {
            LogUtils.e(TAG, " prepare");
            mMediaRecorder.prepare();
            LogUtils.e(TAG, " start");
            mMediaRecorder.start();
            LogUtils.e(TAG, " end");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
        }

        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            if (mMediaRecorderRecording) {
                mMediaRecorder.stop();
                mMediaRecorderRecording = false;
            }
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    private void startVideoRecording() {
        try {
            //receiver.getInputStream();
            svaeVideo(receiver.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getRecordingPath() {
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/hellAndroid/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        String fileName = (String) DateFormat.format("yyyy-MM-dd",
                System.currentTimeMillis());
        return filePath + fileName + ".mp4";
    }

    public void svaeVideo(InputStream inputStream) {
        String filePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/hellAndroid/";
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        String fileName = (String) DateFormat.format("yyyy-MM-dd",
                System.currentTimeMillis());


        File file = new File(filePath + fileName + "other");
        try {
            FileOutputStream opt = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                opt.write(buffer, 0, len);
            }
            opt.flush();
            opt.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
