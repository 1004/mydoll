package com.fy.catchdoll.presentation.presenter.recharge;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.recharge.RechargeConfimBiz;
import com.fy.catchdoll.presentation.model.business.recharge.RechargeOrderBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/12/11 0011.
 */
public class CheckOrderPresenter implements BaseBizListener {
    private RechargeConfimBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public CheckOrderPresenter(){
        mBiz = new RechargeConfimBiz();
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
