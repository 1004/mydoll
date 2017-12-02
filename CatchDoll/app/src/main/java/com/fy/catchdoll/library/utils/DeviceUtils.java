package com.fy.catchdoll.library.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.provider.Settings;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

public class DeviceUtils {

    public static void startAppSettignActivity(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//		context.startActivity(intentInfo);
    }
    public static void startAppSpecialSettignActivity(Context context) {
//        if (OSJudgementUtil.isMIUI8() && Device.getSysVersion() == Build.VERSION_CODES.M){
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + context.getPackageName()));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }else {
//            Uri packageURI = Uri.parse("package:" + context.getPackageName());
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
    }

    public static void startGpsSettignActivity(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            intent.setAction(Settings.ACTION_SETTINGS);
            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.
            // General settings activity
            try {
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, "启动失败,请手动设置", Toast.LENGTH_SHORT).show();
            }
        }
//		context.startActivity(intentInfo);
    }

    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        return dm.widthPixels;

    }

    /**
     * <p>
     * 获取屏幕的宽和高
     * </p>
     * <br/>
     *
     * @param context
     * @return int[] int[0]=widht;int[1]=height
     * @author xky
     * @since 5.0.0
     */
    public static int[] getScreenWidthAndHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = context.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int[] wh = {dm.widthPixels, dm.heightPixels};
        return wh;
    }

    /**
     * <p>获取版本号</p><br/>
     *
     * @param context
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static String getVersionName(Context context) throws NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionName;
    }

    public static int getVersionCode(Context context) throws NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }

    /**
     * 自动安装apk
     *
     * @param apkPath apk文件的路径
     */
    public static void installerAPK(String apkPath, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取屏幕density属性的等级 包括:low, mid, high, x
     */
    public static float getDensity(Context context) {
        WindowManager mWindowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ;
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.density;
    }

    /**
     * <p>获取位深</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @param context
     * @return
     * @author xky
     * @since 1.0.0
     */
    public static int getPixe(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        int pixelformat = display.getPixelFormat();
        PixelFormat localPixelFormat1 = new PixelFormat();
        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
        int deepth = localPixelFormat1.bytesPerPixel;// 位深
        return deepth;
    }


    /**
     * 判断是否可能有ROOT权限
     *
     * @return
     */
    public static boolean haveRoot() {
        try {
            File file = new File("/system/bin/su");
            File file2 = new File("/system/xbin/su");
            return file.exists() || file2.exists();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * <p>隐藏软键盘<p/>
     *
     * @param context
     * @param view
     * @author xky
     */
    public static void hideIme(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showIme(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        //        imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    public static void full(Context context, boolean enable) {
        Activity ac = (Activity) context;
        if (enable) {
            ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            ac.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 复制文本到系统剪切板
     */
    public static void copy2ClipboardManager(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
    }

    /***
     * 获取渠道
     *
     * @param context
     * @param meta
     * @return
     */
    public static String getMetaData(Context context, String meta) {
        PackageManager manager = context.getPackageManager();
        try {
            ApplicationInfo info = manager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String mValue = info.metaData.getString(meta);
            return mValue;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

//    /**
//     * 获取本机的非系统应用列表
//     *
//     * @param context context
//     * @return 应用列表
//     */
//    public static List<AnchorUser2> getAppList(Context context) {
//        ArrayList<AnchorUser2> appList = new ArrayList<>(); //用来存储获取的应用信息数据
//        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
//
//        for (int i = 0; i < packages.size(); i++) {
//            PackageInfo packageInfo = packages.get(i);
//            AnchorUser2 tmpInfo = new AnchorUser2();
//            tmpInfo.setNick(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
//            tmpInfo.setPackageName(packageInfo.packageName);
//            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                appList.add(tmpInfo);//如果非系统应用，则添加至appList
//            }
//        }
//        return appList;
//    }

    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean checkAlertWindowPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }

//    @TargetApi(Build.VERSION_CODES.M)
//    public static boolean checkSpecialAlertPermission(Context context){
////        if (OSJudgementUtil.isMIUI8() && Device.getSysVersion() == Build.VERSION_CODES.M){
////            return Settings.canDrawOverlays(context);
////        }else {
////            return checkAlertWindowPermission(context);
////        }
//    }




    public static boolean checkGPSPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(2);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            Log.e("test", "checkGPSPermission: " + (m == AppOpsManager.MODE_ALLOWED));
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }

    public static boolean checkCoarseLocationGPSPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(1);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            Log.e("test", "checkCoarseLocationGPSPermission: " + (m == AppOpsManager.MODE_ALLOWED));
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }

    public static boolean checkFineLocationGPSPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(0);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            Log.e("test", "checkFineLocationGPSPermission: " + (m == AppOpsManager.MODE_ALLOWED));
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }
    /**
     * 获取系统状态栏高度；
     * @return
     */
    public static int getStatusBarHeight(Activity context) {
        try{
            Rect frame = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            return statusBarHeight;
        }catch (Exception e){
            return 0;
        }
    }

    public static int getStatusBarHeight2(Activity activity){
        int statusBarHeight2 = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight2 = activity.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
            statusBarHeight2 =0;
        }
        return statusBarHeight2;
    }
//
//    /**
//     *设置多媒体声音为0
//     * @param context
//     */
//    public static void setVolumeZero(Context context){
//        AudioManager mAudioManager =  (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        //设置之前，先暂存声音，用于恢复
//        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        PreferencesUtils.savePrefInt(context,"video_volume_key",current);
//        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//    }

//    /**
//     * 恢复多媒体声音
//     * @param context
//     */
//    public static void setVolumeNormal(Context context){
//        AudioManager mAudioManager =  (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        //设置之前，先暂存声音，用于恢复
//        int current = PreferencesUtils.loadPrefInt(context, "video_volume_key", -1);
//        if (current != -1){
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
//            PreferencesUtils.savePrefInt(context, "video_volume_key", -1);
//        }
//    }
}
