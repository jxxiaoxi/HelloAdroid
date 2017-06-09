package com.mj.libcommon.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by liuwei on 6/7/17.
 */

public abstract class BaseSplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new View(this);
        view.setBackgroundResource(setBackgroundResource());
        setContentView(view);
        setAnimation(view);
    }

    /**
     * 当前界面背景
     */
    public abstract int setBackgroundResource();


    /**
     * 当前界面背景显示的动画
     */
    public void setAnimation(View view) {
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gotoActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation);
    }

    /**
     * 动画结束后，需要展示的页面
     */
    public abstract void gotoActivity();

}
