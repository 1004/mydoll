package com.fy.catchdoll.library.utils;

import android.content.Context;
import android.content.Intent;

import com.fy.catchdoll.presentation.view.activitys.main.MainActivity;
import com.fy.catchdoll.presentation.view.activitys.room.RoomActivity;

/**
 * Created by xky on 2017/9/22 0022.
 */
public class ActivityUtils {
    /**
     * 启动首页
     * @param context
     */
    public static void startMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动直播间 开始抓娃娃
     * @param context
     * @param roomId
     */
    public static void startRoomActivity(Context context,String roomId){
        Intent intent = new Intent(context, RoomActivity.class);
        context.startActivity(intent);
    }
}
