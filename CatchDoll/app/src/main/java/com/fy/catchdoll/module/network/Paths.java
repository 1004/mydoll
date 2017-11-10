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
    public static String BETA_PATH = "http://beta.feiyun.tv/";
    public static String DEV_PATH = "http://dev.feiyun.tv/";
    public static String TEST_PATH = BETA_PATH;
    public static String TEST_SOCKET_URL = "http://devdanmu.feiyun.tv/";
    //	public static  String  BASEPATH = "http://api.feiyun.tv/";
    public static String BASEPATH = "http://api.youyouwin.cn/";
    public static String NEWBASEPATH = "http://api.feiyun.tv/";
    //	public static  String SOCKET_URL = "http://danmu.feiyun.tv";
    public static String SOCKET_URL = "http://danmu.feiyun.tv/";


    public static final String HOME_NAV = "v1/nav";
    public static final String HOME_ARTICLE = "v1/article";
}
