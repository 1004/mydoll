package com.qike.telecast.presentation.view.fragements;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.qike.telecast.presentation.view.activitys.base.IViewOperater;


public abstract class BaseFragment extends Fragment implements IViewOperater {

    protected View mView;
    protected LayoutInflater mInflater;
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected abstract void lazyLoad();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = getView(inflater);
        mInflater = inflater;
        mView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initView();
        initData();
        setListener();
        loadData();
        super.onActivityCreated(savedInstanceState);
    }

    private View getView(LayoutInflater inflater) {
        int mLayoutId = getLayoutId();
        return inflater.inflate(mLayoutId, null);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * <p>
     * 获得布局view
     * </p>
     * <br/>
     *
     * @return
     * @author xky
     * @since 1.0.0
     */
    public View getContentView() {
        return mView;
    }

    public View findViewById(int id) {
        return mView == null ? null : mView.findViewById(id);
    }

    /**
     * <p>
     * toast
     * </p>
     * <br/>
     *
     * @param msg
     * @author xky
     * @since 1.0.0
     */
    public void Toast(String msg) {
        if (msg != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public Context getContext() {
        return getActivity();
    }

    /**
     * <p>
     * 在绑定Activiy时候，是否存在这样的Activity
     * </p>
     * <br/>
     *
     * @return true:存在
     * @author xky
     * @since 1.0.0
     */
    public boolean isActivityContains() {
        return getActivity() != null && isAdded();
    }

    public abstract int getLayoutId();




}
