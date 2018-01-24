package com.qike.telecast.presentation.presenter.update2;

import android.content.Context;
import android.text.TextUtils;


import com.qike.telecast.presentation.presenter.update2.Inter.IOnCheckVersonCallBack;
import com.qike.telecast.presentation.presenter.update2.Inter.OnUploadListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xky on 16/6/22.
 * 自动登录管理器
 */
public class UploadManager {
    private UploadPresenter mUploadPresenter;

    private UploadManager() {

    }

    private static UploadManager instance;
    private Map<String, OnUploadListener> mUploadListeners = new HashMap<String, OnUploadListener>();

    public enum UploadState {STATE, PROGRESS, FINISH, ERROR, CANCEL}

    public static UploadManager getInstance() {
        if (instance == null) {
            instance = new UploadManager();
        }
        return instance;
    }

    public void registOnUploadListener(String key, OnUploadListener listener) {
        if (!TextUtils.isEmpty(key) && listener != null) {
            mUploadListeners.put(key, listener);
        }
    }

    public void unRegistOnUploadListener(String key) {
        if (!TextUtils.isEmpty(key) && mUploadListeners.containsKey(key)) {
            mUploadListeners.remove(key);
        }
    }

    public void notifyListener(UploadState state, Object... objects) {
        Set<Map.Entry<String, OnUploadListener>> entries = mUploadListeners.entrySet();
        for (Map.Entry<String, OnUploadListener> entry : entries) {
            OnUploadListener value = entry.getValue();
            switch (state) {
                case STATE:
                    if (value != null) {
                        value.onStartUpload();
                    }
                    break;
                case PROGRESS:
                    if (value != null && objects != null && objects.length >= 3) {
                        value.onUploadProgress(((Long) objects[0]), (Long) objects[1], (Integer) objects[2]);
                    }
                    break;
                case FINISH:
                    if (value != null && objects != null && objects.length >= 1) {
                        value.onUploadFinish((String) objects[0]);
                    }
                    break;
                case ERROR:
                    if (value != null && objects != null && objects.length == 1) {
                        value.onUploadError((String) objects[0]);
                    }
                    break;
                case CANCEL:
                    if (value != null) {
                        value.onUploadCancel();
                    }
                    break;
            }
        }
    }


    public void checkUpLoad(Context context, IOnCheckVersonCallBack checkCallback) {
        mUploadPresenter = new UploadPresenter(context, checkCallback);
        mUploadPresenter.checkUpload();
    }

    public void destoryData() {
        if (mUploadPresenter != null) {
            mUploadPresenter.destoryData();
        }
    }


}
