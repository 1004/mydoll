package com.qike.telecast.presentation.presenter.recharge;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.recharge.RechargeOrderBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/12/11 0011.
 */
public class RechargeOrderPresenter implements BaseBizListener {
    private RechargeOrderBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public RechargeOrderPresenter(){
        mBiz = new RechargeOrderBiz();
        mBiz.registBizCallBack(this);
    }

    public void firstTask(String id){
        mBiz.firstTask(id);
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