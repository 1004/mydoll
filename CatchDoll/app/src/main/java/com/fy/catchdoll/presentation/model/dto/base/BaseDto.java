package com.fy.catchdoll.presentation.model.dto.base;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;


/**
 *<p>Dto基类</p><br/>
 * @since 5.0.0
 * @author suenxianhao
 */
public class BaseDto implements Serializable {
	private static final long serialVersionUID = 1213087319218529863L;


	/**
	 * 解释JSON主体数据,子类自行实现
	 * */
	public Object analyseBody(JSONObject result) throws Exception {
		
		return result;
	}
	
	public void printInfo(){
		Gson gson = new Gson();
//		Log.d(getClass().getSimpleName(), getClass().getSimpleName() + "=" + gson.toJson(this));
	}
}
