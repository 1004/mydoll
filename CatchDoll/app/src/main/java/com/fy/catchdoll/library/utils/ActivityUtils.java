package com.fy.catchdoll.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fy.catchdoll.presentation.model.dto.account.UserInfo;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.model.dto.doll.CatchHistoryDto;
import com.fy.catchdoll.presentation.view.activitys.box.BoxInfoActivity;
import com.fy.catchdoll.presentation.view.activitys.box.UpdateAddressActivity;
import com.fy.catchdoll.presentation.view.activitys.login.LoginActivity;
import com.fy.catchdoll.presentation.view.activitys.main.MainActivity;
import com.fy.catchdoll.presentation.view.activitys.my.MyActivity;
import com.fy.catchdoll.presentation.view.activitys.my.MyExchangeActivity;
import com.fy.catchdoll.presentation.view.activitys.my.MyInviteShareActivity;
import com.fy.catchdoll.presentation.view.activitys.my.MySpendActivity;
import com.fy.catchdoll.presentation.view.activitys.orderhistory.CatchHistoryActivity;
import com.fy.catchdoll.presentation.view.activitys.orderhistory.OrderHistoryActivity;
import com.fy.catchdoll.presentation.view.activitys.question.QuestionActivity;
import com.fy.catchdoll.presentation.view.activitys.recharge.RechargeListActivity;
import com.fy.catchdoll.presentation.view.activitys.room.DollRoomActivity;
import com.fy.catchdoll.presentation.view.activitys.room.RoomActivity;
import com.fy.catchdoll.presentation.view.activitys.video.VideoActivity;
import com.fy.catchdoll.presentation.view.activitys.web.WebActivity;

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
    /**
     * 启动直播间 开始抓娃娃
     * @param context
     * @param roomId
     */
    public static void startDollRoomActivity(Context context,String roomId){
        Intent intent = new Intent(context, DollRoomActivity.class);
        intent.putExtra(DollRoomActivity.DOLL_ROOM_KEY,roomId);
        context.startActivity(intent);
    }
    /**
     * 启动登录页面
     * @param context
     */
    public static void startLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动我的界面
     * @param context
     */
    public static void startMyActivity(Context context){
        Intent intent = new Intent(context, MyActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动邀请码界面
     * @param context
     */
    public static void startInvideCodeActivity(Context context){
        Intent intent = new Intent(context, MyExchangeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动背包界面
     * @param context
     */
    public static void startBoxInfoActivity(Context context){
        Intent intent = new Intent(context, BoxInfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动消费记录
     * @param context
     */
    public static void startMySpendHistory(Context context){
        Intent intent = new Intent(context, MySpendActivity.class);
        context.startActivity(intent);
    }
    /**
     * 启动分享界面
     * @param context
     */
    public static void startMyInviteShareHistory(Context context){
        Intent intent = new Intent(context, MyInviteShareActivity.class);
        context.startActivity(intent);
    }


    /**
     * 启动充值
     * @param context
     */
    public static void startRechargeListActivity(Context context){
        Intent intent = new Intent(context, RechargeListActivity.class);
        context.startActivity(intent);
    }
    /**
     * 启动充值
     * @param context
     */
    public static void startUpdateAddressActivity(Activity context,AddressInfo info){
        Intent intent = new Intent(context, UpdateAddressActivity.class);
        if (info != null){
            intent.putExtra(UpdateAddressActivity.UPDATA_INFO_ADDRESS_KEY,info);
        }
        context.startActivityForResult(intent, UpdateAddressActivity.ADDRESS_REQUESTCODE);
    }


    /**
     * 发货记录
     * @param context
     */
    public static void startSendHistoryActivity(Context context){
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        context.startActivity(intent);
    }

    /**
     * 启动web
     */
    public static void startWebActivity(Context context, String url, String title) {
        Loger.d("url---" + url);
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.KEY_URL, url);
        intent.putExtra(WebActivity.KEY_NAME, title);
        context.startActivity(intent);
    }

    /**
     * 启动播放器
     * @param context
     * @param url
     */
    public static void startPlayerActivity(Context context,String url){
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(VideoActivity.URL_KEY, url);
        context.startActivity(intent);
    }

    /**
     * 启动抓取记录
     * @param context
     */
    public static void startCatchHistoryActivity(Context context){
        Intent intent = new Intent(context, CatchHistoryActivity.class);
        context.startActivity(intent);
    }
    /**
     * 启动抓取记录
     * @param context
     */
    public static void startQuestionActivity(Context context,CatchHistoryDto dto){
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(QuestionActivity.QUESTION_HISTORY_KEY,dto);
        context.startActivity(intent);
    }
}
