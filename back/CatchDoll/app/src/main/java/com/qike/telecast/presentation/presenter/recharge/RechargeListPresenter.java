package com.qike.telecast.presentation.presenter.recharge;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.recharge.RecharegeListBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeListPresenter implements BaseBizListener {
    private RecharegeListBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public RechargeListPresenter(){
        mBiz = new RecharegeListBiz();
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
