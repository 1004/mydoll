package com.fy.catchdoll.presentation.model.business.msg;

import android.content.Context;


import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.model.dto.msg.SocketUrlDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;


/**
 * Created by xky on 16/6/21.
 */
public class UrlBiz extends BaseLoadListener {
    private Context mContext;
    private BazaarGetDao<SocketUrlDto> mGetWsUrlDao;
    private BaseCallbackPresenter mCallback;

    public UrlBiz(Context context){
        mContext = context;
        mGetWsUrlDao = new BazaarGetDao<SocketUrlDto>(Paths.NEWAPI + Paths.NEW_GET_WSURL, SocketUrlDto.class,
                BazaarGetDao.ARRAY_DATA_CHUNK);
    }

    public void loadSocketUrl(){
        mGetWsUrlDao.registerListener(this);
        User user = AccountManager.getInstance().getUser();
        if (user == null){
            return;
        }
        mGetWsUrlDao.putParams("room_id", user.getId());
        mGetWsUrlDao.setNoCache();
        mGetWsUrlDao.asyncDoAPI();
    }

    public void setOnBizCalLBack(BaseCallbackPresenter calLBack) {
        mCallback = calLBack;
    }


    @Override
    public void onComplete(IDao.ResultType resultType) {
        super.onComplete(resultType);
        if (mCallback != null) {
            mCallback.callbackResult(mGetWsUrlDao.getData());
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
