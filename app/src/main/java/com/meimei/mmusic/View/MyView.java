package com.meimei.mmusic.View;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.meimei.mmusic.Activity.LockActivity;
import com.meimei.mmusic.R;

/**
 * Created by 梅梅 on 2016/5/4.
 */
public class MyView extends RelativeLayout{
    private Context mContext;
    private Scroller mScroller;
    private int mScreenWidth = 0;// 屏幕宽度
    private int mLastDownX = 0;// 按下时X轴的位置
    private int mCurryX;// X轴移动的距离
    private int mDelX;// X轴移动的距离
    private boolean mCloseFlag = false;// 标识是否解锁
    private static Handler mainHandler = null; // 与主Activity通信的Handler对象
    private ImageView mImgView;

    public MyView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView(){
        BounceInterpolator interpolator = new BounceInterpolator();
        mScroller = new Scroller(mContext,interpolator);

        //获取屏幕分辨率
        WindowManager wm = (WindowManager) mContext.
                getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;

        //这里一定要设置成透明背景，不然会影响你看到底层布局
        this.setBackgroundColor(R.color.transparency);
        mImgView = new ImageView(mContext);
        mImgView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mImgView.setScaleType(ImageView.ScaleType.FIT_XY); //充满整个屏幕
        mImgView.setImageResource(R.mipmap.ic_bg);
        addView(mImgView);
    }

    public void startBounceAnim(int startX,int dx,int duration){
        mScroller.startScroll(startX,0,dx,0,duration);
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: //按下
                mLastDownX = (int) event.getX();
                return true;
            case MotionEvent.ACTION_MOVE:
                mCurryX = (int)event.getX(); //移动后的X轴位置
                mDelX = mCurryX - mLastDownX; //移动了的距离
                //只准右滑有效
                if(mDelX > 0){
                    scrollTo(-mDelX,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurryX = (int)event.getX();
                mDelX = mCurryX - mLastDownX;

                if(mDelX > 0) {//右滑
                    if(Math.abs(mDelX) > mScreenWidth/2){//当滑动距离超过半屏时解锁
                        // 向右滑动超过半个屏幕宽的时候 开启向右消失动画
                        startBounceAnim(this.getScrollX(),-mScreenWidth, 1000);
                        mCloseFlag = true;
                    }else {//向右滑动未超过半个屏幕宽的时候  开启向左弹动动画
                        startBounceAnim(this.getScrollX(),-this.getScrollX(), 1200);

                    }
                }else {//回滚到原位置
                    startBounceAnim(this.getScrollX(),-this.getScrollX(),1200);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void computeScroll(){

        if (mScroller.computeScrollOffset()){//滚动界面
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            /*Log.i("scroller", "getCurrX()= " + mScroller.getCurrX()
                    + "     getCurrY()=" + mScroller.getCurrY()
                    + "  getFinalY() =  " + mScroller.getFinalY());*/
            // 更新界面
            postInvalidate();
        }else {//解锁成功
            if(mCloseFlag){
                Log.i("aaa","解锁了");
                /*mainHandler.obtainMessage(LockActivity.MSG_LOCK_SUCESS)
                        .sendToTarget();*/
//                this.setVisibility(View.GONE);
                /*Intent i = new Intent(mContext, LockScreenService.class);
                i.setAction(LockScreenService.UNLOCK_ACTION);
                mContext.startService(i);*/
                LockActivity.isLocked = false;
                Message message = new Message();
                message.what = LockActivity.MSG_LOCK_SUCESS;
                mainHandler.sendMessage(message);
            }

        }
    }

    public static void setHandler(Handler handler) {
        mainHandler = handler;// activity所在的Handler对象
    }

}
