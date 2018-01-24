package com.qike.telecast.presentation.presenter.my;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.my.MyInfoBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class MyPresenter implements BaseBizListener {
    private MyInfoBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public MyPresenter(){
        mBiz = new MyInfoBiz();
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
