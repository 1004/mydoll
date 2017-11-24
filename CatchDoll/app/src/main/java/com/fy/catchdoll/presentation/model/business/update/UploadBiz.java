package com.fy.catchdoll.presentation.model.business.update;

import android.content.Context;
import android.text.TextUtils;

import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.dto.updata.UpdateDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;

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
        String metaData = DeviceUtils.getMetaData(mContext, UMENG_CHANNEL);
        String app_id = "4";
        String app_sign= "NzRhYzc0OWM2MWYz";
        if (!TextUtils.isEmpty(metaData)){
            String[] split = metaData.split("_");
            if (split.length >= 3){
                app_id = split[1];
                app_sign = split[2];
            }
        }
        mGetUpdateDao.putParams("app_id", app_id);
        mGetUpdateDao.putParams("app_sign", app_sign);
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
