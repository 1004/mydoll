package com.qike.telecast.presentation.view.activitys.my;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.Loger;
import com.qike.telecast.presentation.model.dto.account.User;
import com.qike.telecast.presentation.presenter.account.AccountManager;
import com.qike.telecast.presentation.presenter.share.SharePresenter;
import com.qike.telecast.presentation.view.activitys.base.AppCompatBaseActivity;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by xky on 2017/11/30 0030.
 * 我的邀请 分享
 */
public class MyInviteShareActivity extends AppCompatBaseActivity{
    public static final String INVITE_SHARE_DATA_KEY = "invite_share_data";
    private TextView mShareCodeTv;
    private TextView mShareHintInfo;
    private View mWxContainer;
    private View mQQContainer;
    private View mCircleContainer;
    private View mQQZoneContainer;
    private SharePresenter mSharePresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_share;
    }

    @Override
    public void initView() {
        mShareCodeTv = (TextView) findViewById(R.id.invite_share_code);
        mShareHintInfo = (TextView) findViewById(R.id.invite_share_content);
        mWxContainer = findViewById(R.id.share_wx_container);
        mQQContainer = findViewById(R.id.share_qq_container);
        mCircleContainer = findViewById(R.id.share_circle_container);
        mQQZoneContainer = findViewById(R.id.share_qqzone_container);
    }

    @Override
    public void initData() {
        setCommonTitle(getMString(R.string.string_invite_code_title));
        mSharePresenter = new SharePresenter(this);
        updataView();
    }

    private void updataView(){
        User user = AccountManager.getInstance().getUser();
        if(user != null){
            mShareCodeTv.setText(user.getInvitation_code());
        }
    }

    @Override
    public void setListener() {
        mWxContainer.setOnClickListener(this);
        mQQContainer.setOnClickListener(this);
        mCircleContainer.setOnClickListener(this);
        mQQZoneContainer.setOnClickListener(this);
//        mSharePresenter.setUMShareListener(mUMShareistener);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.share_circle_container:
                mSharePresenter.startShare(SHARE_MEDIA.WEIXIN_CIRCLE,"TITLE","CONTENT","","2");
                break;
            case R.id.share_qq_container:
                mSharePresenter.startShare(SHARE_MEDIA.QQ,"TITLE","CONTENT","","2");
                break;
            case R.id.share_qqzone_container:
                mSharePresenter.startShare(SHARE_MEDIA.QZONE,"TITLE","CONTENT","","2");
                break;
            case R.id.share_wx_container:
                mSharePresenter.startShare(SHARE_MEDIA.WEIXIN,"TITLE","CONTENT","","2");
                break;
        }
    }

    private UMShareListener mUMShareistener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Loger.d("分享成功---");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Loger.d("分享失败---");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Loger.d("分享取消---");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSharePresenter.operateActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadData() {

    }


}
