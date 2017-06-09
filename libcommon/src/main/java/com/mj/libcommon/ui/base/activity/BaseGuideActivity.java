package com.mj.libcommon.ui.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mj.libcommon.R;
import com.mj.libcommon.adapter.BaseGuideAdapter;
import com.mj.libcommon.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by liuwei on 6/8/17.
 */

public abstract class BaseGuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ArrayList<View> mViewList;
    private final int mScrolleTime = 5000;//切换View的间隔时间
    private int mSelectedDot = 0;
    public ArrayList<Integer> mDrawables;

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
        setContentView(R.layout.activity_base_guide);
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        bindView();
    }

    /**
     * page的点击事件
     */
    private void bindView() {
        mViewList = new ArrayList<View>();
        mDrawables = setViewPagerImageViews();
        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
        for (int i = 0; i < mDrawables.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(mDrawables.get(i));
            mViewList.add(iv);
            intDot();
        }
       View layoutView =  setViewPagerLayoutView();
        if(layoutView != null){
            mViewList.add(layoutView);
            intDot();
        }

        mViewPager.setAdapter(new BaseGuideAdapter(mViewList));
        mViewPager.addOnPageChangeListener(new ViewPagerOnPageChangeListener());
        mViewPager.setOnTouchListener(new PageViewOnTouchListener());
        ll_dot.getChildAt(mSelectedDot).setEnabled(true);


//        //设置当前viewpager要显示第几个条目
//        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mViewList.size());
//        mViewPager.setCurrentItem(item);
        if (isLoop()) {
            mHandler.postDelayed(mRunnable, mScrolleTime);
        }

    }

    /**
     * page的点击事件
     */
    public abstract void onPageClick(int item);

    /**
     * 添加ViewPager需要展示的ImageView
     */
    public abstract ArrayList<Integer> setViewPagerImageViews();

    /**
     * 添加ViewPager需要展示的布局视图
     */
    public abstract View setViewPagerLayoutView();


    /**
     * 添加需要展示的ImageView
     * return 图片id
     * 默认point_background，如需要自定义重写这个方法
     */
    public int setIndicatorView() {
        return R.drawable.point_background;
    }

    /**
     * 初始化dot
     */
    private void intDot() {
        ImageView dot = new ImageView(this);
        dot.setBackgroundResource(setIndicatorView());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        dot.setLayoutParams(params);
        dot.setEnabled(false);
        ll_dot.addView(dot);
    }

    /**
     * ViewPager 是否支持循环
     */
    public boolean isLoop() {
        return false;
    }

    private class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int newposition = position % mViewList.size();
            mViewList.get(newposition).setOnClickListener(new PageOnClickListener());
            setSelectDot(newposition);

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            LogUtils.e("liuwei", "onPageScrollStateChanged ");
        }
    }

    /**
     * 设置dot的对应位置
     */
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
            onPageClick(item);
        }
    }

    private class PageViewOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isLoop()){
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
