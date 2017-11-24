package com.fy.catchdoll.presentation.presenter.update2;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by xky on 16/6/22.
 */
public class UploadService extends Service{
    public static final String APK_URL_KEY = "apk_url_key";
    private UploadTask mTask = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initData(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void initData(Intent intent) {
        if (intent == null){
            return;
        }
        String apkurl = intent.getStringExtra(APK_URL_KEY);
        if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED){
            mTask.cancel(true);
        }
        mTask = new UploadTask();
        mTask.execute(apkurl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTask != null){
            mTask.cancel(true);
        }
    }
}
