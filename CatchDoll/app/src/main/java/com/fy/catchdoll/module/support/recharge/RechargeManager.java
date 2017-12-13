package com.fy.catchdoll.module.support.recharge;

import android.content.Context;

import com.fy.catchdoll.module.support.recharge.base.IPayChannel;
import com.fy.catchdoll.module.support.recharge.common.MPayType;
import com.fy.catchdoll.module.support.recharge.common.dto.BasePayEntry;
import com.fy.catchdoll.module.support.recharge.impl.WxPayChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xky on 2017/12/11 0011.
 *
 * 充值管理器
 */
public class RechargeManager implements IPayChannel{
    private static RechargeManager instance = null;
    private Map<MPayType,IPayChannel> mPayCores = new HashMap<>();
    private MPayType mPayType ;

    private RechargeManager(){
        initDefaultPayChannel();
    }

    public static RechargeManager getInstance(){
        if (instance == null){
            synchronized (RechargeManager.class){
                if (instance == null){
                    instance = new RechargeManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化一个默认的支付方式
     */
    private void initDefaultPayChannel(){
        mPayCores.put(MPayType.WX,new WxPayChannel());
        mPayType = MPayType.WX;
    }

    /**
     * 设置当前的支付类型
     * @param type
     */
    public void setCurrentPayType(MPayType type){
        this.mPayType = type;
    }

    /**
     * 注册支付渠道
     * @param type
     * @param channel
     */
    public void registPayChannel(MPayType type,IPayChannel channel){
        if (channel != null){
            mPayCores.put(type,channel);
        }
    }


    /**
     * 解除注册支付渠道
     * @param type
     */
    public void unRegistPayChannel(MPayType type){
        mPayCores.remove(type);
    }


    /**
     * 获取当前的支付类型
     * @return
     */
    public MPayType getCurrentPayType(){
        return mPayType;
    }


    /**
     * 调支付
     * @param entry
     */
    @Override
    public void Pay(Context context,BasePayEntry entry) {
        MPayType currentPayType = getCurrentPayType();
        if (currentPayType != null){
            IPayChannel payChannel = mPayCores.get(currentPayType);
            if (payChannel != null){
                payChannel.Pay(context,entry);
            }
        }
    }


}
