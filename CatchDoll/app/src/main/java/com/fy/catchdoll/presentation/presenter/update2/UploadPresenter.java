package com.fy.catchdoll.presentation.presenter.update2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.module.service.NetService;
import com.fy.catchdoll.presentation.model.business.update.UploadBiz;
import com.fy.catchdoll.presentation.model.dto.updata.UpdateDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;
import com.fy.catchdoll.presentation.presenter.update2.Inter.IOnCheckVersonCallBack;
import com.fy.catchdoll.presentation.presenter.update2.Inter.OnUploadListener;


/**
 * Created by xky on 16/6/22.
 */
public class UploadPresenter implements BaseCallbackPresenter, DialogManager.OnClickListenerContent, OnUploadListener {
    private UploadBiz mBiz;
    private IOnCheckVersonCallBack mCallBack;
    private Context mContext;
    private AppUpdateReceiver mReceiver;
    private UploadNotifyPresenter mNofityViewPresenter;
    private DialogManager mDialogManager;
    private UpdateDto mDto;
    private boolean isError = false;
    private String PRESENTER_UPLOAD_KEY = "presenter_upload_key";

    public UploadPresenter(Context context, IOnCheckVersonCallBack callBack) {
        mCallBack = callBack;
        mContext = context;
        mBiz = new UploadBiz(context);
        mBiz.setOnBizCalLBack(this);
        registReceiver();
        initElement();

        NetService.registNetObserver(new NetService.INetObserver() {
            @Override
            public void onUpdate(boolean isHasNet) {
                if (isError && isHasNet && mDto != null) {
                    startUpload(mDto.getDownlink());
                }
            }
        });
        UploadManager.getInstance().registOnUploadListener(PRESENTER_UPLOAD_KEY, this);
    }

    private void initElement() {
        mNofityViewPresenter = new UploadNotifyPresenter(mContext);
        mDialogManager = new DialogManager(mContext);
    }

    private void startUpload(String path) {
        Intent intent = new Intent(mContext, UploadService.class);
        intent.putExtra(UploadService.APK_URL_KEY, path);
        mContext.startService(intent);
    }

    private void stopUpload() {
        try{
            mContext.stopService(new Intent(mContext, UploadService.class));
        }catch (Exception e){}
    }

    private void registReceiver() {
        mReceiver = new AppUpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UploadNotifyPresenter.RECEIVER_ACTION_DELETE);
        filter.addAction(UploadNotifyPresenter.RECEIVER_ACTION_RETRY);
        mContext.registerReceiver(mReceiver, filter);
    }

    public void destoryData() {
        try {
            mContext.unregisterReceiver(mReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
        UploadManager.getInstance().unRegistOnUploadListener(PRESENTER_UPLOAD_KEY);
        stopUpload();
    }

    /**
     * 检测自动更新
     */
    public void checkUpload() {
        if (mCallBack != null) {
            mCallBack.onCheckStart();
        }
        mBiz.checkUpload();
    }

    public void setOnCheckCallback(IOnCheckVersonCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public boolean callbackResult(Object obj) {
        if (obj != null && obj instanceof UpdateDto) {
            mDto = (UpdateDto) obj;
            operateUpload(mDto);
        }
        return false;
    }

    private void operateUpload(UpdateDto dto) {
        int version_code = dto.getVersion_code();
        try {
            int app_version_code = DeviceUtils.getVersionCode(mContext);
            if (mCallBack != null) {
                mCallBack.onCheckFinish(version_code > app_version_code, dto.getIs_force() == UpdateDto.FORCE_UPDATA);
            }
            if (version_code > app_version_code) {
                // 更新去了
                goUpload(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goUpload(UpdateDto dto) {
        int force = dto.getIs_force();
        if (force == UpdateDto.FORCE_UPDATA) {
            // 强制更新
            mDialogManager.showDialog(DialogStyle.UPLOAD, this, true, dto.getUpdate_notes(), dto.getDownlink());
            mNofityViewPresenter.setNotifyListener();
            startUpload(dto.getDownlink());
        } else {
            // 非强制更新
            mDialogManager.showDialog(DialogStyle.UPLOAD, this, false, dto.getUpdate_notes(), dto.getDownlink());
            mNofityViewPresenter.setNotifyListener();
        }
    }

    @Override
    public void onErrer(int code, String msg) {
        if (mCallBack != null) {
            mCallBack.onCheckError(msg, code == 10054);
        }
    }

    @Override
    public void onClick(View view, Object... content) {
        switch (view.getId()) {
        case R.id.tv_update:
            if (content != null) {
                String path = (String) content[0];
                boolean isForce = (Boolean) content[1];
                if (isForce) {
                    startUpload(path);
                } else {
                    startUpload(path);
                    mDialogManager.dismissDialog();
                }
            }
            break;
        case R.id.iv_cancel:
            mDialogManager.dismissDialog();
            break;
        case R.id.tv_progress_load:
            if (content != null) {
                String path = (String) content[0];
                startUpload(path);
            }
            break;
        }
    }

    @Override
    public void onStartUpload() {
        isError = false;
    }

    @Override
    public void onUploadProgress(long total, long size, int percent) {

    }

    @Override
    public void onUploadFinish(String path) {

    }

    @Override
    public void onUploadError(String msg) {
        isError = true;
    }

    @Override
    public void onUploadCancel() {

    }

    private class AppUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (action.equals(UploadNotifyPresenter.RECEIVER_ACTION_DELETE)) { // 删除下载
                    stopUpload();
                    mNofityViewPresenter.cancelNotify();
                } else if (action.equals(UploadNotifyPresenter.RECEIVER_ACTION_RETRY)) { // 重试下载
                    if (mDto != null) {
                        startUpload(mDto.getDownlink());
                    }
                }
            }
        }

    }
}
