package com.fy.catchdoll.library.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by xky on 2018/1/29 0029.
 */
public class RotateLayout extends FrameLayout {
    private Matrix mForward = new Matrix();
    private Matrix mReverse = new Matrix();
    private float[] mTemp = new float[2];

    public RotateLayout(Context context) {
        super(context);
    }

    public RotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.rotate(-270, getWidth() / 2, getHeight() / 2);
        mForward = canvas.getMatrix();
        mForward.invert(mReverse);
        canvas.save();
        canvas.setMatrix(mForward); //This is the matrix we need to use for proper positioning of touch events
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float[] temp = mTemp;
        temp[0] = event.getX();
        temp[1] = event.getY();
        mReverse.mapPoints(temp);
        event.setLocation(temp[0], temp[1]);
        return super.dispatchTouchEvent(event);
    }


}
