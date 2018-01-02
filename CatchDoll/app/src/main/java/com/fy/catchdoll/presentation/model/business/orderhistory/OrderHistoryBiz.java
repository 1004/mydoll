package com.fy.catchdoll.presentation.model.business.orderhistory;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.box.OrderHistoryDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/12/14 0014.
 */
public class OrderHistoryBiz extends BaseLoadListener {
    private BazaarGetDao<OrderHistoryDto> mIndexDao;
    private BaseBizListener mCallback;
    public OrderHistoryBiz(){
        mIndexDao = new BazaarGetDao<OrderHistoryDto>(Paths.NEWAPI + Paths.ORDER_HISTORY_URL, OrderHistoryDto.class, BazaarGetDao.ARRAY_DATA);
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
