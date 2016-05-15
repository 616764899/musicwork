package com.meimei.mmusic.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 梅梅 on 2016/5/9.
 */
public class LockScreenReceiver extends BroadcastReceiver{

    private final String LOCK_SCREEN_ACTION = "android.intent.mlockscreen";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
//            Intent lockIntent = new Intent(context,LockActivity.class);
            Intent lockIntent = new Intent(LOCK_SCREEN_ACTION);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lockIntent);
          //  Intent intent1=new Intent(context,main.class);
            Log.i("aa","打开锁屏界面");
        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // 其他操作
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // 其他操作
        }
    }
}
