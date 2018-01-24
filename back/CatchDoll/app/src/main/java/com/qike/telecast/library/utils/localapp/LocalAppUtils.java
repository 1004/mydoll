package com.qike.telecast.library.utils.localapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2016/10/31 0031.
 */
public class LocalAppUtils {

    public static List<LocalAppBean> getLocalAppsNoSystem(Context context){
        List<LocalAppBean> localAppBeans = new ArrayList<LocalAppBean>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                LocalAppBean appBean = new LocalAppBean();
                appBean.setAppName(getApplicationName(pak.applicationInfo.packageName, pManager));
                appBean.setAppPackage(pak.applicationInfo.packageName);
                localAppBeans.add(appBean);
            }
        }
        return localAppBeans;
    }

    /**
     * 判断程序是否已安装
     * @param packagename
     * @return
     */
    public static boolean isAppInstalled( Context context, String packagename )
    {
        if(context == null || TextUtils.isEmpty(packagename)){
            return false;
        }
        try
        {
            context.getPackageManager().getPackageInfo( packagename, 0 );
            return true;
        }
        catch( PackageManager.NameNotFoundException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     *<p>启动游戏</p><br/>
     *<p>TODO(详细描述)</p>
     * @since 5.0.0
     * @author xky
     * @param context
     * @param packageName
     * @throws Exception
     */
    public static void startApp(Context context,String packageName) throws Exception {

        if(!isAppInstalled(context,packageName)){
            Toast.makeText(context, "未安装该游戏", Toast.LENGTH_SHORT).show();
            return;
        }
        PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);

        }
    }

    private static String getApplicationName(String packageName, PackageManager packageManager) {
        String applicationName = null;
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return applicationName;
    }

    public static Drawable getApplicationIcon(Context context,String packageName) {
        PackageManager pManager = context.getPackageManager();

        Drawable applicationDrawable = null;
        try {
            ApplicationInfo applicationInfo = pManager.getApplicationInfo(packageName, 0);
            applicationDrawable =  pManager.getApplicationIcon(applicationInfo);
        } catch (Exception e) {

        }
        return applicationDrawable;
    }


}
