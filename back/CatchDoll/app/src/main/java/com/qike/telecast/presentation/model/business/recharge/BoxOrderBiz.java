package com.qike.telecast.presentation.model.business.recharge;

import com.qike.telecast.module.network.BazaarGetDao;
import com.qike.telecast.module.network.BazaarPostDao;
import com.qike.telecast.module.network.Paths;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.dto.recharge.BoxOrderDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/12/11 0011.
 * 背包订单提交
 */
public class BoxOrderBiz extends BaseLoadListener {
    private BazaarGetDao<BoxOrderDto> mIndexDao;
    private BaseBizListener mCallback;
    public BoxOrderBiz(){
        mIndexDao = new BazaarGetDao<BoxOrderDto>(Paths.NEWAPI + Paths.BOX_ORDER_COMMIT, BoxOrderDto.class, BazaarPostDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(){
        mIndexDao.putParams("payment_method","1");
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
