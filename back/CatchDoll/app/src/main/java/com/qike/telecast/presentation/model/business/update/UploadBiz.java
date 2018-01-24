package com.qike.telecast.presentation.model.business.update;

import android.content.Context;

import com.qike.telecast.module.network.BazaarGetDao;
import com.qike.telecast.module.network.Paths;
import com.qike.telecast.presentation.model.dto.updata.UpdateDto;
import com.qike.telecast.presentation.presenter.BaseCallbackPresenter;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;


/**
 * Created by xky on 16/6/22.
 */
public class UploadBiz extends BaseLoadListener {
    private BazaarGetDao<UpdateDto> mGetUpdateDao;
    private BaseCallbackPresenter mCallback;
    public static final String UMENG_CHANNEL = "UMENG_CHANNEL";
    private Context mContext;
    public UploadBiz(Context context){
        mContext = context;
        mGetUpdateDao = new BazaarGetDao<UpdateDto>(Paths.NEWAPI+Paths.UPLOAD_VERSION, UpdateDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mGetUpdateDao.registerListener(this);
    }

    public void setOnBizCalLBack(BaseCallbackPresenter calLBack) {
        mCallback = calLBack;
    }

    public void checkUpload(){
        mGetUpdateDao.setNoCache();
        mGetUpdateDao.asyncDoAPI();
    }

    @Override
    public void onComplete(IDao.ResultType resultType) {
        super.onComplete(resultType);
        if (mCallback != null) {
            UpdateDto data= mGetUpdateDao.getData();
            if (data != null){
                mCallback.callbackResult(mGetUpdateDao.getData());
            }
        }
    }

    @Override
    public void onError(Result result) {
        super.onError(result);
        if (mCallback != null) {
            mCallback.onErrer(result.getCode(), result.getErrmsg());
        }
    }
}
