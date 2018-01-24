package com.qike.telecast.presentation.presenter;


public interface BaseCallbackPresenter {

    boolean callbackResult(Object obj);

    void onErrer(int code, String msg);

}
