package com.mj.helloapp.fragment;

import android.os.Bundle;

import com.mj.libcommon.http.ResponseCallBack;
import com.mj.libcommon.ui.base.fragment.BaseFragment;

import okhttp3.Response;

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

    @Override
    public void Response(Response response) {

    }

    @Override
    public String setTitle() {
        return "Home";
    }
}
