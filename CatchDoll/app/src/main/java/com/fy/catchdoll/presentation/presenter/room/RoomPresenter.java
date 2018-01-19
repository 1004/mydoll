package com.fy.catchdoll.presentation.presenter.room;

import com.fy.catchdoll.module.network.Page;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.business.room.EnterRoomBiz;
import com.fy.catchdoll.presentation.model.business.room.GetDollInfoBiz;
import com.fy.catchdoll.presentation.model.business.room.OperateMachineBiz;
import com.fy.catchdoll.presentation.presenter.IBasePresenterLinstener;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class RoomPresenter {
    private EnterRoomBiz mEnterBiz;
    private OperateMachineBiz mOperateBiz;
    private GetDollInfoBiz mGetGrepBiz;
    private IBasePresenterLinstener mEnterPresenterCallBack;
    private IBasePresenterLinstener mOperatePresenterCallBack;
    private IBasePresenterLinstener mGetInfoPresenterCallBack;
    public RoomPresenter(){
        initBiz();
        setListener();
    }

    private void initBiz() {
        mEnterBiz = new EnterRoomBiz();
        mOperateBiz = new OperateMachineBiz();
        mGetGrepBiz = new GetDollInfoBiz();
    }

    private void setListener(){
        mEnterBiz.registBizCallBack(mEnterCallBack);
        mOperateBiz.registBizCallBack(mOperateCallBack);
        mGetGrepBiz.registBizCallBack(mGetInfoCallBack);
    }

    public void registPresenterCallBack(IBasePresenterLinstener linstener){
        mEnterPresenterCallBack = linstener;
    }
    public void registOperateMachineCallBack(IBasePresenterLinstener linstener){
        mOperatePresenterCallBack = linstener;
    }
    public void registGetDollInfoCallBack(IBasePresenterLinstener linstener){
        mGetInfoPresenterCallBack = linstener;
    }

    public void enterRoom(String roomId){
        mEnterBiz.firstTask(roomId);
    }

    public void operateMachine(String roomId,String type,int position){
        mOperateBiz.firstTask(roomId,type,position);
    }

    public void getGrepDollInfo(String recordId,String roomId){
        mGetGrepBiz.firstTask(recordId,roomId);
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

    private BaseBizListener mOperateCallBack = new BaseBizListener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (mOperatePresenterCallBack != null){
                mOperatePresenterCallBack.dataResult(obj,page,status);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            if (mOperatePresenterCallBack != null){
                mOperatePresenterCallBack.errerResult(code,msg);
            }
        }
    };

    private BaseBizListener mGetInfoCallBack = new BaseBizListener() {
        @Override
        public void dataResult(Object obj, Page page, int status) {
            if (mGetInfoPresenterCallBack != null){
                mGetInfoPresenterCallBack.dataResult(obj,page,status);
            }
        }

        @Override
        public void errerResult(int code, String msg) {
            if (mGetInfoPresenterCallBack != null){
                mGetInfoPresenterCallBack.errerResult(code,msg);
            }
        }
    };


}
