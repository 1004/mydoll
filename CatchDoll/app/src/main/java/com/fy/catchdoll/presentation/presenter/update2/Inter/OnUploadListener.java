package com.fy.catchdoll.presentation.presenter.update2.Inter;

/**
 * Created by xky on 16/6/22.
 */
public interface OnUploadListener {
    public void onStartUpload();
    public void onUploadProgress(long total, long size, int percent);
    public void onUploadFinish(String path);
    public void onUploadError(String msg);
    public void onUploadCancel();
}
