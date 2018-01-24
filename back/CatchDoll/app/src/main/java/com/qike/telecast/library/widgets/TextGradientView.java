package com.qike.telecast.library.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.qike.telecast.R;
import com.qike.telecast.library.utils.Loger;

import java.lang.ref.WeakReference;

/**
 * 渐变的TextView
 */
public class TextGradientView extends View {

    private Context mContext;
    private Paint mPaint;
    private int[] mColors;
    private float[] mPositions;

    private float mAnimatorValue;
    private ValueAnimator mAnimator;
    private boolean mGradientEnable;

    int desiredWidth;
    int desiredHeight;
    private String mTextStr;
    String mTextValue = "松开手即可刷新";
    private final static String DEFAULT_TEXT = "下拉刷新";

    public TextGradientView(Context context) {
        this(context, null);
    }

    public TextGradientView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mTextStr = DEFAULT_TEXT;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(dip2px(14));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mColors = new int[]{0xff000000, 0xaa000000, 0x77000000, 0xff000000, 0xaa000000, 0x77000000};
        mPositions = new float[]{0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextGradientViewStyle);
            assert array != null;
            mTextValue = array.getString(R.styleable.TextGradientViewStyle_text_value);
            int mTextSize = array.getDimensionPixelSize(R.styleable.TextGradientViewStyle_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            //TypedArray用完记得回收
            array.recycle();
        }

        Rect mBound = new Rect();
        //用来计算当前mTextValue的宽度和高度
        mPaint.getTextBounds(mTextValue, 0, mTextValue.length(), mBound);

        desiredWidth = mBound.width();
        desiredHeight = 10 + mBound.height();
        Loger.d("desiredWidth-------" + desiredWidth);
        Loger.d("desiredHeight-------" + desiredHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //http://blog.csdn.net/u010944680/article/details/52595507
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientEnable) {
//            mPaint.setShader(mLinearGradient = new LinearGradient(0, 0, mAnimatorValue, mAnimatorValue, mColors, mPositions, Shader.TileMode.CLAMP));
            mPaint.setShader(new LinearGradient(0, 0, mAnimatorValue, mAnimatorValue, mColors, mPositions, Shader.TileMode.CLAMP));
        }
        canvas.drawText(mTextStr, getMeasuredWidth() / 2, -mPaint.getFontMetrics().top, mPaint);
    }

    public void startAnimator() {
        float textWidth = getWidth();
        mGradientEnable = true;
        mAnimator = ValueAnimator.ofFloat(0f, textWidth);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setDuration(3000);
        mAnimator.addUpdateListener(new MyUpdateListener(this) {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.start();
    }

    public void stopAnimator() {
        if (mAnimator != null) {
            mGradientEnable = false;
            mPaint.setShader(new LinearGradient(0, 0, 0, 0, 0xff000000, 0xff000000, Shader.TileMode.CLAMP));
//            mPaint.setShader(new LinearGradient(0, 0, 0, 0, 0xff000000, 0xff000000, Shader.TileMode.CLAMP));
            mAnimatorValue = 0;
            clearAnimation();
            mAnimator.setRepeatCount(1);
            mAnimator.cancel();
            mAnimator.end();
            postInvalidate();
        }

    }

    /**
     * @param text
     */
    public void setText(String text) {
        mTextStr = text;
        postInvalidate();
    }

    /**
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private static class MyUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private WeakReference<TextGradientView> mWeakReference;

        public MyUpdateListener(TextGradientView gradientView) {
            mWeakReference = new WeakReference<TextGradientView>(gradientView);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            TextGradientView grad = mWeakReference.get();
            if (grad == null) {
                return;
            }
        }
    }
}

