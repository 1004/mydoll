package com.fy.catchdoll.presentation.presenter.box;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.box.BoxInfoBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxInfoPresenter implements BaseBizListener {
    private BoxInfoBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public BoxInfoPresenter(){
        mBiz = new BoxInfoBiz();
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
