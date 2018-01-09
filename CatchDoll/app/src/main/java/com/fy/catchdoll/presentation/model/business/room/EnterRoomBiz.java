package com.fy.catchdoll.presentation.model.business.room;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.room.CatchRecord;
import com.fy.catchdoll.presentation.model.dto.room.EnterRoomDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class EnterRoomBiz extends BaseLoadListener {
    private BazaarGetDao<EnterRoomDto> mIndexDao;
    private BaseBizListener mCallback;
    public EnterRoomBiz(){
        mIndexDao = new BazaarGetDao<EnterRoomDto>(Paths.NEWAPI + Paths.ENTER_ROOM_URL, EnterRoomDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String roomId){
        mIndexDao.setNoCache();
        mIndexDao.putParams("machine_id",roomId);
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
