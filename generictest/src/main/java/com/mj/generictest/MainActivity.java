package com.mj.generictest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CollectionApply<String> mCollectionApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCollectionApply = new CollectionApply(new ArrayList<String>());
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                mCollectionApply.add("aaa");
                mCollectionApply.add("bbb");
                mCollectionApply.add("ccc");
                mCollectionApply.add("ddd");
                break;
            case R.id.button2:
                mCollectionApply.delete("aaa");
                break;
            case R.id.button3:
                mCollectionApply.show();
                break;
        }
    }
}
