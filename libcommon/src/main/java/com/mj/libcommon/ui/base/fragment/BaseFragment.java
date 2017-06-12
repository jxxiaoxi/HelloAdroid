package com.mj.libcommon.ui.base.fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mj.libcommon.R;

/**
 * Created by liuwei on 6/12/17.
 */

public class BaseFragment extends Fragment {
    private TextView tv_title;
    private LinearLayout ll_title_bar;
    private ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        ll_title_bar = (LinearLayout) view.findViewById(R.id.ll_title_bar);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);

        if (!isTitleBarVisible()) {
            ll_title_bar.setVisibility(view.GONE);
        } else {
            tv_title.setText(setTitle());
            if (isLeftIconVisiable()) {
                if (setLeftIcon() != null) {
                    iv_back.setBackground(setLeftIcon());
                }
                iv_back.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    /**
     * titlebar 左边图片是否显示
     */
    public boolean isLeftIconVisiable() {
        return false;
    }

    /**
     * titlebar 客制化左边icon
     */
    public Drawable setLeftIcon() {
        return null;
    }

    /**
     * titlebar 设置title
     */
    public String setTitle() {
        return "";
    }

    /**
     * titlebar 是否可以显示
     */
    public boolean isTitleBarVisible() {
        return true;
    }
}
