package com.fy.catchdoll.presentation.model.business.box;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.BazaarPostDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.my.MySentCodeDto;

import java.util.Objects;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by wst on 2017/12/2.
 */
public class UpdataInfoBiz extends BaseLoadListener{
    private BazaarPostDao<Object> mIndexDao;
    private BaseBizListener mCallback;

    public UpdataInfoBiz(){
        mIndexDao = new BazaarPostDao<Object>(Paths.NEWAPI + Paths.UPDATE_ADDRESS_DATA, Object.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String name,String phone,String address){
        mIndexDao.putParams("name",name);
        mIndexDao.putParams("phone",phone);
        mIndexDao.putParams("address",address);
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
