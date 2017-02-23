package com.mj.helloapp.fragment;

import android.os.Bundle;

import com.mj.helloapp.R;

/**
 * Created by liuwei on 2/6/17.
 */

public class MusicFragment extends BaseFragment {

    public static MusicFragment newInstance(String param1) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MusicFragment() {

    }

    @Override
    public int getFragmentView() {
        return R.layout.fragment_music;
    }
}