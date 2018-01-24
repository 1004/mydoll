package com.qike.telecast.library.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.qike.telecast.R;
import com.qike.telecast.library.utils.PreferencesUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 列表UI公共组件
 * </p>
 * <br/>
 * <p>
 * 实现下拉刷新上拉更多
 * </p>
 *
 * @author sunxh
 * @since 2.6
 */
public class ResultsListView extends ListView implements OnScrollListener {
    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    // 正在刷新
    private final static int REFRESHING = 2;
    // 刷新完成
    private final static int DONE = 3;
    private final static int LOADING = 4;
    // 处理反复触发
    private Boolean mRepeate = true;
    /*
     * 比率, 比例 这个值是个很有用的值，要设定一个合适的值 现在设置为3,是为了： 1.当手指在屏幕上拉动的距离是headView的3倍的时候才会刷新
     * 2.每当手指滑动三个单位的距离，headView刷新一次 如果是1.那么整个listview会被拉到屏幕的最底部，效果不好
     */
    private final static int RATIO = 3;
    /***
     * 显示底部加载
     */
    public static final int FOOTER_SHOW = 0;
    /***
     * 显示底部无更多文案
     */
    public static final int FOOTER_NOT_SHOW = 1;
    /***
     * 不显示底部加载栏
     */
    public static final int FOOTER_NOT_DATA = 2;
    /***
     * 添加按钮
     */
    public static final int FOOTER_BUTTON = 3;
    private LayoutInflater mInflater;
    private LinearLayout mHeadViewLinearLayout;// headerView
    private TextView mResultListViewMoreTextView;
    private TextGradientView tipsTextview;// 提示文字
    private TextGradientView timeTextview;
    private ImageView mArrowImageView;// 箭头
    private ProgressBar mProgressBar;// 菊花
    private String mTipsString = "正在刷新...";
    private RotateAnimation mAnimation;// 逆时针
    private RotateAnimation mReverseAnimation;// 顺时针
    private boolean mIsRecored;
    // private int mHeadContentWidth;// head宽
    private int mHeadContentHeight;// head高，这个属性比较重要
    private int mStartY;
    private int mFirstItemIndex;
    private int mState;
    private boolean mIsBack;
    private OnRefreshListener mRefreshListener;
    private boolean mIsRefreshable;// 是否可下拉刷新，只有设置了下拉刷新listener之后才可以下来
    private boolean mIsRefreshableNohead = true;// 如果删除headview就将此标志更改为false，取消onTouch事件，否则轻轻滑动会使列表回滚到第一行
    private int mScrollState;
    private BaseAdapter mAdapter;
    private Context mContext;
    private int pageLastIndex;
    private int when2LoadNext;
    private final int pagesize = 12;
    private boolean isUp;
    private View mView;
    private View mLayout;
    private TextView mText;
    private View mItemClick;
    private OnScrollListener onScrollListener;
    private static onEventUpListener mUplistener;
    public GetMoreDataListener mGetMoreDataListener;

    private Class mClazz;

    public ResultsListView(Context context) {
        super(context);
        init(context);
    }

    public ResultsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(this);
        this.onScrollListener = l;
    }

    private void init(Context context) {
        this.mContext = context;
        setCacheColorHint(context.getResources().getColor(R.color.transparent));
        mInflater = LayoutInflater.from(context);
        mHeadViewLinearLayout = (LinearLayout) mInflater.inflate(R.layout.zz_resultslistview_head, null);
        mArrowImageView = (ImageView) mHeadViewLinearLayout.findViewById(R.id.head_arrowImageView);
        mArrowImageView.setMinimumWidth(70);
        mArrowImageView.setMinimumHeight(50);
        mProgressBar = (ProgressBar) mHeadViewLinearLayout.findViewById(R.id.head_progressBar);
        tipsTextview = (TextGradientView) mHeadViewLinearLayout.findViewById(R.id.head_tipsTextView);
        timeTextview = (TextGradientView) mHeadViewLinearLayout.findViewById(R.id.head_time);
        measureView(mHeadViewLinearLayout);// 确定headView的宽高,如果不调用这个方法，之后得到的宽高值都为0
        mHeadContentHeight = mHeadViewLinearLayout.getMeasuredHeight();
        mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight, 0, 0);// 设置paddingTop为负数(-head_height)，这个是把head放到看不见的地方的代码
        mHeadViewLinearLayout.invalidate();
        addHeaderView(mHeadViewLinearLayout);// 和上边方法区别的，这个添加分割线
        super.setOnScrollListener(this);// 设置滚动监听
        // 逆时针动画
        mAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(200);
        mAnimation.setFillAfter(true);// 设置在动画结束之后图片就呈现的是动画结束之后的样子
        mReverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseAnimation.setInterpolator(new LinearInterpolator());
        mReverseAnimation.setDuration(200);
        mReverseAnimation.setFillAfter(true);
        mState = LOADING;// 初始化state
        mIsRefreshable = false;// 默认不可下拉刷新
    }

    public void setmGetMoreDataListener(GetMoreDataListener mGetMoreDataListener) {
        this.mGetMoreDataListener = mGetMoreDataListener;
    }

    /**
     * 底部更多回调接口
     *
     * @author sunxh
     */
    public interface GetMoreDataListener {
        void getMore();
    }

    public void daoClear() {
        pageLastIndex = 0;
        isUp = true;
        when2LoadNext = 0;
    }

    public boolean isListViewReachTopEdge(final ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }

    /**
     * 记录滑动状态
     */
    @Override
    public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisiableItem, visibleItemCount, totalItemCount);
        }
        mFirstItemIndex = firstVisiableItem;
        if (mAdapter != null) {
            // 判断滑动动作
            if (pageLastIndex < firstVisiableItem + visibleItemCount) {
                pageLastIndex = firstVisiableItem + visibleItemCount;// 页面上显示的�?���?��数据的索�?
                isUp = true;
            } else {
                isUp = false;
            }
            if (!(when2LoadNext < pagesize) || !(when2LoadNext > 0)) {
                when2LoadNext = pagesize / 2;
            }
            if ((totalItemCount - (firstVisiableItem + visibleItemCount)) < when2LoadNext) {
                if (isUp && view.getChildAt(0).getTop() != 0) {
                    /**检测如果listview在顶部则不需要调用onUpload*/
                    if (mRefreshListener != null) {
                        mRefreshListener.onUpload();
                    }
                }
            }

        }

    }

    /**
     * 底部加载栏控制
     */
    public void setFooterView(int option) {
        switch (option) {
            // 显示底部加载
            case FOOTER_SHOW:
                mLayout.setVisibility(View.VISIBLE);
                mText.setVisibility(View.GONE);
                hideBtn();
                break;
            // 显示底部无更多文�?
            case FOOTER_NOT_SHOW:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.VISIBLE);
                hideBtn();
                break;
            // 不显示底部加载栏
            case FOOTER_NOT_DATA:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);
                hideBtn();
                break;
            default:
                break;
        }
    }

    private void hideBtn() {
        if (mItemClick != null) {
            mItemClick.setVisibility(View.GONE);
        }
    }

    /**
     * 底部加载栏控制
     * footer添加按钮
     */
    public void setFooterView(int option, OnClickListener click, String text) {
        switch (option) {
            case FOOTER_BUTTON:
                mLayout.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);
                mItemClick.setVisibility(View.VISIBLE);
                // mItemClick.setText(text);
                mItemClick.setOnClickListener(click);
                break;

            default:
                break;
        }
    }

    /**
     * 滑动动作
     */
    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(arg0, arg1);
        }

        int first = arg0.getFirstVisiblePosition();// 判断当前listview第一条item显示第几条数�?
        this.mScrollState = arg1;
        if (mScrollState == SCROLL_STATE_IDLE) {
            // if (((BaseAdapter) ((HeaderViewListAdapter)
            // getAdapter()).getWrappedAdapter()) != null) {
            // //某些手机在没有数据时候仍可以滑动
            // ((BaseAdapter) ((HeaderViewListAdapter)
            // getAdapter()).getWrappedAdapter()).notifyDataSetChanged();
            // }
        }
        if (arg1 == 2 && first == 0) {
            // 解决部分手机下拉刷新是listview状态错误
            this.mScrollState = SCROLL_STATE_IDLE;
        }
    }

    public int getScrollState() {
        return mScrollState;
    }


    public boolean onTouchEvent(MotionEvent event) {
        if (mIsRefreshable && mIsRefreshableNohead) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:// 按下
                    if (mFirstItemIndex == 0 && !mIsRecored) {
                        mIsRecored = true;
                        mStartY = (int) event.getY();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mState != REFRESHING && mState != LOADING) {
                        if (mState == DONE) {
                        }
                        if (mState == PULL_To_REFRESH) {// 拉的距离不够
                            mState = DONE;
                            changeHeaderViewByState();
                        }
                        if (mState == RELEASE_To_REFRESH) {
                            mState = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }
                    mIsRecored = false;
                    mIsBack = false;
                    if (mUplistener != null) {
                        mUplistener.onUp();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) event.getY();
                    if (!mIsRecored && mFirstItemIndex == 0) {
                        mIsRecored = true;
                        mStartY = tempY;
                    }
                    if (mState != REFRESHING && mIsRecored && mState != LOADING) {

                        if (mState == RELEASE_To_REFRESH) {
                            // setSelection(0);
                            if (((tempY - mStartY) / RATIO < mHeadContentHeight) && (tempY - mStartY) > 0) {
                                mState = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            } else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        if (mState == PULL_To_REFRESH) {
                            // setSelection(0);
                            if ((tempY - mStartY) / RATIO >= mHeadContentHeight) {// 滑动的距离到达临近点
                                mState = RELEASE_To_REFRESH;
                                mIsBack = true;
                                changeHeaderViewByState();
                            } else if (tempY - mStartY <= 0) {
                                mState = DONE;
                                changeHeaderViewByState();

                            }
                        }
                        if (mState == DONE) {
                            if (tempY - mStartY > 0) {
                                mState = PULL_To_REFRESH;
                                changeHeaderViewByState();

                            }
                        }
                        // 更新headView的size
                        if (mState == PULL_To_REFRESH) {
                            mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight + (tempY - mStartY) / RATIO, 0, 0);
                        }
                        // 更新headView的paddingTop
                        if (mState == RELEASE_To_REFRESH) {
                            mHeadViewLinearLayout.setPadding(0, (tempY - mStartY) / RATIO - mHeadContentHeight, 0, 0);
                        }
                    }
                    break;
            }
        }
        try {
            return super.onTouchEvent(event);
        } catch (IllegalStateException e) {
            return true;
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    /**
     * 当状态改变时候，调用该方法，以更新界面
     */
    private void changeHeaderViewByState() {
        switch (mState) {
            case RELEASE_To_REFRESH:
                releaeeToRefresh();
                break;
            case PULL_To_REFRESH:
                pullToRefresh();
                break;
            case REFRESHING:
                refreshing();
                break;
            case DONE:
                done();
                break;
        }
    }

    /**
     * 添加监听
     */
    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    public static void setOnEventUpListener(onEventUpListener uplistener) {
        mUplistener = uplistener;
    }

    /**
     * 下拉回调接口
     */
    public interface OnRefreshListener {
        void onRefresh();

        void onUpload();
    }

    /**
     * 加载完成
     */
    public void onRefreshComplete() {
        mState = DONE;
        mRepeate = true;
        changeHeaderViewByState();
        putTime();
        daoClear();
    }

    public void initHeaderTime(Class clazz) {
        mClazz = clazz;
        putTime();
    }

    private void putTime() {
        if (mClazz != null) {
            DateFormat sdf = new SimpleDateFormat("MM-dd hh:mm");
            String date = sdf.format(new Date(System.currentTimeMillis()));
            PreferencesUtils.savePrefString(mContext, mClazz.getName() + "turnheader_time", date);
            timeTextview.setText(mContext.getResources().getString(R.string.quanzi_turnover_time, date));
        }
    }

    /**
     * 正在刷新方法
     */
    private void onRefresh() {
        if (mRefreshListener != null) {
            setFastScrollEnabled(false);
            mRefreshListener.onRefresh();
        }
    }

    /**
     * 只有调用了这个方法以后，header的宽高才能被计算出来
     */
    private void measureView(View header) {
        ViewGroup.LayoutParams p = header.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int childHeightSpec;
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        header.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 列表初始化
     */
    public void setAdapter(BaseAdapter adapter, Context context) {
        mAdapter = adapter;
        mContext = context;
        mView = View.inflate(mContext, R.layout.zz_item_footer_loading, null);
        mLayout = mView.findViewById(R.id.itemloading);
        mItemClick = mView.findViewById(R.id.item_click);
        mText = (TextView) mView.findViewById(R.id.footer);
        addFooterView(mView);
        refreshing();
        onRefreshComplete();
        setVerticalScrollBarEnabled(false);
        setFastScrollEnabled(false);
        super.setAdapter(adapter);
    }

    public void removeFooter() {
        removeFooterView(mView);
    }

    /**
     * 手动设置底部无更多到上边的距离
     */
    public void setFooterPadingTop(int top) {
        mText.setPadding(mText.getPaddingLeft(), top, mText.getPaddingRight(), mText.getPaddingBottom());
    }

    /**
     * 松开即可刷新动作
     */
    public void releaeeToRefresh() {
        mArrowImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        tipsTextview.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mAnimation);
        tipsTextview.setText(mContext.getString(R.string.mzw_let_go_refresh));
    }

    /**
     * 下拉刷新动作
     */
    public void pullToRefresh() {
        mProgressBar.setVisibility(View.GONE);
        tipsTextview.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.VISIBLE);
        if (mIsBack) {
            mIsBack = false;
            mArrowImageView.clearAnimation();
            mArrowImageView.startAnimation(mReverseAnimation);
            tipsTextview.setText(mContext.getString(R.string.mzw_pull_refresh));
        } else {
            tipsTextview.setText(mContext.getString(R.string.mzw_pull_refresh));
        }
        tipsTextview.startAnimator();
        timeTextview.startAnimator();
    }

    /**
     * 加载中
     */
    public void refreshing() {
        mIsRefreshable = false;
        mHeadViewLinearLayout.setPadding(0, 0, 0, 0);
        mProgressBar.setVisibility(View.VISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.GONE);
        tipsTextview.setText(mTipsString);

    }

    /**
     * 加载中并且位于顶部
     */
    public void refreshingTop() {
        setSelection(0);
        refreshing();
    }

    /**
     * 加载结束
     */
    public void done() {
        mIsRefreshable = true;
        mHeadViewLinearLayout.setPadding(0, -1 * mHeadContentHeight, 0, 0);
        mProgressBar.setVisibility(View.GONE);
        mArrowImageView.clearAnimation();
        mArrowImageView.setImageResource(R.mipmap.wf_listview_arrow);
        tipsTextview.setText(mContext.getString(R.string.mzw_already_finish));
        tipsTextview.stopAnimator();
        timeTextview.stopAnimator();
    }

    /**
     * 移除上拉刷新
     */
    public void removeHead() {
        mIsRefreshableNohead = false;
        removeHeaderView(mHeadViewLinearLayout);
    }

    /**
     * 添加上拉刷新
     */
    public void addHead() {
        // addHeaderView(mHeadViewLinearLayout, null, false);
    }

    /**
     * 修改头文字内容
     */
    public void setTipsString(String string) {
        mTipsString = string;
    }

    /**
     * 修改顶部文字内容
     */
    public void setBootomString(String string) {
        mResultListViewMoreTextView.setText(string);
    }

    /**
     * 处理事件传递
     */
    public interface onEventUpListener {
        /**
         * 手指抬起
         */
        void onUp();
    }
}
