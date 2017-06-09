package com.mj.helloapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mj.helloapp.R;
import com.mj.libcommon.ui.base.activity.BaseGuideActivity;
import com.mj.libcommon.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by liuwei on 6/9/17.
 */

public class GuideActivity extends BaseGuideActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPageClick(int item) {
        Toast.makeText(this, "click the pagge :  " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<Integer> setViewPagerImageViews() {
        ArrayList<Integer> views = new ArrayList<Integer>();
        views.add(R.mipmap.guide_1);
        views.add(R.mipmap.guide_2);
        views.add(R.mipmap.guide_3);
        return views;
    }

    @Override
    public View setViewPagerLayoutView() {
        View view = View.inflate(this, R.layout.guide_last_view,null);
        Button start = (Button)view.findViewById(R.id.bt_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMain();
            }
        });
        return view;
    }

    public void goMain(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
