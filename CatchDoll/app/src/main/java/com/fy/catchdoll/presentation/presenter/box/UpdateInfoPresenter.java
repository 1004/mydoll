package com.fy.catchdoll.presentation.presenter.box;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.box.UpdataInfoBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

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
