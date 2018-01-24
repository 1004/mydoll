package com.fy.catchdoll.presentation.presenter.msg;




import com.fy.catchdoll.presentation.model.dto.msg.MessDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *<p>消息到来通知管理</p><br/>
 * @since 1.0.0
 * @author xky
 */
public class MessageNotifyManager {
	private Map<String,IOnMessageComeCallBack> mCallBacks ;
	private static MessageNotifyManager INSTANCE; 
	private MessageNotifyManager() {
		mCallBacks = new HashMap<String, IOnMessageComeCallBack>();
	}
	public static MessageNotifyManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new MessageNotifyManager();
		}
		return INSTANCE;
	}
	
	public void registGiftCallBack(String key,IOnMessageComeCallBack callBack){
		if(callBack != null){
			mCallBacks.put(key, callBack);
		}
	}
	public void registAnimGiftCallBack(String key,IOnMessageComeContainesAnimCallBack callBack){
		if(callBack != null){
			mCallBacks.put(key, callBack);
		}
	}
	public void unRegisteGiftCallBack(String key){
		if(mCallBacks != null && mCallBacks.containsKey(key)){
			mCallBacks.remove(key);
		}
	}
	
	public void notfifyGiftCame(MessDto msg){
		if(mCallBacks == null){
			return;
		}
		for(Map.Entry<String, IOnMessageComeCallBack> entry : mCallBacks.entrySet()){
			IOnMessageComeCallBack callBack = entry.getValue();
			if(callBack != null){
				callBack.onMessageCome(msg);
			}
		}
	}

	public void notfifyAnimGiftCame(MessDto msg){
		if(mCallBacks == null){
			return;
		}
		for(Map.Entry<String, IOnMessageComeCallBack> entry : mCallBacks.entrySet()){
			IOnMessageComeCallBack callBack = entry.getValue();
			if(callBack != null && callBack instanceof IOnMessageComeContainesAnimCallBack){
				((IOnMessageComeContainesAnimCallBack)callBack).onAnimMessageCome(msg);
			}
		}
	}

	public void notifyGiftCames(List<MessDto> megs){
		if(mCallBacks == null){
			return;
		}
		for(Map.Entry<String, IOnMessageComeCallBack> entry : mCallBacks.entrySet()){
			IOnMessageComeCallBack callBack = entry.getValue();
			if(callBack != null){
				callBack.onMessageChanges(megs);
			}
		}
	}
	
	
	public interface IOnMessageComeCallBack{
		public void onMessageCome(MessDto msg);
		public void onMessageChanges(List<MessDto> msgs);
	}

	public interface IOnMessageComeContainesAnimCallBack extends IOnMessageComeCallBack{
		public void onAnimMessageCome(MessDto msg);
	}
	
}
