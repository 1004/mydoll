package com.qike.umengshare_643.bean;

public class UmengUserInfo {

	private String userName;
	
	private String userImageUrl;
	
	private String openId;
	
	private String token;
	
	private String source;

	/**
	 * 性别，1：男  2：女
	 */
	private int sex;

	private String mPlat;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getmPlat() {
		return mPlat;
	}

	public void setmPlat(String mPlat) {
		this.mPlat = mPlat;
	}
}
