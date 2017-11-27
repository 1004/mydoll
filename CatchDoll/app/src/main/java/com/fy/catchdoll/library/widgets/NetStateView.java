package com.fy.catchdoll.library.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fy.catchdoll.R;


/**
 * <p>网络处理类</p><br/>
 * <p>TODO (类的详细的功能描述)</p>
 *
 * @author xky
 * @since 1.0.0
 */
public class NetStateView extends RelativeLayout {
    private LayoutInflater mInflater;
    private View mView;
    private View mLoadingContainer;
    private View mEmptContentLayout;
    private View[] mContentViews;
    private Context mContext;
    private IRefreshListener mListener;
    private RelativeLayout mServerError;

    public NetStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
        setListener();
    }

    /**
     * <p>内容view</p><br/>
     * 该view和网络状态处于相反关系【处理一些显示无网络状态，但是可以点击后边view】
     *
     * @param views
     * @author xky
     * @since 1.0.0
     */
    public void setContentView(View... views) {
        mContentViews = views;
    }

    private void setListener() {
        mServerError.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRefrsh(v);
                }
            }
        });
        mEmptContentLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRefrsh(v);
                }
            }
        });
    }

    /**
     * 展示某种状态
     *
     * @param state
     */
    public void show(NetState state) {
        switch (state) {
            case LOADING:
                showLoading();
                break;
            case NETERROR:
                showNetError();
                break;
            case CONTENT:
                showContent();
                break;
            case EMPTY:
                showEmpty();
                break;
            default:
                break;
        }
    }

    private void showEmpty() {
        mView.setVisibility(View.VISIBLE);
        mLoadingContainer.setVisibility(View.GONE);
        mEmptContentLayout.setVisibility(View.VISIBLE);
        mServerError.setVisibility(View.GONE);
        hideContentViews();
    }

    private void showNetError() {
        mView.setVisibility(View.VISIBLE);
        mLoadingContainer.setVisibility(View.GONE);
        mEmptContentLayout.setVisibility(View.GONE);
        mServerError.setVisibility(View.VISIBLE);
        hideContentViews();
    }

    public void showLoading() {
        mView.setVisibility(View.VISIBLE);
        mLoadingContainer.setVisibility(View.VISIBLE);
        mEmptContentLayout.setVisibility(View.GONE);
        mServerError.setVisibility(View.GONE);
        final AnimationDrawable animDrawable = (AnimationDrawable) loading
                .getBackground();
        loading.post(new Runnable() {
            @Override
            public void run() {
                animDrawable.start();
            }
        });
        hideContentViews();
    }

    private void hideContentViews() {
        if (mContentViews != null) {
            for (View view : mContentViews) {
                view.setVisibility(View.GONE);
            }
        }
    }

    private void showContentViews() {
        if (mContentViews != null) {
            for (View view : mContentViews) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置刷新
     */
    public void setOnRefreshListener(IRefreshListener listener) {
        mListener = listener;
    }

    public interface IRefreshListener {
        public void onRefrsh(View view);
    }

    ImageView loading;

    private void initView() {
        mView = mInflater.inflate(R.layout.net_state, this);
        mLoadingContainer = mView.findViewById(R.id.loading_container);
        mEmptContentLayout = mView.findViewById(R.id.empt_content_layout);
        mServerError = (RelativeLayout) mView.findViewById(R.id.server_error_container);
        loading = (ImageView) mView.findViewById(R.id.loading);
//		loading.setImageResource(R.anim.voice_to_icon);
        showContent();
    }

    private void showContent() {
        mView.setVisibility(View.GONE);
        showContentViews();
    }


    /**
     * <p>网络状态</p><br/>
     * <p>TODO (类的详细的功能描述)</p>
     *
     * @author xky
     *         LOADING ：加载中
     *         NETERROR：网络错误
     *         CONTENT：展示内容
     * @since 1.0.0
     */
    public enum NetState {
        /**
         * 加载中...
         */
        LOADING,
        /**
         * 网络错误
         */
        NETERROR,
//		/**其他错误*/
//		OTHERERROR,
        /**
         * 展示内容
         */
        CONTENT,
        /*空*/
        EMPTY
    }


}
