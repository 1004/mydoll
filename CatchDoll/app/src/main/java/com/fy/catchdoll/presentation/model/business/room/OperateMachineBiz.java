package com.fy.catchdoll.presentation.model.business.room;

import com.fy.catchdoll.module.network.BazaarGetDao;
import com.fy.catchdoll.module.network.Paths;
import com.fy.catchdoll.presentation.model.business.BaseBizListener;
import com.fy.catchdoll.presentation.model.dto.room.EnterRoomDto;
import com.fy.catchdoll.presentation.model.dto.room.OperateMachineDto;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.BaseLoadListener;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.IDao;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.support.Result;

/**
 * Created by xky on 2018/1/9 0009.
 */
public class OperateMachineBiz extends BaseLoadListener {
    private BazaarGetDao<OperateMachineDto> mIndexDao;
    private BaseBizListener mCallback;
    public OperateMachineBiz(){
        mIndexDao = new BazaarGetDao<OperateMachineDto>(Paths.NEWAPI + Paths.MACHINE_OPERATE_URL, OperateMachineDto.class, BazaarGetDao.ARRAY_DATA_CHUNK);
        mIndexDao.registerListener(this);
    }

    public void firstTask(String roomId,String type,int position){
        mIndexDao.setNoCache();
        mIndexDao.putParams("machine_id", roomId);
        mIndexDao.putParams("camera_position", position);
        mIndexDao.putParams("type",type);
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
