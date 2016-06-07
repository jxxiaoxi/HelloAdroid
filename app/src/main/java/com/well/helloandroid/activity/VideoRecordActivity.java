package com.well.helloandroid.activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.TextureView;

import com.well.helloandroid.codec.VideoEncoderFromBuffer;
import com.well.helloandroid.utils.FileUtils;
import com.well.helloandroid.utils.LogUtils;

import java.io.IOException;
import java.util.List;
import com.well.helloandroid.R;

/**
 * Created by liuwei on 5/30/16.
 */
public class VideoRecordActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private static final String TAG = "VideoRecordActivity";
    private TextureView mTextureView;
    private Camera mCamera;
    private static int HIGH = 1080;
    private static int WIDTH = 1920;
    private int mBeferrSize;
    private VideoEncoderFromBuffer mVideoEncoderFromBuffer;
    private byte[] mBytes;
    private boolean isPreview = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videorecord);
        mTextureView = (TextureView) findViewById(R.id.texture);
        mTextureView.setSurfaceTextureListener(this);
        startCamera();
    }

    private void startCamera() {
        String filePath = FileUtils.getFileFolder();
        LogUtils.e(TAG,filePath);
        mVideoEncoderFromBuffer = new VideoEncoderFromBuffer(filePath, WIDTH, HIGH);
        mCamera = Camera.open(0);
        initCamera();
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        LogUtils.e(TAG, "onSurfaceTextureAvailable");
        mCamera.addCallbackBuffer(new byte[mBeferrSize]);
        mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, Camera camera) {
                mBytes = bytes;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mVideoEncoderFromBuffer.startEncoder(mBytes);
                    }
                }).start();

                mCamera.addCallbackBuffer(bytes);//这里的参数，不要new byte[mBeferrSize]，否则会照成内存泄漏。直接用 bytes则不会出现问题
            }
        });

        try {
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
            isPreview = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        LogUtils.e(TAG, "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        LogUtils.e(TAG, "onSurfaceTextureDestroyed");
        mCamera.stopPreview();
        mCamera.release();//加上这句，就OK！
        mCamera = null;
        isPreview = false;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        // Ignored, Camera does all the work for us
        //LogUtils.e(TAG, "onSurfaceTextureUpdated");
    }

    private void initCamera() {
        if (isPreview) {
            mCamera.stopPreview();
        }
        if (null != mCamera) {
            mBeferrSize = WIDTH * HIGH * 3 / 2;
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            LogUtils.e(TAG, "supportedPreviewSizes  :  " + cameraSizeToSting(supportedPreviewSizes));
            parameters.setPreviewSize(WIDTH, HIGH);
            parameters.setPreviewFormat(ImageFormat.NV21);
            mCamera.setParameters(parameters);
        }
    }

    public static String cameraSizeToSting(Iterable<Camera.Size> sizes) {
        StringBuilder s = new StringBuilder();
        for (Camera.Size size : sizes) {
            if (s.length() != 0)
                s.append(",");
            s.append(size.width).append('x').append(size.height);
        }
        return s.toString();
    }
}
