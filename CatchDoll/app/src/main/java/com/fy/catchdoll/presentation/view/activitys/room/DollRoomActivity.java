package com.fy.catchdoll.presentation.view.activitys.room;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.library.utils.tool.SoftKeyBoardListener;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.fy.catchdoll.presentation.view.activitys.base.AppCompatBaseActivity;

/**
 * Created by xky on 2017/12/1 0001.
 */
public class DollRoomActivity extends AppCompatBaseActivity{
    private LinearLayout mVideoChatContainer;
    private View mChatBtn;
    private Handler handler;
    private DialogManager dialogManager;


    @Override
    public int getLayoutId() {
        return R.layout.activity_room_2;
    }

    @Override
    public void initView() {
        mVideoChatContainer = (LinearLayout) findViewById(R.id.room_video_chat_container);
        mChatBtn = findViewById(R.id.room_chat_btn);
    }

    @Override
    public void initData() {
        inittopViewH();
        handler = new Handler();
        dialogManager = new DialogManager(this);
    }

    private void inittopViewH(){
        int[] screenWidthAndHeight = DeviceUtils.getScreenWidthAndHeight(this);
        int allH = (int) (screenWidthAndHeight[1]-getResources().getDimension(R.dimen.dimen_nav_h));
        ViewGroup.LayoutParams params = mVideoChatContainer.getLayoutParams();
        params.height = allH;
        mVideoChatContainer.setLayoutParams(params);
    }

    @Override
    public void setListener() {
        mChatBtn.setOnClickListener(this);
        softKeyboardListnenr();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.room_chat_btn:
                dialogManager.showDialog(DialogStyle.SENT_CHAT,null,null);
                break;
        }
    }




    /**
     * 软键盘显示与隐藏的监听
     */
    private void softKeyboardListnenr() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {/*软键盘显示：执行隐藏title动画，并修改listview高度和装载礼物容器的高度*/
            }

            @Override
            public void keyBoardHide(int height) {/*软键盘隐藏：隐藏聊天输入框并显示聊天按钮，执行显示title动画，并修改listview高度和装载礼物容器的高度*/
                dialogManager.dismissDialog();
            }
        });
    }

}
