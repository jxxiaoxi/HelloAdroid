package com.well.helloandroid.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by liuwei on 5/30/16.
 */
public  class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //添加Fragment
    public void addFragment(int containerViewId,Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());//添加Fragment到回退栈
            fragmentTransaction.commitAllowingStateLoss();//提交操作，注意和commit的区别
        }
    }

    //移除Fragment
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) { //获取回退栈中的fragment个数
            getSupportFragmentManager().popBackStack();//把Fragment从回退栈中移除掉
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            if(getSupportFragmentManager().getBackStackEntryCount()==1){//当按返回键时，判断此时 回退栈中还有几个fragment，如果只有1个，那么在你再按回退的时候，应该finish掉当前activity。
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
