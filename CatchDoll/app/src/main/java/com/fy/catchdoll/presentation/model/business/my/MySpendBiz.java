package com.fy.catchdoll.presentation.model.business.my;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.my.MySpendDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class MySpendBiz extends BaseLoadListener{
    private BazaarGetDao<MySpendDto> mIndexDao;
    private BaseBizListener mCallback;
    public MySpendBiz(){
        mIndexDao = new BazaarGetDao<MySpendDto>(Paths.NEWAPI + Paths.MY_SPEND_DATA, MySpendDto.class, BazaarGetDao.ARRAY_DATA);
        mIndexDao.registerListener(this);
    }

    public void firstTask(){
        mIndexDao.startUpTask();
    }

    public void nextTask(){
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
