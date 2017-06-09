package com.mj.viewpagerdemo.ui;

import android.os.Bundle;
import android.view.View;

import com.mj.libcommon.ui.base.activity.BaseGuideActivity;
import com.mj.viewpagerdemo.R;

import java.util.ArrayList;

public class MainActivity extends BaseGuideActivity {
    public final String TAG = "ViewPagerActivity";
    public ArrayList<Integer> mPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPageClick(int item) {

    }

    @Override
    public ArrayList<Integer> setViewPagerImageViews() {
        mPic = new ArrayList<Integer>();
        mPic.add(R.mipmap.ic_launcher);
        mPic.add(R.mipmap.a);
        mPic.add(R.mipmap.b);
        mPic.add(R.mipmap.c);
        return mPic;
    }

    @Override
    public View setViewPagerLayoutView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
