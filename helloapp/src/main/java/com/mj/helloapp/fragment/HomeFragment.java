package com.mj.helloapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.HttpAuthHandler;

import com.mj.helloapp.R;
import com.mj.libcommon.http.OkHttpUtil;
import com.mj.libcommon.http.ResponseCallBack;
import com.mj.libcommon.util.LogUtils;

import okhttp3.Response;

import static com.mj.helloapp.constant.HttpConstant.LATEST_NEW;

/**
 * Created by liuwei on 2/6/17.
 */

public class HomeFragment extends BaseFragment implements ResponseCallBack {

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);

        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public int getFragmentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void Response(Response response) {
        LogUtils.e("liuwei", "response :   " + response);
    }
}
