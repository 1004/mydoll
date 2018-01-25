package com.fy.catchdoll.library.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.fy.catchdoll.R;

/**
 * Created by xky on 2018/1/25 0025.
 */
public class MyScrollView extends ScrollView{
    private ListView mListView;
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListView();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListView(ListView lv){
        mListView = lv;
    }

    private void initListView(){
        mListView = (ListView) findViewById(R.id.msg_list);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean isintercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (checkPointInView(x,y,mListView)){
                    isintercept = false;
                }else {
                    isintercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (checkPointInView(x,y,mListView)){
                    isintercept = false;
                }else {
                    isintercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        Log.i("test","isintercept"+isintercept);
        return isintercept;
    }

    private boolean checkPointInView(int x,int y,View view){
        if (view == null || view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE){
            return false;
        }
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        if (x>rect.left & x<rect.right & y>rect.top & y<rect.bottom){
            return true;
        }else {
            return false;
        }
    }
}
