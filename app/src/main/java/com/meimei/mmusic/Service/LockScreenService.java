package com.meimei.mmusic.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by 梅梅 on 2016/5/9.
 */
public class LockScreenService extends Service{

    public static final String UNLOCK_ACTION = "unlock";
    private BroadcastReceiver mReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("aa","oncreate");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        mReceiver = new LockScreenReceiver();
        registerReceiver(mReceiver,filter);
        Log.i("aa","注册锁屏广播接收器");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        Log.i("aa","注销锁屏广播接收器");
    }
}
