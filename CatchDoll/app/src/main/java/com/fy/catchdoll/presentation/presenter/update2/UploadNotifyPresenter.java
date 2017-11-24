package com.fy.catchdoll.presentation.presenter.update2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.presentation.presenter.update2.Inter.OnUploadListener;


/**
 * Created by xky on 16/6/22.
 * 通知栏
 */
public class UploadNotifyPresenter implements OnUploadListener {
    private Notification mNotification;
    private NotificationManager mNm;
    private RemoteViews mRemoteViews;
    private Context mContext;
    private static final int NOTIFY_ID = 2080;
    public static final String RECEIVER_ACTION_DELETE = "com.qike.telecast.appupdate.action.receiver.delete";
    public static final String RECEIVER_ACTION_RETRY = "com.qike.telecast.appupdate.action.receiver.retry";

    private static final String NOTIFY_UPLOAD_KEY = "notify_upload_key";
    public UploadNotifyPresenter(Context context){
        mContext = context;
        initDefaultNotification();
    }

    public void setNotifyListener() {
        UploadManager.getInstance().registOnUploadListener(NOTIFY_UPLOAD_KEY, this);
    }
    public void unSetNotifyListener(){
        UploadManager.getInstance().unRegistOnUploadListener(NOTIFY_UPLOAD_KEY);
    }

    private void initDefaultNotification() {
        mNm = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.app_update_custom_notification);
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < android.os.Build.VERSION_CODES.HONEYCOMB) {
            mNotification = new Notification(android.R.drawable.stat_sys_download,mContext.getResources().getString(R.string.app_update_ticker), 0);
            mNotification.contentView = mRemoteViews;
            // 3.0以下的不支持自定义通知栏中控件的点击事件，所以把删除按钮隐藏掉
            mRemoteViews.setViewVisibility(R.id.app_update_del, View.GONE);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            mNotification = builder.setSmallIcon(android.R.drawable.stat_sys_download)
                    .setTicker(mContext.getResources().getString(R.string.app_update_ticker)).setContent(mRemoteViews)
                    .build();
        }
        mNotification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
        mRemoteViews.setImageViewResource(R.id.app_update_logo, R.mipmap.ic_launcher);
        mRemoteViews.setTextViewText(R.id.app_update_title, mContext.getResources().getString(R.string.app_name));
        mRemoteViews.setTextViewText(R.id.app_update_progress_msg, "");
        mRemoteViews.setTextViewText(R.id.app_update_tips, mContext.getResources().getString(R.string.app_update_downloading));
        mRemoteViews.setProgressBar(R.id.app_update_progress, 100, 0, false);
        mRemoteViews.setOnClickPendingIntent(R.id.app_update_del, createPendingIntent(mContext, RECEIVER_ACTION_DELETE));
    }


    public void showNotify(){
        if (mNm != null){
            mNm.notify(NOTIFY_ID, mNotification);
        }
    }

    public void cancelNotify(){
        if (mNm != null){
            mNm.cancel(NOTIFY_ID);
        }
    }



    private PendingIntent createPendingIntent(Context ctx, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        PendingIntent pIntent = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }

    @Override
    public void onStartUpload() {
        mRemoteViews.setTextViewText(R.id.app_update_tips, mContext.getResources().getString(R.string.app_update_downloading));
        mNotification.icon = android.R.drawable.stat_sys_download;
        mNotification.tickerText = mContext.getResources().getString(R.string.app_update_ticker);
        showNotify();
    }

    @Override
    public void onUploadProgress(long total, long size, int percent) {
        mRemoteViews.setProgressBar(R.id.app_update_progress, 100, percent, false);
        String Strtotal = String.format("%.2fM", (float) total / 1024 / 1024);
        String Strcurrent = String.format("%.2fM/", (float) size / 1024 / 1024);
        String StrprogressMsg = percent + "%" + "(" + Strcurrent + Strtotal + ")";
        mRemoteViews.setTextViewText(R.id.app_update_progress_msg, StrprogressMsg);
        showNotify();
    }

    @Override
    public void onUploadFinish(String path) {
        mNotification.icon = android.R.drawable.stat_sys_download_done;
        mNotification.tickerText = "下载完成";
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//        mRemoteViews.setTextViewText(R.id.app_update_tips, mContext.getResources().getString(R.string.app_update_downloaded));
        cancelNotify();
        DeviceUtils.installerAPK(path, mContext);
    }

    @Override
    public void onUploadError(String msg) {
        mNotification.contentIntent = createPendingIntent(mContext, RECEIVER_ACTION_RETRY);
        mRemoteViews.setTextViewText(R.id.app_update_tips, mContext.getResources().getString(R.string.app_update_download_error));
        mNotification.icon = android.R.drawable.stat_sys_download_done;
        mNotification.tickerText = "下载失败了";
        showNotify();
    }

    @Override
    public void onUploadCancel() {
        cancelNotify();
    }


}
