package com.well.helloandroid.codec;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaMuxer;

import com.well.helloandroid.utils.LogUtils;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by liuwei on 6/2/16.
 */
public class VideoEncoderFromBuffer {
    private static final String VIDEO_MIME_TYPE = "video/avc";

    private int mWidth, mHeight;
    private MediaCodec mCodec;
    private MediaMuxer mMuxer;
    private static int TIMEOUTUS = 1000;
    private int mTrackIndex = -1;
    private boolean mMuxerStarted;
    private static final String TAG = "VideoEncoderFromBuffer";

    public VideoEncoderFromBuffer(String filePath, int width, int height) {

        this.mWidth = width;
        this.mHeight = height;
        //初始化 MediaCodec
        MediaCodecInfo codecInfo = selectCodec(VIDEO_MIME_TYPE);
        try {
            mCodec = MediaCodec.createByCodecName(codecInfo.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建视频 MediaFormat
        MediaFormat videoFormat = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, this.mWidth, this.mHeight);
        videoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
        videoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
        videoFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height);
        videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);

        mCodec.configure(videoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mCodec.start();

        //保存文件的名字
        String fileName = filePath + mWidth + "x"
                + mHeight + ".mp4";

        //初始化 MediaMuxer
        try {
            mMuxer = new MediaMuxer(fileName, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTrackIndex = -1;
        mMuxerStarted = false;

    }

    public void startEncoder(byte[] bytes) {
        ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
        LogUtils.e(TAG, "inputBuffers length  : " + inputBuffers.length);
        ByteBuffer[] outputBuffers = mCodec.getOutputBuffers();
        int inputBufferId = mCodec.dequeueInputBuffer(TIMEOUTUS);
        if (inputBufferId >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferId];//申请一个可以在应索引位置写入数据的ByteBuffer
            inputBuffer.put(bytes);//把我们需要处理的数据，put到ByteBuffer 中
            mCodec.queueInputBuffer(inputBufferId, 0, bytes.length, System.nanoTime() / 1000, 0); //编码
        } else {
            LogUtils.e(TAG, "NO InputBuffer DATA");
        }

        MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mCodec.dequeueOutputBuffer(mBufferInfo, TIMEOUTUS);
        LogUtils.e(TAG, "outputBufferIndex :  " + outputBufferIndex);
        while (outputBufferIndex >= 0) {
            if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                // 没有可用的输出
                LogUtils.e(TAG, "no output from encoder available");
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                //编码输出的buffer 已经改变
                outputBuffers = mCodec.getOutputBuffers();
                LogUtils.e(TAG, "encoder output buffers changed");
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                //没找到正确的 encoder
                MediaFormat newFormat = mCodec.getOutputFormat();
                mTrackIndex = mMuxer.addTrack(newFormat);
                mMuxer.start();
                mMuxerStarted = true;
            } else if (outputBufferIndex < 0) {
                LogUtils.e(TAG, "unexpected result from encoder.dequeueOutputBuffer: " +
                        outputBufferIndex);
            } else {
                //buffer和 encoder都正确，开始编码
                LogUtils.e(TAG, "start encoding");
                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                if (outputBuffer == null) {
                    throw new RuntimeException("encoderOutputBuffer " + outputBufferIndex +
                            " was null");
                }


                if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    LogUtils.e(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
                    mBufferInfo.size = 0;
                }

                if (mBufferInfo.size != 0) {
                    LogUtils.e(TAG, "mMuxerStarted  : "+mMuxerStarted);
                    if (!mMuxerStarted) {
                        MediaFormat newFormat = mCodec.getOutputFormat();
                        LogUtils.e(TAG, "mMuxer  : "+mMuxer);
                        mTrackIndex = mMuxer.addTrack(newFormat);
                        mMuxer.start();
                        mMuxerStarted = true;
                    }

                    outputBuffer.position(mBufferInfo.offset);//outputBuffer 开始的位置
                    outputBuffer.limit(mBufferInfo.offset + mBufferInfo.size);//outputBuffer limit 长度

                    mMuxer.writeSampleData(mTrackIndex, outputBuffer, mBufferInfo);

                    LogUtils.e(TAG, "sent " + mBufferInfo.size + " bytes to muxer");
                }

                mCodec.releaseOutputBuffer(outputBufferIndex, false);
            }
            outputBufferIndex = mCodec.dequeueOutputBuffer(mBufferInfo, TIMEOUTUS);
        }
    }

    private static MediaCodecInfo selectCodec(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }

}
