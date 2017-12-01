package com.fy.catchdoll.module.network;


import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.presentation.application.CdApplication;
import com.fy.catchdoll.presentation.model.dto.account.User;
import com.fy.catchdoll.presentation.presenter.account.AccountManager;

import java.net.URI;
import java.util.Map;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.SecurityUtils;


public class CommonalityParams {

	public static final String USER_UNIQUE = "dec4b3722f";
	public static final String SECRET_KEY = "59ca285101ede1db9140d72b2c8914af"; 
	public static final String QIKE_VERSION_KEY = "qikeversion";
	public static final String SOURCE_KEY = "source";
	public static final String CPU_KEY = "cpu";
	public static final String BRAND_KEY = "brand";
	public static final String MODEL_KEY = "model";
	public static final String SYSVERSION_KEY = "sysversion";
	public static final String LOCALE_KEY = "locale";
	public static final String DENSITY_KEY = "density";
	public static final String USERID_KEY = "uid";
	public static final String ACCESSTOKEN_KEY = "accesstoken";
	public static final String DEVICE_ID="deviceid";
	private String mUrl;

	public Map<String, Object> initGeneralParams(String url, Map<String, Object> mParams) {
		try {
			mUrl = url;
			try {
				User user = AccountManager.getInstance().getUser();
				if (user != null){
					mParams.put("user_id",user.getId());
					mParams.put("access_token",user.getAccess_token());
				}
				mParams.put("signtype", "1");
				mParams.put("version", DeviceUtils.getVersionCode(CdApplication.getApplication()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return mParams;
	}

	public String convertParams(Map<String, Object> mParams) {
		try {
			StringBuilder sb = new StringBuilder();

			if (mParams != null && mParams.size() > 0) {
				for (Map.Entry<String, Object> entry : mParams.entrySet()) {
					sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String initGeneralKey(String url, Map<String, Object> mParams) {
		String paramString = convertParams(mParams);
		String sign = null;
		try {
			URI uri = new URI(mUrl);
			String path = uri.getHost() + uri.getPath();
			sign = SecurityUtils.getMd5(SecurityUtils.getMd5(path, "UTF-8") + paramString, "UTF-8");

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return sign;
	}

}