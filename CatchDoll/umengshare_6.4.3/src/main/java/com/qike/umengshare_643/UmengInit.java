package com.qike.umengshare_643;

import com.umeng.socialize.PlatformConfig;

public class UmengInit {

	public static final String WEIXIN_APPID = "wx8ff481aad7c25e65";
//	public static final String WEIXIN_APPKEY = "c874039fe9cd2feff732abb5b6b3883a";
	public static final String WEIXIN_APPKEY = "c6d6fe311f79f9b60c00ca80251d211c";
	public static final String QQ_APPID = "1106587128";
	public static final String QQ_APPKEY = "5NOqpKNLd9vEgc46";
//	public static final String SINA_APPID = "4121226451";
//	public static final String SINA_APPKEY = "7d4978b61dae937838519e07f9910b5f";
	public static final String SINA_APPID = "3144866046";
	public static final String SINA_APPKEY = "25b401365a4ef8bae5b07cc829e5ef3c";
	public static void initUmengService(){
		//微信
		PlatformConfig.setWeixin(WEIXIN_APPID, WEIXIN_APPKEY);
		// QQ和Qzone appid appkey   
		PlatformConfig.setQQZone(QQ_APPID, QQ_APPKEY); 
//		//新浪微博 appkey appsecret
		PlatformConfig.setSinaWeibo(SINA_APPID,SINA_APPKEY,"http://sns.whalecloud.com/sina2/callback");

	}

	
	
	
}
