package com.fy.catchdoll.presentation.model.business.orderhistory;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.box.OrderHistoryDto;
import com.fy.catchdoll.presentation.model.dto.doll.CatchHistoryDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/12/14 0014.
 */
public class CatchHistoryBiz extends BaseLoadListener {
    private BazaarGetDao<CatchHistoryDto> mIndexDao;
    private BaseBizListener mCallback;
    public CatchHistoryBiz(){
        mIndexDao = new BazaarGetDao<CatchHistoryDto>(Paths.NEWAPI + Paths.CATCH_HISTORY_INFO, CatchHistoryDto.class, BazaarGetDao.ARRAY_DATA);
        mIndexDao.registerListener(this);
    }

    public void firstTask(){
        mIndexDao.setNoCache();
        mIndexDao.startUpTask();
    }

    public void nextTask(){
        mIndexDao.setNoCache();
        mIndexDao.nextUpTask();
    }


    public void registBizCallBack(BaseBizListener callBack){
        this.mCallback = callBack;
    }

    @Override
    public void onComplete(IDao.ResultType resultType) {
        super.onComplete(resultType);
        if (mCallback != null){
            mCallback.dataResult(mIndexDao.getList(),mIndexDao.getPage(),mIndexDao.getCode());
        }
    }

    @Override
    public void onError(Result result) {
        super.onError(result);
        if (mCallback != null){
            mCallback.errerResult(result.getCode(),result.getErrmsg());
        }
    }
}
