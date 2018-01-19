package com.fy.catchdoll.presentation.model.business.room;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.room.EnterRoomDto;
import com.fy.catchdoll.presentation.model.dto.room.GetDollDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class GetDollInfoBiz extends BaseLoadListener {
    private BazaarGetDao<GetDollDto> mIndexDao;
    private BaseBizListener mCallback;
    public GetDollInfoBiz(){
        mIndexDao = new BazaarGetDao<GetDollDto>(Paths.NEWAPI + Paths.GET_DOLL_INFO, GetDollDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String recorid,String roomId){
        mIndexDao.setNoCache();
        mIndexDao.putParams("machine_id", roomId);
        mIndexDao.putParams("record_id",recorid);
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
