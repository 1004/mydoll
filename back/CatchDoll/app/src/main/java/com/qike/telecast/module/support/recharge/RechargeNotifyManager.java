package com.qike.telecast.module.support.recharge;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xky on 2017/12/13 0013.
 *
 * 支付状态分发器
 */
public class RechargeNotifyManager {
    public static final int RECHARGE_SUCCESS_STATE = 0;
    public static final int RECHARGE_ERROR_STATE = -1;
    public static final int RECHARGE_CANCEL_STATE = -2;
    private Map<String,OnPayStateListener> mPayStateListeners = new HashMap<>();

    private static RechargeNotifyManager instance;
    private RechargeNotifyManager(){}
    public static RechargeNotifyManager getInstance(){
        if (instance == null){
            synchronized (RechargeNotifyManager.class){
                if (instance == null){
                    instance = new RechargeNotifyManager();
                }
            }
        }
        return instance;
    }


    public interface OnPayStateListener{
        public void onPayState(int state);
    }

    public void registPayStateListener(String key,OnPayStateListener listener){
        if (listener != null && !TextUtils.isEmpty(key)){
            mPayStateListeners.put(key,listener);
        }
    }

    public void unRegistPayStateListener(String key){
        mPayStateListeners.remove(key);
    }


    public void notifyPayState(int state){
        Set<Map.Entry<String, OnPayStateListener>> entries = mPayStateListeners.entrySet();
        for (Map.Entry<String, OnPayStateListener> entry :entries){
            OnPayStateListener value = entry.getValue();
            if (value != null){
                value.onPayState(state);
            }
        }
    }





}
