package com.fy.catchdoll.presentation.model.business.recharge;

import com.fy.catchdoll.module.network.BazaarPostDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.module.support.recharge.common.dto.WxPayEntry;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/12/11 0011.
 * 订单创建
 */
public class RechargeOrderBiz extends BaseLoadListener {
    private BazaarPostDao<WxPayEntry> mIndexDao;
    private BaseBizListener mCallback;
    public RechargeOrderBiz(){
        mIndexDao = new BazaarPostDao<WxPayEntry>(Paths.NEWAPI + Paths.GOLD_ORDER_DATA, WxPayEntry.class, BazaarPostDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String id){
        mIndexDao.putParams("package_id",id);
        mIndexDao.setNoCache();
        mIndexDao.asyncDoAPI();
    }



    public void registBizCallBack(BaseBizListener callBack){
        this.mCallback = callBack;
    }

    @Override
    public void onComplete(IDao.ResultType resultType) {
        super.onComplete(resultType);
        if (mCallback != null){
            mCallback.dataResult(mIndexDao.getData(),null,1);
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
