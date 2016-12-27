package com.mj.viewpagerdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mj.libcommon.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by liuwei on 12/21/16.
 */

public class ViewPagerAdapter<E extends View> extends PagerAdapter {
    private ArrayList<View> mList;

    public ViewPagerAdapter(ArrayList<View> list) {
        super();
        mList = list;
    }

    @Override
    public int getCount() {
        //return mList.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*
    *  页面显示和滑动过程中删除页面，比如滑动到第2页，会调用该方法将不在前后页（第2页的前后页为1、3页）的第0页移除
    *
    * */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtils.e("liuwei", "destroyItem : " + position);
        container.removeView((View) object);
    }

/*
*  页面显示和滑动过程中添加页面，比如滑动到第2页，会调用该方法提前加载第三页
*
* */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.e("liuwei", "instantiateItem : " + position);
        //拿着position位置 % 集合.size
        int newposition = position % mList.size();
        container.addView(mList.get(newposition));
        return mList.get(newposition);
    }

}
