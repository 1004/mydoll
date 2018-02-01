package com.fy.catchdoll.presentation.presenter.start;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.Device;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.presentation.model.dto.room.RoomInfo;

/**
 * Created by xky on 2018/1/30 0030.
 */
public class StartPresenter {
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private Context mContext;
    private OnRequestPermissonCallBack mCallBack;
    private String[] mPermissions;
    private DialogManager mDialogManager;

    public StartPresenter(Context context){
        this.mContext = context;
        mDialogManager = new DialogManager(context);
    }

    /**
     * 初始化权限
     */
    public void initPermission(String[] permissions){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            mPermissions = permissions;
            if (mPermissions == null || mPermissions.length == 0){
                //不需要授权
                onSure_();
            }else {
                operatepermission();
            }
        }else {
            onSure_();
        }
    }

    private void operatepermission() {
        boolean isAllGranted = checkPermissionAllGranted(mPermissions);
        if (isAllGranted){
            onSure_();
        }else {
            ActivityCompat.requestPermissions((Activity) mContext, mPermissions, MY_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                onSure_();
            } else {
                mDialogManager.showDialog(DialogStyle.COMMON, mDialogListener
                        , mContext.getResources().getString(R.string.string_no_permissio_info)
                        , mContext.getResources().getString(R.string.cancel)
                        , mContext.getResources().getString(R.string.sure));
            }
        }
    }

    private DialogManager.OnClickListenerContent mDialogListener = new DialogManager.OnClickListenerContent() {
        @Override
        public void onClick(View view, Object... content) {
            switch (view.getId()){
                case R.id.tv_yes_common:
                    mDialogManager.dismissDialog();
                    Device.startMiuiEditorActivity(mContext);
                    break;
                case R.id.tv_cancel_common:
                    mDialogManager.dismissDialog();
                    onNo_();
                    break;
            }
        }
    };

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    public void setRequestPermissionCalLBack(OnRequestPermissonCallBack calLBack){
        this.mCallBack = calLBack;
    }

    private void onNo_(){
        if (mCallBack != null){
            mCallBack.onNo();
        }
    }

    private void onSure_(){
        if (mCallBack != null){
            mCallBack.onSure();
        }
    }


    public interface OnRequestPermissonCallBack{
        void onNo();
        void onSure();
    }
}
