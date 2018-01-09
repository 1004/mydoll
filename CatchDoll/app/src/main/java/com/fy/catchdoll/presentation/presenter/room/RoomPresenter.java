package com.fy.catchdoll.presentation.presenter.room;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.room.EnterRoomBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class RoomPresenter {
    private EnterRoomBiz mEnterBiz;
    private IBasePresenterLinstener mEnterPresenterCallBack;
    public RoomPresenter(){
        initBiz();
        setListener();
    }

    private void initBiz() {
        mEnterBiz = new EnterRoomBiz();
    }

    private void setListener(){
        mEnterBiz.registBizCallBack(mEnterCallBack);
    }

    public void registPresenterCallBack(IBasePresenterLinstener linstener){
        mEnterPresenterCallBack = linstener;
    }

    public void enterRoom(String roomId){
        mEnterBiz.firstTask(roomId);
    }


    private BaseBizListener mEnterCallBack = new BaseBizListener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (mEnterPresenterCallBack != null){
                mEnterPresenterCallBack.dataResult(obj,page,status);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            if (mEnterPresenterCallBack != null){
                mEnterPresenterCallBack.errerResult(code,msg);
            }
        }
    };


}
