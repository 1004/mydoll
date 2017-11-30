package com.fy.catchdoll.presentation.model.business.my;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.BazaarPostDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.account.UserInfo;
import com.fy.catchdoll.presentation.model.dto.my.MySentCodeDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class SentInviteCodeBiz extends BaseLoadListener{
    private BazaarPostDao<MySentCodeDto> mIndexDao;
    private BaseBizListener mCallback;

    public SentInviteCodeBiz(){
        mIndexDao = new BazaarPostDao<MySentCodeDto>(Paths.NEWAPI + Paths.MY_SEND_CODE, MySentCodeDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String code){
        mIndexDao.putParams("invitation_code",code);
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
