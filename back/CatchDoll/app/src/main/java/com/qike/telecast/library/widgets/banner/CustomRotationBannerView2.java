package com.qike.telecast.library.widgets.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.qike.telecast.R;
import com.qike.telecast.library.widgets.ResultsListView;
import com.qike.telecast.presentation.model.dto.doll.banner.BannerInfo;
import com.qike.telecast.presentation.view.adapters.main.RotationBannerViewPagerAdapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>自定义一个bannerview</p><br/>
 * <p>无线轮训</p>
 *
 * @author xky
 * @since 1.0.0
 */
public class CustomRotationBannerView2 extends RelativeLayout implements OnPageChangeListener, OnTouchListener, ResultsListView.onEventUpListener {

    private LayoutInflater mInflater;
    private View mView;
    private ViewPager mViewPager;
    private TextView mBannerTitleView;
    private LinearLayout mPointContainer;
    private Timer mTimer;
    private MTimerTask mTimerTask;
    private final long DELAYTIME = 3000;//延时
    private final long PERIODTIME = 2000;//频率
    private List<BannerInfo> mDatas;
    private Context mContext;
    private RotationBannerViewPagerAdapter2 mAdapter;
    private int mFocusId = R.mipmap.current;
    private int mDefaultId = R.mipmap.point;
    private int mCurrentPosition;
    private boolean mIsContinue = true;
    public static final int TIMERLOOPER = 1;

    public CustomRotationBannerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        initView();
        setListener();
        initData();
    }

    private void initData() {
        mTimer = new Timer();
        mTimerTask = new MTimerTask();
//        MResultsListView.setOnEventUpListener(this);
    }

    private void setListener() {
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOnTouchListener(this);
    }

    private void initView() {
        mView = mInflater.inflate(R.layout.tele_banner_layout, null);
        mViewPager = (ViewPager) mView.findViewById(R.id.banner_viewpager);
        mBannerTitleView = (TextView) mView.findViewById(R.id.banner_bottom_title);
        mPointContainer = (LinearLayout) mView.findViewById(R.id.banner_bottom_point_container);
        addView(mView);
    }

    /**
     * <p>设置轮训数据</p><br/>
     *
     * @param datas
     * @author xky
     * @since 1.0.0
     */
    public void setData(List<BannerInfo> datas) {
        mDatas = (datas == null ? new ArrayList<BannerInfo>() : datas);
        mAdapter = new RotationBannerViewPagerAdapter2(datas, mContext);
        mViewPager.setAdapter(mAdapter);
        initPoint();
        mCurrentPosition = mDatas.size() * 3000;
        setTitle(mCurrentPosition);
        changePoint(mCurrentPosition);
        endTimer2();
        startTimer2();
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    private void setTitle(int position) {
        if (mDatas.size() <= 0) {
            return;
        }
        int po = position % mDatas.size();
        mBannerTitleView.setText(mDatas.get(po).getTitle());
    }

    public void setOnBannerClickListener(IOnBannerItemClickListener2 bannerListener) {
        mAdapter.setOnBannerClickListener(bannerListener);
    }

    /**
     * <p>开启自动轮训</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @author xky
     * @since 1.0.0
     */
    private void startTimer() {
        endTimer();
        if (mTimer == null) {
            mTimer = new Timer();
            mTimerTask = new MTimerTask();
        }
        mTimer.schedule(mTimerTask, DELAYTIME, PERIODTIME);
    }

    Handler mHander = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case TIMERLOOPER:
                    timerOperate();
                    startTimer2();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private void startTimer2() {
        mHander.sendEmptyMessageDelayed(TIMERLOOPER, DELAYTIME);
    }

    private void endTimer2() {
        mHander.removeMessages(TIMERLOOPER);
    }

    /**
     * <p>结束自动轮训</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @author xky
     * @since 1.0.0
     */
    public void endTimer() {
        releaseSource();
    }

    /**
     * <p>init point</p><br/>
     *
     * @author xky
     * @since 1.0.0
     */
    private void initPoint() {
        mPointContainer.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView point = (ImageView) mInflater.inflate(R.layout.tele_banner_point_layout, null);
            point.setTag(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 15;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            mPointContainer.addView(point, params);
        }
    }

    /**
     * <p>change point focus</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @param position
     * @author xky
     * @since 1.0.0
     */
    private void changePoint(int position) {
        if (mDatas.size() <= 0) {
            return;
        }

        int po = position % mDatas.size();


        for (int i = 0; i < mDatas.size(); i++) {
            ImageView point = (ImageView) mPointContainer.findViewWithTag(i);
            if(point != null){
                if (i == po) {
                    point.setBackgroundResource(mFocusId);
                } else {
                    point.setBackgroundResource(mDefaultId);
                }
            }
        }

    }

    /**
     * <p>设置轮训点图片</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @param focusId   选中该点时的图片id
     * @param defaultId 未选中时的图片id
     * @author xky
     * @since 1.0.0
     */
    public void setPointBackground(int focusId, int defaultId) {
        mFocusId = focusId;
        mDefaultId = defaultId;
    }

    /**
     * <p>释放一些必要的资源</p><br/>
     * <p>最好在控件被销毁的时候调用</p>
     *
     * @author xky
     * @since 1.0.0
     */
    public void releaseSource() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }

    boolean isCloseImg = false;

    //
    public void closeTraining() {
        isCloseImg = true;
    }

    public void openTraining(){
        isCloseImg = false;
    }

    /*****
     * pager
     *******/
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        changePoint(mCurrentPosition);
        setTitle(mCurrentPosition);
    }

    /*****
     * pager
     *******/


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsContinue = false;
                //最好取消定时器[防止松开手马上滚动的情况]
                endTimer2();
                break;
            case MotionEvent.ACTION_MOVE:

                mIsContinue = false;
                endTimer2();
                break;
            case MotionEvent.ACTION_UP:

                mIsContinue = true;
                startTimer2();
                break;
            default:
                break;
        }
        return false;
    }


    private void timerOperate() {
        if (isCloseImg) {
            return;
        }
        if (mIsContinue) {
            if (mCurrentPosition == Integer.MAX_VALUE) {
                mCurrentPosition = mDatas.size() * 3000 - 1;
            }
            mViewPager.setCurrentItem(++mCurrentPosition);
        }
    }

    class MTimerTask extends TimerTask {

        @Override
        public void run() {
            if (mIsContinue) {
                if (mCurrentPosition == Integer.MAX_VALUE) {
                    mCurrentPosition = mDatas.size() * 3000 - 1;
                }
                mHander.post(new Runnable() {

                    @Override
                    public void run() {
                        if (!isCloseImg) {
                            mViewPager.setCurrentItem(++mCurrentPosition);
                        }
                    }
                });
            }
        }

    }

    @Override
    public void onUp() {
        if (!mIsContinue) {
            mIsContinue = true;
            startTimer2();
        }
    }

    public interface IOnBannerItemClickListener2 {

        public void onBannerItemClick(BannerInfo dto);

    }


}
