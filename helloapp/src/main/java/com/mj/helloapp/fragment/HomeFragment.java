package com.mj.helloapp.fragment;

import android.os.Bundle;

import com.mj.helloapp.R;

/**
 * Created by liuwei on 2/6/17.
 */

public class HomeFragment extends BaseFragment {
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
}
