package com.mj.viewpagerdemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mj.libcommon.ui.BaseActivity;
import com.mj.libcommon.util.LogUtils;
import com.mj.viewpagerdemo.R;
import com.mj.viewpagerdemo.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    public final String TAG = "ViewPagerActivity";
    private ViewPager mViewPager;
    public int[] mPic = {R.mipmap.ic_launcher, R.mipmap.a, R.mipmap.b, R.mipmap.c};
    private ArrayList<View> mViewList;
    private final int mScrolleTime = 5000;//切换View的间隔时间
    private int mSelectedDot = 0;

    //
    private LinearLayout ll_dot;

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
        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
        for (int i = 0; i < mPic.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackground(getResources().getDrawable(mPic[i]));
            mViewList.add(iv);

            ImageView dot = new ImageView(this);
            dot.setBackgroundResource(R.drawable.point_background);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            ll_dot.addView(dot);
        }
        mViewPager.setAdapter(new ViewPagerAdapter(mViewList));
        mViewPager.addOnPageChangeListener(new ViewPagerOnPageChangeListener());
        mViewPager.setOnTouchListener(new PageViewOnTouchListener());
        ll_dot.getChildAt(mSelectedDot).setEnabled(true);


//        //设置当前viewpager要显示第几个条目
//        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mViewList.size());
//        mViewPager.setCurrentItem(item);

        mHandler.postDelayed(mRunnable, mScrolleTime);

    }

    private class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int newposition = position % mViewList.size();
            //LogUtils.e(TAG, "onPageScrolled  position : " + position + "   ; positionOffset : " + positionOffset + "  ;positionOffsetPixels  : " + positionOffsetPixels);
            mViewList.get(newposition).setOnClickListener(new PageOnClickListener());
            setSelectDot(newposition);

        }

        @Override
        public void onPageSelected(int position) {
            //LogUtils.e(TAG, "onPageSelected ===========>" + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            LogUtils.e("liuwei", "onPageScrollStateChanged ");
        }
    }

    private void setSelectDot(int dot) {
        for (int i = 0; i < mViewList.size(); i++) {
            ll_dot.getChildAt(i).setEnabled(false);
        }

        ll_dot.getChildAt(dot).setEnabled(true);
    }


    private class PageOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int item = mViewPager.getCurrentItem() % mViewList.size();
            Toast.makeText(MainActivity.this, "click the pagge :  " + item, Toast.LENGTH_SHORT).show();
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
