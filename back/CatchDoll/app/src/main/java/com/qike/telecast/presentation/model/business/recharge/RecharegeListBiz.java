package com.qike.telecast.presentation.model.business.recharge;

import com.qike.telecast.module.network.BazaarGetDao;
import com.qike.telecast.module.network.Paths;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.dto.recharge.RechargeDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by wst on 2017/12/2.
 */
public class RecharegeListBiz extends BaseLoadListener {
    private BazaarGetDao<RechargeDto> mIndexDao;
    private BaseBizListener mCallback;
    public RecharegeListBiz(){
        mIndexDao = new BazaarGetDao<RechargeDto>(Paths.NEWAPI + Paths.RECHARGE_LIST_, RechargeDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(){
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
            mCallback.dataResult(mIndexDao.getData(),mIndexDao.getPage(),mIndexDao.getCode());
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
