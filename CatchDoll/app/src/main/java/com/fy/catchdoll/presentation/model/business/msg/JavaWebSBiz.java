package com.fy.catchdoll.presentation.model.business.msg;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fy.catchdoll.module.support.java_websocket.client.WebSocketClient;
import com.fy.catchdoll.module.support.java_websocket.drafts.Draft;
import com.fy.catchdoll.module.support.java_websocket.handshake.ServerHandshake;
import com.fy.catchdoll.presentation.model.business.msg.inter.IMessageBiz;
import com.fy.catchdoll.presentation.model.business.msg.inter.OnReLinkSocketListener;
import com.fy.catchdoll.presentation.model.dto.msg.MessDto;
import com.fy.catchdoll.presentation.model.dto.msg.SentMessDto;
import com.fy.catchdoll.presentation.presenter.BaseCallbackPresenter;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;

import java.net.URI;
import java.util.Map;

/**
 * Created by xky on 16/7/18.
 */
public class JavaWebSBiz implements IMessageBiz {
    private Context mContext;
    private boolean isCloseNormal = false;
    OnReLinkSocketListener mLinkSocketListener;
    private BaseCallbackPresenter mCallback;
    private String mSocketUrl ;

    private WebSocketClient mWebClent;
    private Handler mHandler = new Handler();
    public JavaWebSBiz(Context context) {
        this.mContext = context;
    }

    @Override
    public void sentMeg(SentMessDto msg) {
        if (AccountManager.getInstance().getUser() == null || msg == null) {
            return;
        }
        String message = JSON.toJSONString(msg);
        if (mWebClent != null && mWebClent.isOpen() && message != null) {
            mWebClent.send(message);
        } else {
            if (mLinkSocketListener != null) {
                mLinkSocketListener.onReLink();
            }
        }
    }

    @Override
    public void initWebSocket(String socketUrl) {
        isCloseNormal = false;
        mSocketUrl = socketUrl;
        if (!TextUtils.isEmpty(socketUrl)) {
            try {
                mWebClent = new MyWebSocketClient( new URI(socketUrl));
                mWebClent.connect();
                Log.i("test", "-------javaWebsocket---弹幕---------------开始连接");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MyWebSocketClient extends WebSocketClient{

        public MyWebSocketClient(URI serverURI) {
            super(serverURI);
        }

        public MyWebSocketClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        public MyWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
            super(serverUri, protocolDraft, httpHeaders, connectTimeout);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.i("test", "-------javaWebsocket------弹幕-------------链接成功");
        }

        @Override
        public void onMessage(String message) {
            if (!TextUtils.isEmpty(message)) {
                try {
                    final MessDto dto = JSON.parseObject(message, MessDto.class);
                    if (mCallback != null) {
                        mCallback.callbackResult(dto);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            onClose_(code,reason);
        }

        @Override
        public void onError(Exception ex) {
            onClose_(-1, "");
        }
    }

    private void onClose_(final int code, final String reason){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isCloseNormal) {
                    if (mLinkSocketListener != null) {
                        mLinkSocketListener.onReLink();
                    }
                }
                Log.i("test", "-------javaWebsocket-----弹幕--------------链接失败");
                if (mCallback != null) {
                    mCallback.onErrer(code, reason);
                }
            }
        });
    }
    @Override
    public void changeInit(String ws_url) {
        destroySocket();
        initWebSocket(ws_url);
    }
    @Override
    public void destroySocket() {
        isCloseNormal = true;
        if (mWebClent != null && mWebClent.isOpen()) {
            Log.i("test", "-------javaWebsocket------弹幕-------------开始关闭");

            mWebClent.close();
            mWebClent = null;
        }
    }

    @Override
    public void setOnReLinkSocketListener(OnReLinkSocketListener linkSocketListener) {
        mLinkSocketListener = linkSocketListener;
    }
    @Override
    public void setOnBizCalLBack(BaseCallbackPresenter calLBack) {
        mCallback = calLBack;
    }
}
