package com.fy.catchdoll.presentation.model.business.recharge;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.box.BoxInfoDto;
import com.fy.catchdoll.presentation.model.dto.recharge.RechargeItem;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by wst on 2017/12/2.
 */
public class RecharegeListBiz extends BaseLoadListener {
    private BazaarGetDao<RechargeItem> mIndexDao;
    private BaseBizListener mCallback;
    public RecharegeListBiz(){
        mIndexDao = new BazaarGetDao<RechargeItem>(Paths.NEWAPI + Paths.RECHARGE_LIST_, RechargeItem.class, BazaarGetDao.ARRAY_DATA);
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
