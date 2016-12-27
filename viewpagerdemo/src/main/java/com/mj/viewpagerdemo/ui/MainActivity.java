package com.mj.viewpagerdemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mj.libcommon.ui.BaseActivity;
import com.mj.libcommon.util.LogUtils;
import com.mj.viewpagerdemo.R;
import com.mj.viewpagerdemo.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public final String TAG = "MainActivity";
    private ViewPager mViewPager;
    public int[] mPic = {R.mipmap.ic_launcher, R.mipmap.a, R.mipmap.b, R.mipmap.c};
    private ArrayList<View> mViewList;
    private final int mScrolleTime = 5000;//切换View的间隔时间

    private Handler mHandler = new Handler() {
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            mHandler.postDelayed(this, mScrolleTime);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        bindView();
    }

    private void bindView() {
        mViewList = new ArrayList<View>();
        for (int i = 0; i < mPic.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackground(getResources().getDrawable(mPic[i]));
            mViewList.add(iv);
        }
        mViewPager.setAdapter(new ViewPagerAdapter(mViewList));
        mViewPager.addOnPageChangeListener(new ViewPagerOnPageChangeListener());
        mViewPager.setOnTouchListener(new PageViewOnTouchListener());

        mHandler.postDelayed(mRunnable, mScrolleTime);

    }

    private class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
          //  LogUtils.e(TAG, "onPageScrolled  position : " + position + "   ; positionOffset : " + positionOffset + "  ;positionOffsetPixels  : " + positionOffsetPixels);
            mViewList.get(position).setOnClickListener(new PageOnClickListener());
        }

        @Override
        public void onPageSelected(int position) {
           // LogUtils.e(TAG, "onPageSelected ===========>" + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //LogUtils.e(TAG, "onPageScrollStateChanged ");
        }
    }


    private class PageOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "click the pagge :  " + mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    private class PageViewOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            LogUtils.e(TAG, " onTouch");
            return false;
        }
    }

}
