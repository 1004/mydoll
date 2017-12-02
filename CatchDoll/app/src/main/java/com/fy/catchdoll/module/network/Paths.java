package com.fy.catchdoll.module.network;

/**
 * <p>TODO(类的概括性描述)</p><br/>
 * <p>TODO (类的详细的功能描述)</p>
 *
 * @author suenxianhao
 * @since 5.0.0
 */
public class Paths {

    public static boolean ISTEST = true;
    public static boolean IS_DANMU_TEST = false;//如果是beta版本或者正式版本要是false
    public static String NEWAPI = "http://api.badsou.com/";



    public static final String UPLOAD_VERSION = "/basics/initialization.json";
    public static final String HOME_ARTICLE = "v1/article";

    public static final String IDNEX_DATA = "index/list.json";//首页数据
    public static final String MY_INFO_DATA = "user/info.json";//个人信息
    public static final String BOX_INFO_DATA = "backpack/mybackpack.json";//背包信息
    public static final String MY_SPEND_DATA = "gold/changelist.json";//消费记录
    public static final String MY_SEND_CODE = "user/setinvicode.json";//消费记录
    public static final String WX_LOGIN_DATA = "login/wechat.json";//微信登陆
    public static final String RECHARGE_LIST_ = "gold/packagelist.json";//娃娃币充值套餐列表
    public static final String UPDATE_ADDRESS_DATA = "user/setaddress.json";//添加/修改用户收件地址


}
