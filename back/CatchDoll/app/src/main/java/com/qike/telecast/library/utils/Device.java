package com.qike.telecast.library.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 获取手机硬件补助类
 * </p>
 * <br/>
 *
 * @author sunxh
 * @since 5.0.0
 */
public class Device {

    public static final Locale[] LANGUAGE_CATEGORY = {Locale.getDefault(),
            Locale.CHINESE, Locale.ENGLISH, Locale.KOREAN, Locale.TAIWAN,};

    private static final String FILE_MEMORY = "/proc/meminfo";
    private static final String FILE_CPU = "/proc/cpuinfo";
    public String mIMEI;
    public int mPhoneType;
    public int mSysVersion;
    public String mNetWorkCountryIso;
    public String mNetWorkOperator;
    public String mNetWorkOperatorName;
    public int mNetWorkType;
    public boolean mIsOnLine;
    public String mConnectTypeName;
    public long mFreeMem;
    public long mTotalMem;
    public String mCupInfo;
    public String mProductName;
    public String mModelName;
    public String mManufacturerName;

    // public static Device getInstance(Context context){
    // mContext = context;
    // return INSTANCE;
    // }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics();
        return dm;
    }

    /**
     * <p>
     * 获取CPU型号
     * </p>
     * <br/>
     *
     * @return
     * @author suenxianhao
     * @since 5.0.0
     */
    public static String getCpuType() {
        return EnvUtils.getCpuType();
    }

    public static boolean isCpuType(String proCpuType) {
        String cpuType = getCpuType();
        int len = proCpuType.length();
        if (cpuType.length() >= len) {
            // return cpuType.substring(0,len).equalsIgnoreCase(proCpuType);
            return cpuType.startsWith(proCpuType.toLowerCase());
        } else {
            return false;
        }
    }

    /**
     * <p>
     * 获取androidID
     * </p>
     * <br/>
     * <p>
     * 可能会和其他机子重复或本机改变。
     * </p>
     *
     * @param context
     * @author suenxianhao
     * @since 5.0.0
     */
    public static void getAndroidID(Context context) {
        String t = Settings.System.getString(
                context.getContentResolver(), "android_id");
    }

    // /**
    // * @param proCpuType
    // * @return
    // */
    // public static int getConfigParams(Context context,String proCpuType){
    // /* if(proCpuType.equals(CPU_PAR_NAME_TEGRA)){
    // return Integer.parseInt(MobclickAgent.getConfigParams (context,
    // CPU_PAR_NAME_TEGRA));
    // }
    // if(proCpuType.equals(CPU_PAR_NAME_MSM)){
    // return Integer.parseInt(MobclickAgent.getConfigParams (context,
    // CPU_PAR_NAME_MSM));
    // }*/
    // int i=-1;
    // try {
    // i = Integer.parseInt(MobclickAgent.getConfigParams (context,
    // proCpuType));
    // } catch (NumberFormatException e) {
    // e.printStackTrace();
    // }
    // if(i==-1){
    // getDefaultValue(proCpuType);
    // }
    // return i;
    // }
    // 在线参数名
    public static final String CPU_PAR_NAME_TEGRA = "properties_cpu_type_tegra";
    public static final String CPU_PAR_NAME_MSM = "properties_cpu_type_msm";

    public static int getDefaultValue(String proCpuType) {
        if (proCpuType.equals(CPU_PAR_NAME_TEGRA)) {
            return 10000;
        }
        if (proCpuType.equals(CPU_PAR_NAME_MSM)) {
            return 10001;
        }
        return -1;
    }

    /**
     * <p>
     * 获取IMEI识别码
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager manager = (TelephonyManager) context
                    .getSystemService(Activity.TELEPHONY_SERVICE);
            // check if has the permission
            if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
                    .checkPermission(Manifest.permission.READ_PHONE_STATE,
                            context.getPackageName())) {
                imei = manager.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PushLogUtils.e("IMEI", "IMEI: " + imei);
        return TextUtils.isEmpty(imei) ? "" : imei;
    }

    /**
     * <p>
     * 获取手机网络制式
     * </p>
     * <p>
     * 获取通讯类型，GSM,CDMA...
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static int getPhoneType(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getPhoneType();
    }

    /**
     * <p>
     * 获取手机系统版本
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static int getSysVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * <p>
     * 获取手机系统版本名称
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getSysVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * <p>
     * 获取移动通讯的国家注册码（移动区域码）
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getNetWorkCountryIso(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkCountryIso();
    }


    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();

    }

    /**
     * <p>
     * 用IMSI号码获取手机运营商
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String ProvidersName = null;
        String IMSI = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")
                || IMSI.startsWith("46007")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }

    /**
     * <p>
     * 获取网络运营商名称
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getNetWorkOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    /**
     * <p>
     * 获得当前网络的类型
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static int getNetworkType(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkType();
    }

    /**
     * <p>
     * 是否有可用数据链接
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }


    public static boolean isNetAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isAvailable());
    }

    /**
     * <p>
     * 获得当前数据连接类型的名称.比如：GPRS,WIFI
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getConnectTypeName(Context context) {
        if (!isOnline(context)) {
            return "OFFLINE";
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.getTypeName();
        } else {
            return "OFFLINE";
        }
    }

    /**
     * <p>
     * 获得当前数据连接类型的名称.比如：GPRS,WIFI
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getConnectTypeName2(Context context) {
        if (!isOnline(context)) {
            return "";
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            String typeName = info.getTypeName();
            return typeName != null ? typeName.toUpperCase() : "";
        } else {
            return "";
        }
    }

    public static boolean isWifyType(Context context) {
//		ConnectivityManager.TYPE_WIFI
        boolean iswify = false;
        if (!isOnline(context)) {
            iswify = false;
        } else {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null) {
                iswify = (info.getType() == ConnectivityManager.TYPE_WIFI);
            }
        }
        return iswify;
    }

    /**
     * <p>
     * 手机剩余内存
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        MemoryInfo info = new MemoryInfo();
        manager.getMemoryInfo(info);
        long free = info.availMem / 1024 / 1024;
        return free;
    }

    /**
     * <p>
     * 获取sd卡剩余内存
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static long getAvailaleSize() {

        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径

        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize;

        // (availableBlocks * blockSize)/1024 KIB 单位

        // (availableBlocks * blockSize)/1024 /1024 MIB单位

    }

    /**
     * <p>
     * 获取手机总内存
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static long getTotalMem(Context context) {
        try {
            FileReader fr = new FileReader(FILE_MEMORY);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");

            return Long.valueOf(array[1]) / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * <p>
     * 获取手机状态栏高度
     * </p>
     *
     * @return
     * @author sunxianhao
     * @since 5.0.0
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * <p>
     * 获取手机CPU型号
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getCpuInfo() {
        try {
            FileReader fr = new FileReader(FILE_CPU);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {

            }

            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 获取手机名称
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * <p>
     * 获取手机型号
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getModelName() {
        return Build.MODEL;
    }

    /**
     * <p>
     * 手机设备制造商名称
     * </p>
     *
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static String getManufacturerName() {
        return Build.MANUFACTURER;
    }

    /**
     * <p>
     * 获取项目版本号
     * </p>
     * <br/>
     *
     * @param ctx
     * @return
     * @author sunxianhao
     * @since 5.0.0
     */
    public static String getSoftVersion(Context ctx) {
        PackageInfo pinfo = null;
        try {
            PackageManager pm = ctx.getPackageManager();
            pinfo = pm.getPackageInfo(ctx.getPackageName(), 0);
            String versionCode = pinfo.versionName;
        } catch (NameNotFoundException e) {
        }
        if (!TextUtils.isEmpty(pinfo.versionName)) {
            return pinfo.versionName;
        } else {
            return null;
        }
    }

    /**
     * 获取项目版本号
     */
    public static int getVersionCode(Context ctx) {
        PackageInfo pinfo = null;
        int versionCode = 0;
        try {
            PackageManager pm = ctx.getPackageManager();
            pinfo = pm.getPackageInfo(ctx.getPackageName(), 0);
            versionCode = pinfo.versionCode;
        } catch (NameNotFoundException e) {
        }
        return versionCode;
    }

    /**
     * <p>
     * 获取屏幕密度
     * </p>
     * <br/>
     *
     * @param context
     * @return
     * @author suenxianhao
     * @since 5.0.0
     */
    public static int getDenisity(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 冻酸奶2.2-api8 以后版本
     *
     * @return
     * @author sunxianhao
     * @since 5.0.0
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 姜饼 2.3-api9 以后版本
     *
     * @return
     * @author sunxianhao
     * @since 5.0.0
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    // /**
    // * 蜂巢3.0-api11 以后版本
    // */
    // public static boolean hasHoneycomb() {
    // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    // }
    //
    // /**
    // * 蜂巢3.1-api12 以后版本
    // */
    // public static boolean hasHoneycombMR1() {
    // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    // }
    /**
     * 果冻豆4.1.2-api16 以后版本
     *
     * @since 5.0.0
     * @author sunxianhao
     * @return
     *
     *         public static boolean hasJellyBean() { return
     *         Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN; }
     */

    /**
     * <p>
     * 获取全部信息方法
     * </p>
     * <p>
     * 调用此方法随后调用toString获取信息
     * </p>
     *
     * @param context
     * @return
     * @author sunxh
     * @since 5.0.0
     */
    public static Device getPhoneInfo(Context context) {
        Device result = new Device();
        result.mIMEI = getIMEI(context);
        result.mPhoneType = getPhoneType(context);
        result.mSysVersion = getSysVersion();
        result.mNetWorkCountryIso = getNetWorkCountryIso(context);
        // result.mNetWorkOperator = getNetWorkOperator(context);
        result.mNetWorkOperatorName = getNetWorkOperatorName(context);
        result.mNetWorkType = getNetworkType(context);
        result.mIsOnLine = isOnline(context);
        result.mConnectTypeName = getConnectTypeName(context);
        result.mFreeMem = getFreeMem(context);
        result.mTotalMem = getTotalMem(context);
        result.mCupInfo = getCpuInfo();
        result.mProductName = getProductName();
        result.mModelName = getModelName();
        result.mManufacturerName = getManufacturerName();
        return result;
    }

    /**
     * <p>
     * 判断是否为飞行模式
     * </p>
     *
     * @param context
     * @return true为飞行模式
     * @author sunxh
     * @since 2.6
     */
    public static boolean IsAirModeOn(Context context) {
        return (Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
    }

    /**
     * <p>
     * 判断SIM卡是否可用
     * </p>
     *
     * @param context
     * @return true为SIM卡可用
     * @author sunxh
     * @since 2.6
     */
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将所有信息转为string类型
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IMEI : " + mIMEI + "\n");
        builder.append("mPhoneType : " + mPhoneType + "\n");
        builder.append("mSysVersion : " + mSysVersion + "\n");
        builder.append("mNetWorkCountryIso : " + mNetWorkCountryIso + "\n");
        // builder.append("mNetWorkOperator : "+mNetWorkOperator+"\n");
        builder.append("mNetWorkOperatorName : " + mNetWorkOperatorName + "\n");
        builder.append("mNetWorkType : " + mNetWorkType + "\n");
        builder.append("mIsOnLine : " + mIsOnLine + "\n");
        builder.append("mConnectTypeName : " + mConnectTypeName + "\n");
        builder.append("mFreeMem : " + mFreeMem + "M\n");
        builder.append("mTotalMem : " + mTotalMem + "M\n");
        builder.append("mCupInfo : " + mCupInfo + "\n");
        builder.append("mProductName : " + mProductName + "\n");
        builder.append("mModelName : " + mModelName + "\n");
        builder.append("mManufacturerName : " + mManufacturerName + "\n");
        return builder.toString();
    }

    /**
     * <p>
     * 获取屏幕的宽和高
     * </p>
     * <br/>
     *
     * @param activity
     * @return int[] int[0]=widht;int[1]=height
     * @author xky
     * @since 5.0.0
     */
    public static int[] getScreenWidthAndHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int[] wh = {display.getWidth(), display.getHeight()};
        return wh;
    }

    /**
     * 获取屏幕density属性的等级 包括:low, mid, high, x
     */
    public static float getDensity(Context context) {
        WindowManager mWindowManager = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ;
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.density;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * <p>
     * 获取系统内所有的应用 排除系统应用
     * </p>
     * <br/>
     *
     * @param context
     * @return
     * @throws Throwable
     * @author xky
     * @since 5.0.0
     */
    public static List<PackageInfo> getLocalAppInfo(Context context)
            throws Throwable {

        PackageManager pm = context.getPackageManager();
        List<PackageInfo> rawList = pm.getInstalledPackages(0);
        List<PackageInfo> list = new ArrayList<PackageInfo>();

        for (PackageInfo info : rawList) {
            ApplicationInfo appInfo = pm
                    .getApplicationInfo(info.packageName, 0);
            if (!((appInfo.flags & appInfo.FLAG_SYSTEM) > 0)) {
                list.add(info);
            }
        }
        return list;
    }

    /**
     * <p>
     * TODO 弹起软键盘
     * </p>
     * <br/>
     * <p>
     * TODO(详细描述)
     * </p>
     *
     * @param context
     * @author ll
     * @since 5.0.0
     */
    public static void showSoftInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }

    }

    /**
     * <p>
     * TODO 另一种方法弹起软键盘
     * </p>
     * <br/>
     *
     * @param activity
     * @author ll
     * @since 5.0.0
     */
    public static void showSoftInputMethod(Activity activity) {
        if (activity != null) {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    /**
     * <p>
     * TODO 收起软键盘
     * </p>
     * <br/>
     * <p>
     * TODO(详细描述)
     * </p>
     *
     * @param context
     * @author ll
     * @since 5.0.0
     */
    public static void collapseSoftInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            // imm.hideSoftInputFromWindow(inputText.getWindowToken(),
            // InputMethodManager.HIDE_IMPLICIT_ONLY);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * <p>
     * TODO 另一种方法收起软键盘
     * </p>
     * <br/>
     *
     * @param activity
     * @author ll
     * @since 5.0.0
     */
    public static void collapseSoftInputMethod(Activity activity) {
        if (activity != null) {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    /**
     * <p>
     * 点击下一步隐藏软键盘
     * </p>
     * <br/>
     *
     * @param et
     * @author xky
     * @since 1.0.0
     */
    public static void clickNexthidenSoftInputMethod(final EditText et,
                                                     final Activity activity) {
        et.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NEXT:
                        collapseSoftInputMethod(activity, et);
                        // InputMethodManager imm
                        // =(InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        // imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 重启系统
     */
    public static void rebootSystem() {
        PrintStream ps = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            ps = new PrintStream(process.getOutputStream());

            ps.println("reboot");
            ps.println("exit");
            ps.println("exit");
            ps.close();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            try {
                int exit = process.exitValue();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * 读取电话号码
     * </p>
     * <br/>
     * 有些手机是读取不到的
     *
     * @param context
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 判断手机格式是否正确
     *
     * @param str
     *            需要判断的字符串
     * @return 返回true说明字符串匹配成功
     */
    // Pattern类的作用在于编译正则表达式后创建一个匹配模式. Matcher类使用Pattern实例提供的模式信息对正则表达式进行匹配
    public static boolean isPhone(String str) {
        // 将给定的正则表达式编译并赋予给Pattern类
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        // 对指定输入的字符串创建一个Matcher对象
        Matcher matcher = pattern.matcher(str);
        // 尝试对整个目标字符展开匹配检测,也就是只有整个目标字符串完全匹配时才返回真值.
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * 获取appkey
     * </p>
     * <br/>
     *
     * @param context
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static String getMetaData(Context context) {
        String appkeyValue = "";
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            appkeyValue = info.metaData.getString("appkey");
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return appkeyValue;
    }


    /**
     * <p>
     * 获取手机端ip地址
     * </p>
     * <br/>
     * WIF和3G都可以吧
     *
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }

        return null;
    }

    /**
     * <p>
     * 获取手机端ua信息
     * </p>
     * <br/>
     *
     * @param context
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static String getUAInfo(Context context) {
        WebView webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String ua = settings.getUserAgentString();
        webview = null;
        return ua;
    }

    /**
     * 是否可以卢平
     *
     * @return
     */
    public static boolean checkCanRecored() {
        return Device.getSysVersion() >= Build.VERSION_CODES.KITKAT;
    }

    public static void startMIUIEditActivity(Context context) {
        try {
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }


    private static void startMiuiEditorActivity(Context context) {
        if (isMIUI()) {
            jumpToPermissionsEditorActivity(context);
        } else {
            startMIUIEditActivity(context);
        }
    }

    /**
     * 跳转到MIUI应用权限设置页面
     *
     * @param context context
     */
    public static void jumpToPermissionsEditorActivity(Context context) {
        if (isMIUI()) {
            try {
                // MIUI 8
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e) {
                try {
                    // MIUI 5/6/7
                    Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    localIntent.putExtra("extra_pkgname", context.getPackageName());
                    context.startActivity(localIntent);
                } catch (Exception e1) {
                    // 否则跳转到应用详情
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            }
        }
    }


    /**
     * 判断是否是MIUI
     */
    private static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
                return prop.getProperty("ro.miui.ui.version.code", null) != null
                        || prop.getProperty("ro.miui.ui.version.name", null) != null
                        || prop.getProperty("ro.miui.internal.storage", null) != null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * cup  型号是否 arm - v7a
     */
    public static boolean hasCompatibleCPU() {
        // If already checked return cached result

        String CPU_ABI = Build.CPU_ABI;
        String CPU_ABI2 = "none";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) { // CPU_ABI2
            // since
            // 2.2
            try {
                CPU_ABI2 = (String) Build.class.getDeclaredField(
                        "CPU_ABI2").get(null);
            } catch (Exception e) {
                return false;
            }
        }

        if (CPU_ABI.equals("armeabi-v7a") || CPU_ABI2.equals("armeabi-v7a")) {
            return true;
        }
        try {
            FileReader fileReader = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ARMv7")) {
                    return true;
                }
            }
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * set粘贴板文案
     *
     * @param mContext
     * @param content
     */
    public static void setCopy(Context mContext, String content) {
        ClipboardManager clip = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(content);
    }

    /**
     * 判断手机有没有安装该应用
     *
     * @param mContext
     * @param pkgName
     * @return
     */
    public static boolean isPkgInstalled(Context mContext, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}