package com.mj.helloapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mj.helloapp.R;
import com.mj.helloapp.cache.DataCache;
import com.mj.helloapp.constant.Constant;
import com.mj.libcommon.ui.base.activity.BaseSplashActivity;

/**
 * Created by liuwei on 2/23/17.
 */

public class SplashActivity extends BaseSplashActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBackgroundResource() {
        return R.mipmap.wallpaper_11;
    }

    @Override
    public void gotoActivity() {
        goToMian();
    }

    private void goToMian() {
        if(!DataCache.getInstens(this).getBoolean(Constant.IS_LOGIN_MIAN)){
            startActivity(new Intent(this, GuideActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

}
