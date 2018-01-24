package com.qike.telecast.presentation.presenter.update2.Inter;

/**
 * Created by xky on 16/6/22.
 */
public interface IOnCheckVersonCallBack {
    /**
     * 开始检测
     */
    public void onCheckStart();

    /**
     * 检测完成
     * @param isNew ：服務器端是否最新  true :是  false:不是
     */
    public void onCheckFinish(boolean isNew, boolean isForce);

    /**
     * 检测失败
     */
    public void onCheckError(String msg, boolean isStartBaiDu);
}
