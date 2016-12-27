package com.well.helloandroid.model;

import com.well.helloandroid.R;
import com.well.helloandroid.fragment.FinancialCenterFragment;
import com.well.helloandroid.fragment.SystemFragment;
import com.well.helloandroid.fragment.UserFragment;

public enum HomeTab {

    FINANCIAL(0, R.string.home_tab_home, R.mipmap.ic_launcher, FinancialCenterFragment.class),
    USER(1, R.string.home_tab_list, R.mipmap.ic_launcher, UserFragment.class),
    SYSTEM(2, R.string.home_tab_config, R.mipmap.ic_launcher, SystemFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private HomeTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
