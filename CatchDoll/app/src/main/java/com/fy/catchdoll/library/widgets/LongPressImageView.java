package com.fy.catchdoll.library.widgets;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by xky on 2018/1/10 0010.
 */
public class LongPressImageView extends ImageView implements View.OnTouchListener, View.OnLongClickListener {
    private int SECOND = 200;
    private int MAXCOUNT = 10;
    long time = 0;
    private int mCount = 0;
    private OnClickListener clickListener;
    private boolean isUp = false;
    private Handler mHandler = new Handler();
    public LongPressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LongPressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOnTouchListener(this);
    }


    public void setOnTimeClickListener(OnClickListener l){
        setOnLongClickListener(this);
        this.clickListener = l;
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("test","down");
                mCount = 0;
                time = System.currentTimeMillis();
                isUp = false;
                mHandler.postDelayed(runnable,SECOND);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("test","move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test", "up");
                isUp = true;
                mHandler.removeCallbacks(runnable);
                if(System.currentTimeMillis() - time < SECOND){
                    //少于100s则算一次单击
                    if (clickListener != null){
                        clickListener.onClick(LongPressImageView.this);
                    }
                }
                break;
        }
        return false;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("test", "click");
            if (isUp){
                return;
            }
            mCount ++;
            if (mCount > MAXCOUNT){
                //限制次数
                mHandler.removeCallbacks(runnable);
                return;
            }
            if (clickListener != null){
                clickListener.onClick(LongPressImageView.this);
            }
            mHandler.removeCallbacks(runnable);
            mHandler.postDelayed(runnable,SECOND);
        }
    };

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
