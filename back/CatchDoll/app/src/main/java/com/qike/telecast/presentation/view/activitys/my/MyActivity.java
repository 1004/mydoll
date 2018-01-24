package com.qike.telecast.presentation.view.activitys.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.ActivityUtils;
import com.qike.telecast.library.utils.UiUtils;
import com.qike.telecast.module.network.Page;
import com.qike.telecast.presentation.model.dto.account.User;
import com.qike.telecast.presentation.model.dto.account.UserInfo;
import com.qike.telecast.presentation.presenter.ErrorCodeOperate;
import com.qike.telecast.presentation.presenter.IBasePresenterLinstener;
import com.qike.telecast.presentation.presenter.account.AccountManager;
import com.qike.telecast.presentation.presenter.my.MyPresenter;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;
import com.qike.umengshare_643.UmengUtil643;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class MyActivity extends AppCompatBaseActivity implements IBasePresenterLinstener {
    private TextView mGoldTv;
    private View mRechangeTv;
    private View mMoneyHistory;
    private View mInviteGive;
    private View mInviteCode;
    private ImageView mBgMusic;
    private ImageView mVoice;
    private View mExitTv;
    private MyPresenter mMyPresenter;
    private UserInfo mUserInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    public void initView() {
        mGoldTv = (TextView) findViewById(R.id.my_money_tv);
        mRechangeTv = findViewById(R.id.my_rechange_tv);
        mMoneyHistory = findViewById(R.id.my_rechange_history);
        mInviteGive = findViewById(R.id.my_invite_give);
        mInviteCode = findViewById(R.id.my_invite_code);
        mBgMusic = (ImageView) findViewById(R.id.my_bg_music_iv);
        mVoice = (ImageView) findViewById(R.id.my_voice);
        mExitTv = findViewById(R.id.my_exit);
    }

    @Override
    public void initData() {
        mMyPresenter = new MyPresenter();
        setCommonTitle(getMString(R.string.string_my_title));
    }

    @Override
    public void onResume() {
        super.onResume();
        updata();
    }



    @Override
    public void setListener() {
        mRechangeTv.setOnClickListener(this);
        mMoneyHistory.setOnClickListener(this);
        mInviteGive.setOnClickListener(this);
        mInviteCode.setOnClickListener(this);
        mBgMusic.setOnClickListener(this);
        mVoice.setOnClickListener(this);
        mExitTv.setOnClickListener(this);
        mMyPresenter.registPresenterCallBack(this);
    }


    @Override
    public void loadData() {
//        mMyPresenter.firstTask();
    }

    @Override
    public void dataResult(Object obj, Page page, int status) {
        if (obj != null && obj instanceof UserInfo){
            mUserInfo = (UserInfo) obj;
            updata();
        }
    }

    private void updata(){
        User user = AccountManager.getInstance().getUser();
        if (user != null){
            mGoldTv.setText(user.getGold()+"");
        }
    }

    @Override
    public void errerResult(int code, String msg) {
        ErrorCodeOperate.executeError("", this, code, msg, true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.my_invite_code:
                //兑换邀请码
                ActivityUtils.startInvideCodeActivity(this);
                break;
            case R.id.my_rechange_history:
                //消费记录
                ActivityUtils.startMySpendHistory(this);
                break;
            case R.id.my_invite_give:
//                邀请奖励
//                if (mUserInfo == null) {
//                    Toast.makeText(this,"信息获取中",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                ActivityUtils.startMyInviteShareHistory(this);
                break;
            case R.id.my_exit:
                logout();
                break;
            case R.id.my_rechange_tv:
                ActivityUtils.startRechargeListActivity(this);
                break;
        }
    }

    private void logout() {
        UmengUtil643.deleteAuth(this, SHARE_MEDIA.WEIXIN);
        AccountManager.getInstance().logout();
        UiUtils.finishAllALiveAcitity();
        ActivityUtils.startLoginActivity(this);
    }
}
