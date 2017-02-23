package com.mj.helloapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mj.helloapp.R;
import com.mj.helloapp.fragment.BookFragment;
import com.mj.helloapp.fragment.HomeFragment;
import com.mj.helloapp.fragment.MusicFragment;
import com.mj.libcommon.ui.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        refresh();
        setDefaultFragment();
    }

    private void refresh() {
        bottomNavigationBar.clearAll();
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "book"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_music_note_white_24dp, "music")).initialise();


    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment homeFragment = HomeFragment.newInstance("home");
        fragmentTransaction.replace(R.id.fl_fragment, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fragmentManager = this.getFragmentManager();
        //开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                Fragment homeFragment = HomeFragment.newInstance("home");
                fragmentTransaction.replace(R.id.fl_fragment, homeFragment);
                break;
            case 1:
                Fragment bookFragment = BookFragment.newInstance("book");
                fragmentTransaction.replace(R.id.fl_fragment, bookFragment);
                break;
            case 2:
                Fragment musicFragment = MusicFragment.newInstance("music");
                fragmentTransaction.replace(R.id.fl_fragment, musicFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
