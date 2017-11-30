package com.fy.catchdoll.presentation.presenter.share;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;
import com.fy.catchdoll.library.utils.PushLogUtils;
import com.fy.catchdoll.library.widgets.dialog.DialogManager;
import com.fy.catchdoll.library.widgets.dialog.DialogStyle;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class SharePresenter implements DialogManager.OnClickListenerContent {
    private Activity mContext;
    private DialogManager mManger;
    private String mShareTitle;
    private String mShareContent;
    private String mShareImgUri;
    private DialogManager.OnClickListenerContent mClickListener;
    private static final int REQUEST_TEXT = 0x12;
    private boolean isLiveShare = false;

    private String mTargetUrlId = "http://www.feiyun.tv/[:ID:]";

    private String mTopicTargetUrlId = "";
    private String defaultTopicSuffx = "wap/space/info/";


    private static String defaultUrl = "http://www.feiyun.tv/";
    private String mTargetUrl = "";
    private String mTid;

    public SharePresenter(Activity context) {
        mContext = context;
        mManger = new DialogManager(context);
        mTargetUrlId = defaultUrl + "[:ID:]";

        mTopicTargetUrlId = defaultUrl+defaultTopicSuffx+"[:ID:]";
    }

    /**
     * <p>TODO(概括性描述)</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @param title   分享的标题
     * @param content 分享的内容
     * @param imgurl  分享的图片链接
     * @param id      分享id
     * @author xky
     * @since 1.0.0
     */
    public void showShareView(String title, String content, String imgurl, String id) {
        mShareTitle = getSplitStr(title);
        mShareContent = getSplitStr(content);
        mShareImgUri = imgurl;
        mTid = id;
        mTargetUrl = mTargetUrlId.replace("[:ID:]", id);
        mManger.showDialog(DialogStyle.SHARE, this, new Object[]{});
        isLiveShare = false;
    }

    /**
     * <p>帖子的分享 </p><br/>
     *
     * @param title   分享的标题
     * @param content 分享的内容
     * @param imgurl  分享的图片链接
     * @param id      分享id
     * @author xky
     * @since 1.0.0
     */
    public void showTopicShareView(String title, String content, String imgurl, String id) {
        mShareTitle = getSplitStr(title);
        mShareContent = getSplitStr(content);
        mShareImgUri = imgurl;
        mTid = id;
        mTargetUrl = mTopicTargetUrlId.replace("[:ID:]", id);
        mManger.showDialog(DialogStyle.SHARE, this, new Object[]{});
        isLiveShare = false;
    }

    /**
     * <p>TODO(概括性描述)</p><br/>
     * <p>TODO(详细描述)</p>
     *
     * @param title   分享的标题
     * @param content 分享的内容
     * @param imgurl  分享的图片链接
     * @param url     分享地址
     * @author xky
     * @since 1.0.0
     */
    public void showShareViewSite(String title, String content, String imgurl, String url) {
        mShareTitle = getSplitStr(title);
        mShareContent = getSplitStr(content);
        mShareImgUri = imgurl;
        mTid = url;
        if (url != null && !url.isEmpty()) {
            mTargetUrl = url;
        } else {
            mTargetUrl = defaultUrl;
        }
        mManger.showDialog(DialogStyle.SHARE, this, new Object[]{});
        isLiveShare = false;
    }

    private UMShareListener mUMShareistener;
    private UMShareListener mInnerShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            mManger.showDialog(DialogStyle.LOADING,null,"分享中...");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            mManger.dismissDialog();
            if (mUMShareistener != null) {
                mUMShareistener.onResult(share_media);
            } else {
                Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            try {
                mManger.dismissDialog();
                if (mUMShareistener != null) {
                    mUMShareistener.onError(share_media, throwable);
                } else {
                    Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
                    try {
                        PushLogUtils.v("SHARE_PRESENTER", throwable.toString());
                    } catch (Exception e) {
                        PushLogUtils.v("SHARE_PRESENTER", "分享失败,e:" + e.getMessage() == null ? "" : e.getMessage());
                    }
                }
            }catch(Exception e){}
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            mManger.dismissDialog();
            if (mUMShareistener != null) {
                mUMShareistener.onCancel(share_media);
            } else {
                Toast.makeText(mContext, "分享取消了", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void setUMShareListener(UMShareListener mUMShareistener) {
        this.mUMShareistener = mUMShareistener;
    }

    public void showLiveShareView(String title, String content, String imgurl, String id) {
        mShareTitle = getSplitStr(title);
        mShareContent = getSplitStr(content);
        mShareImgUri = imgurl;
        mTid = id;
        mTargetUrl = mTargetUrlId.replace("[:ID:]", id);
        mManger.showDialog(DialogStyle.SHARE, this, DialogManager.START_LIVE_SHARE);
        isLiveShare = true;
    }

    public void setOnClickListener(DialogManager.OnClickListenerContent mListener) {
        mClickListener = mListener;
    }

    public void startShare(SHARE_MEDIA platform,String title, String content, String imgurl,String id){
        mShareTitle = getSplitStr(title);
        mShareContent = getSplitStr(content);
        mShareImgUri = imgurl;
        mTargetUrl = mTargetUrlId.replace("[:ID:]", id);

        UMShareAPI mShareAPI = UMShareAPI.get(mContext);
        if (platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.QZONE){
            if (!mShareAPI.isInstall(mContext, SHARE_MEDIA.QQ)) {
                Toast.makeText(mContext, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (platform == SHARE_MEDIA.WEIXIN || platform == SHARE_MEDIA.WEIXIN_CIRCLE){
            if (!mShareAPI.isInstall(mContext, platform)) {
                Toast.makeText(mContext, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (platform == SHARE_MEDIA.SINA) {
            ShareController.getInstance().sharePicText(mContext, platform, mShareTitle + mShareContent + mTargetUrl, mShareImgUri, null, null, R.mipmap.ic_launcher, mInnerShareListener);
        } else {
            ShareController.getInstance().shareLink(mContext, platform, mShareTitle, mShareContent, mTargetUrl, mShareImgUri, null, null, R.mipmap.ic_launcher, mInnerShareListener);
        }
    }

    public void operateActivityResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view, Object... content) {
//        SHARE_MEDIA platform = SHARE_MEDIA.QQ;
//        UMShareAPI mShareAPI = UMShareAPI.get(mContext);
//        switch (view.getId()) {
//            case R.id.share_to_qq_container:
//                //qq
//                platform = SHARE_MEDIA.QQ;
//                if (!mShareAPI.isInstall(mContext, platform)) {
//                    Toast.makeText(mContext, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.QQ");
//                break;
//            case R.id.share_to_qqzone_container:
//                //qqzone
//                platform = SHARE_MEDIA.QZONE;
//                if (!mShareAPI.isInstall(mContext, SHARE_MEDIA.QQ)) {
//                    Toast.makeText(mContext, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.QZONE");
//                break;
//            case R.id.share_to_wx_container:
//                //wx
//                platform = SHARE_MEDIA.WEIXIN;
//                if (!mShareAPI.isInstall((Activity) mContext, platform)) {
//                    Toast.makeText(mContext, "请安装微信客户端", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.WEIXIN");
//                break;
//            case R.id.share_to_wxfriend_container:
//                //wxf
//                platform = SHARE_MEDIA.WEIXIN_CIRCLE;
//                if (!mShareAPI.isInstall((Activity) mContext, platform)) {
//                    Toast.makeText(mContext, "请安装微信客户端", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.WEIXIN_CIRCLE");
//                break;
//            case R.id.share_to_sina_container:
//                //新浪微博
//                platform = SHARE_MEDIA.SINA;
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.SINA");
//                break;
//            case R.id.share_to_account_container:
//                //通讯录
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.ACCOUNT");
//                jumpComm();
//                if (!isLiveShare) {
//                    mManger.dismissDialog();
//                }
//                return;
//            case R.id.share_to_copy_container:
//                //粘贴板
//                if (TextUtils.isEmpty(mTargetUrl)) {
//                    mTargetUrl = defaultUrl;
//                }
//                StatisticsIncident.getInstance().analysisLiveDetailShareClick(mContext, "SHARE_MEDIA.COPY");
//                DeviceUtils.copy2ClipboardManager(mContext, mShareContent + "\n" + mTargetUrl);
//                Toast.makeText(mContext, "复制内容到粘贴板", Toast.LENGTH_SHORT).show();
//                if (!isLiveShare) {
//                    mManger.dismissDialog();
//                }
//                return;
//            case R.id.share_start_title:
//                mManger.dismissDialog();
//                if (mClickListener != null) {
//                    mClickListener.onClick(view);
//                }
//                return;
//            case R.id.share_cancel_title:
//                mManger.dismissDialog();
//                return;
//            default:
//                break;
//        }
//        if (!isLiveShare) {
//            mManger.dismissDialog();
//        }
//        if (platform == SHARE_MEDIA.SINA) {
//            ShareController.getInstance().sharePicText(mContext, platform, mShareTitle + mShareContent + mTargetUrl, mShareImgUri, null, null, R.drawable.logo, mInnerShareListener);
//        } else {
//            ShareController.getInstance().shareLink(mContext, platform, mShareTitle, mShareContent, mTargetUrl, mShareImgUri, null, null, R.drawable.logo, mInnerShareListener);
//        }
//        if (platform == SHARE_MEDIA.SINA) {
//            //mShareTitle + mShareContent 的原因，新浪分享时会只显示conten的内容，title不会显示
//            ShareController.getInstance().openDirShare(mContext, platform, mShareTitle, mShareTitle + mShareContent, mTargetUrl, mShareImgUri, null, null, R.drawable.fy_icon, mUMShareistener);
//        } else {
//            ShareController.getInstance().openDirShare(mContext, platform, mShareTitle, mShareContent, mTargetUrl, mShareImgUri, null, null, R.drawable.fy_icon, mInnerShareListener);
//        }
    }

    private void jumpComm() {
        try {
            Intent it = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
            mContext.startActivityForResult(it, REQUEST_TEXT);
        } catch (Exception e) {
        }
    }


    private void shareSend(String title, String content, String number, String sdCardImgUrl) {
        //			Intent intent = new Intent(Intent.ACTION_SEND);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        Uri smsToUri = Uri.parse("smsto:" + number);
        intent.setData(smsToUri);
        intent.putExtra("sms_body", content);
        mContext.startActivity(intent);
    }

    /**
     * <p>带有id直接分享 无弹出窗</p><br/>
     * <p>帖子详情</p><br/>
     *
     * @param platform
     * @author xky
     * @since 1.0.0
     */
    public void showShareController(String title, String content, String imgurl, String id, SHARE_MEDIA platform) {
        mTargetUrl = mTargetUrl.replace("[:ID:]", id);
        title = getSplitStr(title);
        content = getSplitStr(content);
        //ShareController.getInstance().openDirShare(mContext, platform, title, content, null, imgurl, null, null, R.drawable.fy_icon, null);
    }

    /**
     * <p>普通无弹出窗 直接分享</p><br/>
     * <p>邀请好友</p>
     *
     * @param title
     * @param content
     * @param imgurl
     * @param targetUrl
     * @param platform
     * @author xky
     * @since 1.0.0
     */
    public void showShareControllerNoId(String title, String content, String imgurl, String targetUrl, SHARE_MEDIA platform) {
       // ShareController.getInstance().openDirShare(mContext, platform, title, content, targetUrl, imgurl, null, null, R.drawable.fy_icon, null);
    }

    private String getSplitStr(String content) {
//		if(!TextUtils.isEmpty(content) && content.length()>14){
//			content = content.substring(0, 14)+"...";
//		}
        if (TextUtils.isEmpty(content)) {
            content = mContext.getResources().getString(R.string.string_share_no_content);
        }
        return content;
    }

}
