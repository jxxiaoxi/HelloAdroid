package com.mj.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ry_view;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ry_view = (RecyclerView) findViewById(R.id.ry_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ry_view.setLayoutManager(mLinearLayoutManager);
//        ry_view.addItemDecoration(new DividerItemDecoration(ry_view.getContext(),
//                mLinearLayoutManager.getOrientation()));

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, initData(100));
        ry_view.setAdapter(adapter);
    }

    private ArrayList<String> initData(int i) {
        ArrayList<String> data = new ArrayList<String>();
        for (int j = 0; j < i; j++) {
            data.add(j, j + " RecyclerView ");
        }

        return data;
    }

}
