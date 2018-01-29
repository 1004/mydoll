package com.fy.catchdoll.presentation.presenter.box;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.box.QuestionBiz;
import com.fy.catchdoll.presentation.model.business.box.UpdataInfoBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by wst on 2017/12/2.
 *
 * 申诉
 */
public class QuestionPresenter implements BaseBizListener {
    private QuestionBiz mBiz;
    private IBasePresenterLinstener mListener ;
    public QuestionPresenter(){
        mBiz = new QuestionBiz();
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
