package com.fy.catchdoll.presentation.model.business.msg.inter;


import com.fy.catchdoll.presentation.model.dto.msg.SentMessDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;

/**
 * Created by xky on 16/7/18.
 */
public interface IMessageBiz {
    public void sentMeg(SentMessDto msg);
    public void initWebSocket(String socketUrl);
    public void changeInit(String ws_url);
    public void destroySocket();
    public void setOnReLinkSocketListener(OnReLinkSocketListener linkSocketListener);
    public void setOnBizCalLBack(BaseCallbackPresenter calLBack);
}
