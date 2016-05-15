package com.meimei.mmusic.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;

import com.meimei.mmusic.R;
import com.meimei.mmusic.Service.LockScreenService;
import com.meimei.mmusic.View.MyView;

public class LockActivity extends Activity {

    public static int MSG_LOCK_SUCESS = 0x123;// 解锁成功

    public static boolean isLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aaa","正在展示锁屏界面");
        setContentView(R.layout.activity_lock);
        MyView.setHandler(mHandler);

        isLocked = true;
        /*iv_key = (ImageView) findViewById(R.id.iv_key);

        iv_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virbate();
                isLocked = false;
                Toast.makeText(LockActivity.this, "Screen is unlocked", Toast.LENGTH_SHORT).show();
                finish();
            }
        });*/

        try {
            startService(new Intent(this, LockScreenService.class));
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void virbate() {
        Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(Message message){
            if (MSG_LOCK_SUCESS == message.what){
                Log.i("aaa","finish");
                finish();
            }
        }
    };
}
