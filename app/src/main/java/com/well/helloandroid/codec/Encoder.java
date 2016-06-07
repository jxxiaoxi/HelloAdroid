package com.well.helloandroid.codec;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;

import com.well.helloandroid.utils.LogUtils;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Encoder {
    private static final int TIMEOUT_US = 10000;
    private MediaMuxer mMediaMuxer;
    private MediaExtractor mMediaExtractor;
    public static String TAG = "wellvideo";

    /*
    *
    *  将video中的视频和音频分开
    *
    */

    public void statMediaExtractor(String inputVideoPath, String outputVideoPath) {

        int mVideoTrackIndex = -1;
        mMediaExtractor = new MediaExtractor();
        try {
            LogUtils.e(TAG, "inputVideoPath " + inputVideoPath);
            mMediaExtractor.setDataSource(inputVideoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int tracks = mMediaExtractor.getTrackCount();
        LogUtils.e(TAG, "tracks " + tracks);
        for (int i = 0; i < tracks; i++) {
            MediaFormat format = mMediaExtractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (!mime.startsWith("video/")) {
                continue;
            }
            mMediaExtractor.selectTrack(i);
            try {
                mMediaMuxer = new MediaMuxer(outputVideoPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mVideoTrackIndex = mMediaMuxer.addTrack(format);
            mMediaMuxer.start();
        }

        BufferInfo info = new BufferInfo();
        LogUtils.e(TAG, "BufferInfo " +info.presentationTimeUs  + " ====> "+info.size);

        info.presentationTimeUs = 0;
        boolean sawEOS = false;
        ByteBuffer buffer = ByteBuffer.allocate(500 * 1024);
        while (!sawEOS) {
            int sampleSize = mMediaExtractor.readSampleData(buffer, 0);
            LogUtils.e(TAG, "sampleSize  : " + sampleSize);
            if (sampleSize < 0) {
                sawEOS = true;
                info.size = 0;
                break;
            }else {
                mMediaExtractor.advance();
                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                if(mMediaExtractor.getSampleTime() > 0) {
                    info.presentationTimeUs = mMediaExtractor.getSampleTime();
                }
                mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
            }
        }

        if (mMediaExtractor != null) {
            mMediaExtractor.release();
        }
        if (mMediaMuxer != null) {
            mMediaMuxer.stop();
            mMediaMuxer.release();
        }
    }
}
