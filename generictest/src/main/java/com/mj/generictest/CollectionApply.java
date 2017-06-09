package com.mj.generictest;

import com.mj.libcommon.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by liuwei on 5/11/17.
 * 泛型在集合的中使用
 */

public class CollectionApply<T> {
    public final static String TAG = "CollectionApply";
    private ArrayList<T> mList;

    public CollectionApply(ArrayList<T> list) {
        this.mList = list;
    }

    public void add(T t) {
        mList.add(t);
    }

    public void delete(T t) {
        if (mList.contains(t)) {
            mList.remove(t);
        }
    }

    public void show() {
        if (mList == null)
            return;
        for (int i = 0; i < mList.size(); i++) {
            LogUtils.e(TAG, mList.get(i).toString());
        }
    }
}
