package com.fy.catchdoll.presentation.model.business.my;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.account.UserInfo;
import com.fy.catchdoll.presentation.model.dto.doll.IndexDataDto;

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
