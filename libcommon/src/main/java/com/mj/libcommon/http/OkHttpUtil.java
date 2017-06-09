package com.mj.libcommon.http;

import com.mj.libcommon.callback.ResultCallBack;
import com.mj.libcommon.util.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liuwei on 3/10/17.
 */

public class OkHttpUtil {
    private static OkHttpUtil mOkHttpUtil;
    private OkHttpClient mOkHttpClient;
    private ResponseCallBack mResponseCallBack;
    private final static String TAG = "com.mj.libcommon.http.OkHttpUtil";

    public static final MediaType MEDIA_TYPE
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
    }

    public static OkHttpUtil getInstance() {
        if (mOkHttpUtil == null) {
            mOkHttpUtil = new OkHttpUtil();
        }
        return mOkHttpUtil;
    }

    /**
     * Created by liuwei on 3/10/17.
     * get 请求
     */

    public void get(String url, final ResultCallBack resultCallBack) {
        LogUtils.e(TAG, "url :  " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, "onFailure ");
                resultCallBack.onResultCallBack("fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.e(TAG, "response   :  " + response.body().string());
                resultCallBack.onResultCallBack(response.body().string());
            }
        });
    }


    /**
     * Created by liuwei on 3/10/17.
     * post 请求
     */

    private void post(String url) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, "");
        Response response = null;
        Request request = new Request.Builder()
                .url(url).post(requestBody)
                .build();
        try {
            response = mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return result
        if (response != null) {
            mResponseCallBack.Response(response);
        }
    }

    private void ResponseCallBack(ResponseCallBack callBack) {
        this.mResponseCallBack = callBack;
    }

}
