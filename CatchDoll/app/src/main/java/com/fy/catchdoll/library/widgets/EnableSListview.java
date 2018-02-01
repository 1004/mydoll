package com.fy.catchdoll.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by xky on 2018/1/30 0030.
 */
public class EnableSListview extends ListView{
    public EnableSListview(Context context) {
        super(context);
        init();
    }

    public EnableSListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EnableSListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setScrollbarFadingEnabled(true);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true; // 禁止GridView滑动
        }
        return super.dispatchTouchEvent(ev);

    }
}
