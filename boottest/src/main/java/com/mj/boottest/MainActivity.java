package com.mj.boottest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TelephonyManager tm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
//        List<String> deviceIds = new ArrayList<String>();
//        for (int slot = 0; slot < tm.getPhoneCount(); slot++) {
//            String deviceId = tm.getDeviceId(slot);
//            if (!TextUtils.isEmpty(deviceId)) {
//                deviceIds.add(deviceId);
//            }
//        }



        String imei = tm.getDeviceId();
        //Log.e("mijie", "imei :  " + imei);
        //new Thread(new MyRunnable()).start();

    }

    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
                String imei = tm.getDeviceId();
                //while (true) {

                    Log.e("mijie", "imei :  " + imei);
                //}
        }
    }
}
