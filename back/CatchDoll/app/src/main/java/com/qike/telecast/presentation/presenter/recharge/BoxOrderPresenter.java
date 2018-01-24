package com.qike.telecast.presentation.presenter.recharge;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.recharge.BoxOrderBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/12/13 0013.
 */
public class BoxOrderPresenter implements BaseBizListener {
    private BoxOrderBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public BoxOrderPresenter(){
        mBiz = new BoxOrderBiz();
        mBiz.registBizCallBack(this);
    }

    public void firstTask(){
        mBiz.firstTask();
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
