package com.fy.catchdoll.presentation.presenter.account;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *<p>登录通知管理</p><br/>
 * @since 1.0.0
 * @author xky
 */
public class LoginNotifyManager {
	private Map<String,ILoginChangeListener> mCallBacks ;
	private static LoginNotifyManager INSTANCE; 
	private LoginNotifyManager() {
		mCallBacks = new HashMap<String, ILoginChangeListener>();
	}
	public static LoginNotifyManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new LoginNotifyManager();
		}
		return INSTANCE;
	}
	
	public void registLoginChangeCallBack(String key,ILoginChangeListener callBack){
		if(callBack != null){
			mCallBacks.put(key, callBack);
		}
	}
	public void unRegisteLoginChangeCallBack(String key){
		if(mCallBacks != null && mCallBacks.containsKey(key)){
			mCallBacks.remove(key);
		}
	}
	
	public void notifyLoginChange(){
		if(mCallBacks == null){
			return;
		}
		for(Map.Entry<String, ILoginChangeListener> entry : mCallBacks.entrySet()){
			ILoginChangeListener callBack = entry.getValue();
			if(callBack != null){
				callBack.onLoginChange();
			}
		}
	}
	
}
