package com.fy.catchdoll.presentation.model.business.index;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.doll.IndexDataDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class IndexBiz extends BaseLoadListener {
    private BazaarGetDao<IndexDataDto> mIndexDao;
    private BaseBizListener mCallback;
    public IndexBiz(){
        mIndexDao = new BazaarGetDao<IndexDataDto>(Paths.NEWAPI + Paths.IDNEX_DATA, IndexDataDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
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

