package com.mj.mainapp.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mj.libcommon.ui.BaseActivity;
import com.mj.libcommon.util.LogUtils;
import com.mj.mainapp.R;
import com.mj.mainapp.service.RetrieveDataService;

public class MainActivity extends BaseActivity {
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this, RetrieveDataService.class);
    }


    /*
    * start 启动服务后 service 调用的方法
    * onCreate-->onStartCommand
    *
    * */

    public void start(View view) {
        LogUtils.e("RetrieveDataService", " start");
        startService(mIntent);
    }

    /*
    * stop 停止服务后 service 调用的方法
    * onDestroy
    * */
    public void stop(View view) {
        stopService(mIntent);
    }


    /*
    * bind 启动服务后 service 调用的方法
    * onCreate-->onBind
    * */
    public void bind(View view) {
        bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /*
    * unBind 停止服务后 service 调用的方法
    *onUnbind-->onDestroy
    * */
    public void unBind(View view) {
        unbindService(mServiceConnection);
    }


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RetrieveDataService mRetrieveDataService = ((RetrieveDataService.LocalBinder) iBinder).getLocalService();
            LogUtils.e("RetrieveDataService", "mRetrieveDataService :  " + mRetrieveDataService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
