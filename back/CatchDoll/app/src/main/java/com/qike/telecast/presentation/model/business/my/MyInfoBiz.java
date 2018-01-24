package com.qike.telecast.presentation.model.business.my;

import com.qike.telecast.module.network.BazaarGetDao;
import com.qike.telecast.module.network.Paths;
import com.qike.telecast.presentation.model.business.BaseBizListener;
import com.qike.telecast.presentation.model.dto.account.UserInfo;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class MyInfoBiz extends BaseLoadListener {
    private BazaarGetDao<UserInfo> mIndexDao;
    private BaseBizListener mCallback;

    public MyInfoBiz(){
        mIndexDao = new BazaarGetDao<UserInfo>(Paths.NEWAPI + Paths.MY_INFO_DATA, UserInfo.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(){
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
