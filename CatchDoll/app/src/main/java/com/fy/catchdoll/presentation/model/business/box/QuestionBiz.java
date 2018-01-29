package com.fy.catchdoll.presentation.model.business.box;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.BazaarPostDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by wst on 2017/12/2.
 * 申诉
 */
public class QuestionBiz extends BaseLoadListener{
    private BazaarPostDao<Object> mIndexDao;
    private BaseBizListener mCallback;

    public QuestionBiz(){
        mIndexDao = new BazaarPostDao<Object>(Paths.NEWAPI + Paths.CATCH_QUESTION_INFO, Object.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String id){
        mIndexDao.putParams("gr_id",id);
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
