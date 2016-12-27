package com.well.helloandroid.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.well.helloandroid.R;
import com.well.helloandroid.model.HomeTab;
import com.well.helloandroid.utils.LogUtils;
import com.well.helloandroid.widget.CustomFragmentTabHost;

/**
 * Created by liuwei on 6/07/16.
 * 此类用来演示一个app首页框架
 */

public class HomeFrameActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private CustomFragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mTabHost = (CustomFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //mTabHost.getTabWidget().setDividerDrawable(null);
        initTabs();
        mTabHost.setOnTabChangedListener(this);
    }

    private void initTabs() {
        HomeTab[] tabs = HomeTab.values();
        for (int i = 0; i < tabs.length; i++) {
            HomeTab tab = tabs[i];
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getResName()));
            View indicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView pic = (ImageView) indicator.findViewById(R.id.tab_pic);

            Drawable icon = getResources().getDrawable(tab.getResIcon());
            title.setText(getString(tab.getResName()));
            pic.setImageDrawable(icon);

            tabSpec.setIndicator(indicator);
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String s) {
                    return new View(HomeFrameActivity.this);
                }
            });
            mTabHost.addTab(tabSpec, tab.getClz(), null);
        }
    }

    @Override
    public void onTabChanged(String s) {
        LogUtils.e("liuwei","tab : "+s);
    }
}
