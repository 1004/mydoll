package com.qike.telecast.presentation.presenter.box;

import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.business.box.UpdataInfoBiz;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by wst on 2017/12/2.
 */
public class UpdateInfoPresenter implements BaseBizListener {
    private UpdataInfoBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public UpdateInfoPresenter(){
        mBiz = new UpdataInfoBiz();
        mBiz.registBizCallBack(this);
    }

    public void firstTask(String name,String phone,String address){
        mBiz.firstTask(name,phone,address);
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
