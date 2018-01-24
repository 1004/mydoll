package com.qike.telecast.presentation.presenter.my;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.my.MySpendBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class MySpendPresenter implements BaseBizListener {
    private MySpendBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public MySpendPresenter(){
        mBiz = new MySpendBiz();
        mBiz.registBizCallBack(this);
    }

    public void firstTask(){
        mBiz.firstTask();
    }

    public void nextTask(){
        mBiz.nextTask();
    }


    public void registPresenterCallBack(IBasePresenterLinstener linstener){
        mListener = linstener;
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (mListener != null){
            mListener.dataResult(obj,page,status);
        }
    }

    @Override
    public void errerResult(int code, String msg) {
        if (mListener != null){
            mListener.errerResult(code,msg);
        }
    }
}
