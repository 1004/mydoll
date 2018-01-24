package com.qike.telecast.module.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络变化的监听
 *
 * @author Administrator
 */
public class NetService extends Service {


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetReceiver);
    }

    BroadcastReceiver mNetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                return;
            }
            ConnectivityManager mConManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConManager.getActiveNetworkInfo();
            boolean isHasNet;
            if (info != null && info.isAvailable()) {
                isHasNet = true;
            } else {
                isHasNet = false;
            }
            notificationObserver(isHasNet);
        }
    };

    /**
     * 网络观察者
     *
     * @author Administrator
     */
    public interface INetObserver {
        /**
         * <p>网络更新</p><br/>
         *
         * @param isHasNet
         * @author xky
         * @since 1.0.0
         */
        void onUpdate(boolean isHasNet);
    }

    private static List<INetObserver> mObservers = new ArrayList<INetObserver>();

    /**
     * <p>注册一个网络的观察者</p><br/>
     *
     * @param observer
     * @author xky
     * @since 1.0.0
     */
    public static void registNetObserver(INetObserver observer) {
        if (observer != null) {
            mObservers.add(observer);
        }
    }

    /**
     * <p>解除注册网络观察者</p><br/>
     *
     * @param observer
     * @author xky
     * @since 1.0.0
     */
    public static void unRegistNetObserver(INetObserver observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    public void notificationObserver(boolean isHasNet) {
        for (INetObserver observer : mObservers) {
            observer.onUpdate(isHasNet);
        }
    }

    /**
     * @return true 表示网络可用
     */
    public static boolean isNetdevice(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
