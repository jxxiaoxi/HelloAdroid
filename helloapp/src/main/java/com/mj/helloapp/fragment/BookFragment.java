package com.mj.helloapp.fragment;

import android.os.Bundle;

import com.mj.libcommon.ui.base.fragment.BaseFragment;

/**
 * Created by liuwei on 2/6/17.
 */

public class BookFragment extends BaseFragment {
    public static BookFragment newInstance(String param1) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public BookFragment() {

    }

    @Override
    public String setTitle() {
        return "Book";
    }
}
