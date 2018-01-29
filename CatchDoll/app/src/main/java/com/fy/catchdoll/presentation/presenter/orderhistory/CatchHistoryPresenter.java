package com.fy.catchdoll.presentation.presenter.orderhistory;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.orderhistory.CatchHistoryBiz;
import com.fy.catchdoll.presentation.model.business.orderhistory.OrderHistoryBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/12/14 0014.
 */
public class CatchHistoryPresenter implements BaseBizListener {
    private CatchHistoryBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public CatchHistoryPresenter(){
        mBiz = new CatchHistoryBiz();
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
