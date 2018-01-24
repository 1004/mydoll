package com.qike.telecast.library.widgets.dialog;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.DeviceUtils;
import com.qike.telecast.library.widgets.BackEditText;
import com.qike.telecast.presentation.presenter.update2.Inter.OnUploadListener;
import com.qike.telecast.presentation.presenter.update2.UploadManager;


/**
 * 管理Dialog的类
 *
 * @author sll
 */
public class DialogManager {
    private LayoutInflater mInflater;
    private Context mContext;
    private SimpleDialog mSimpleDialog;
    private DialogStyle mStyle;
    public static final String START_LIVE_SHARE = "start_live";
    private Handler mHandler;
    private BackEditText mSentContentTv;

    //需要实时更新还剩多少秒可召唤粉丝用到的控件

    public DialogManager(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mHandler = new Handler();
    }

    /**
     * 显示Dialog
     *
     * @param style
     * @param listener
     * @param objects
     * @author sll
     */
    public void showDialog(DialogStyle style, OnClickListenerContent listener, Object... objects) {
        mStyle = style;
        mSimpleDialog = getDialog();
        mSimpleDialog.setBottomViewVisibility(false);
        mSimpleDialog.setTopViewVisibility(false);
        switch (style) {
            case EXIT:
                exit(listener);
                break;
            case SHARE:
//                operateShare(listener, objects);
                break;
            case UPLOAD:
                operateUpload(listener, objects);
                break;
            case COMMON:
                break;
            case LOADING:
                operateLoading(listener, objects);
                break;
            case COMMON_INTOR:
                operateCommonIntro(listener, objects);
                break;
            case SENT_CHAT:
                operateChat(listener,objects);
                break;
            case PAY_STATE:
                operaatePayState(listener,objects);
                break;
            case BOX_ORDER_SUCCESS:
                operateOrderSuccess(listener,objects);
                break;
            default:
                break;
        }
        mSimpleDialog.dismiss();
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            try {
                mSimpleDialog.show();
                if (mStyle == DialogStyle.SENT_CHAT && mSentContentTv != null){
                    mHandler.postDelayed(keywork,50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void operateOrderSuccess(final OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_order_success, null);
        TextView minfoTv = (TextView) mView.findViewById(R.id.dialog_order_send_info);
        TextView mOKBtn = (TextView) mView.findViewById(R.id.box_order_success);
        if (objects != null && objects.length > 0) {
            String info = (String) objects[0];
            minfoTv.setText(info);
        }
        mOKBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setListener(listener, v, new Object[]{});
            }
        });
        mSimpleDialog.createOrUpdate(-1, mView);
    }

    private void operaatePayState(OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_pay_state, null);
        TextView minfoTv = (TextView) mView.findViewById(R.id.pay_info_title);
        if (objects != null && objects.length > 0) {
            String info = (String) objects[0];
            minfoTv.setText(info);
        }
        mSimpleDialog.createOrUpdate(-1, mView);
    }

    private void operateChat(OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_chat, null);
        mSentContentTv = (BackEditText) mView.findViewById(R.id.etInput);
        mSimpleDialog.setFocusView(mSentContentTv);

        mSentContentTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSentContentTv.clearFocus();
                mSentContentTv.requestFocus();
                mSentContentTv.findFocus();
                DeviceUtils.showIme(mContext, mSentContentTv);
            }
        });

        mSentContentTv.setOnBackClickListener(new BackEditText.OnBackClickListener() {
            @Override
            public void onBack(View v) {
                dismissDialog();
            }
        });

        mSimpleDialog.createOrUpdate(-1, mView);
    }

    Runnable keywork = new Runnable() {
        @Override
        public void run() {
            if (mStyle == DialogStyle.SENT_CHAT && mSentContentTv != null){
                mSentContentTv.clearFocus();
                mSentContentTv.setFocusableInTouchMode(true);
                mSentContentTv.setFocusable(true);
                mSentContentTv.requestFocus();
                mSentContentTv.findFocus();
                DeviceUtils.showIme(mContext, mSentContentTv);
            }
        }
    };


    private void operateCommonIntro(final OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_live_common_info, null);
        TextView contentTv = (TextView) mView.findViewById(R.id.dialog_content);
        TextView mCancelTv = (TextView) mView.findViewById(R.id.tv_cancel_common);
        TextView mYesTv = (TextView) mView.findViewById(R.id.tv_yes_common);
        if (objects != null && objects.length > 0) {
            String content = (String) objects[0];
            contentTv.setText(content);
        }
        if (objects != null && objects.length > 1) {
            String left = (String) objects[1];
            mCancelTv.setText(left);
        }
        if (objects != null && objects.length > 2) {
            String right = (String) objects[2];
            mYesTv.setText(right);
        }

        mCancelTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setListener(listener, view, new Object[]{});
            }
        });
        mYesTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setListener(listener, view, new Object[]{});
            }
        });
        mSimpleDialog.createOrUpdate(-1, mView);
    }


    private void operateLoading(OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_common_loading, null);
        TextView minfoTv = (TextView) mView.findViewById(R.id.loading_info_title);
        if (objects != null && objects.length > 0) {
            String info = (String) objects[0];
            minfoTv.setText(info);
        }
        mSimpleDialog.createOrUpdate(-1, mView);
    }



    private void operateUpload(final OnClickListenerContent listener, Object[] objects) {
        View mView = mInflater.inflate(R.layout.dialog_upload_view, null);
        TextView tv_content = (TextView) mView.findViewById(R.id.tv_content);
        final TextView tv_update = (TextView) mView.findViewById(R.id.tv_update);
        ImageView iv_cancel = (ImageView) mView.findViewById(R.id.iv_cancel);
        final ProgressBar pb_progress = (ProgressBar) mView.findViewById(R.id.pb_progress);
        View mProgressContainer = mView.findViewById(R.id.progress_container);
        final TextView mProgressTv = (TextView) mView.findViewById(R.id.tv_progress_load);
        final boolean isForeceUpdate = (Boolean) objects[0];
        String content = (String) objects[1];
        final String path = (String) objects[2];
        tv_content.setText(content);
        iv_cancel.setVisibility(isForeceUpdate ? View.INVISIBLE : View.VISIBLE);
        tv_update.setVisibility(isForeceUpdate ? View.INVISIBLE : View.VISIBLE);
        mProgressContainer.setVisibility(isForeceUpdate ? View.VISIBLE : View.GONE);
        if (isForeceUpdate) {
            UploadManager.getInstance().registOnUploadListener("upload_key", new OnUploadListener() {
                @Override
                public void onStartUpload() {
                    mProgressTv.setText("正在升级");
                    pb_progress.setProgress(0);
                }

                @Override
                public void onUploadProgress(long total, long size, int percent) {
                    pb_progress.setProgress(percent);
                    mProgressTv.setText("正在升级" + percent + "%");
                }

                @Override
                public void onUploadFinish(String path) {
//	                    DeviceUtils.installerAPK(path,mContext);
                    dismissDialog();
                }

                @Override
                public void onUploadError(String msg) {
                    mProgressTv.setText("更新错误，点击重试");
                    mProgressTv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setListener(listener, view, path);
                        }
                    });
                }

                @Override
                public void onUploadCancel() {
                    dismissDialog();
                }
            });
        } else {
            UploadManager.getInstance().unRegistOnUploadListener("upload_key");
        }
        tv_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setListener(listener, view, path, isForeceUpdate);
            }
        });
        iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setListener(listener, view);
            }
        });

        mSimpleDialog.createOrUpdate(-1, mView);
    }

//    private void operateShare(final OnClickListenerContent mListener, Object[] objects) {
//        boolean isLiveShare = false;
//        if (objects != null && objects.length > 0) {
//            String tag = (String) objects[0];
//            isLiveShare = START_LIVE_SHARE.equals(tag);
//        }
//        View mView = mInflater.inflate(isLiveShare ? R.layout.share_dialog_live_layout : R.layout.zeze_share_dialog_layout, null);
//
//        RelativeLayout share2qqContainer = (RelativeLayout) mView.findViewById(R.id.share_to_qq_container);
//        RelativeLayout share2qqZoneContainer = (RelativeLayout) mView.findViewById(R.id.share_to_qqzone_container);
//        RelativeLayout share2WxContainer = (RelativeLayout) mView.findViewById(R.id.share_to_wx_container);
//        RelativeLayout share2WxFContainer = (RelativeLayout) mView.findViewById(R.id.share_to_wxfriend_container);
//        RelativeLayout share2SinaContainer = (RelativeLayout) mView.findViewById(R.id.share_to_sina_container);
//        RelativeLayout share2Account = (RelativeLayout) mView.findViewById(R.id.share_to_account_container);
//        RelativeLayout share2Copy = (RelativeLayout) mView.findViewById(R.id.share_to_copy_container);
//        ShareSwitchControllerDto dto = ShareSwitchController.getInstance().getShareSwitch();
//        if (dto != null) {
//            if (dto.getQq() == ShareSwitchControllerDto.INVISIBLE) {
//                share2qqContainer.setVisibility(View.GONE);
//            } else {
//                share2qqContainer.setVisibility(View.VISIBLE);
//            }
//            if (dto.getQzone() == ShareSwitchControllerDto.INVISIBLE) {
//                share2qqZoneContainer.setVisibility(View.GONE);
//            } else {
//                share2qqZoneContainer.setVisibility(View.VISIBLE);
//            }
//            if (dto.getWechat() == ShareSwitchControllerDto.INVISIBLE) {
//                share2WxContainer.setVisibility(View.GONE);
//            } else {
//                share2WxContainer.setVisibility(View.VISIBLE);
//            }
//            if (dto.getWxpyq() == ShareSwitchControllerDto.INVISIBLE) {
//                share2WxFContainer.setVisibility(View.GONE);
//            } else {
//                share2WxFContainer.setVisibility(View.VISIBLE);
//            }
//            if (dto.getWeibo() == ShareSwitchControllerDto.INVISIBLE) {
//                share2SinaContainer.setVisibility(View.GONE);
//            } else {
//                share2SinaContainer.setVisibility(View.VISIBLE);
//            }
//        }
//        View mTitleContainer = mView.findViewById(isLiveShare ? R.id.share_start_title : R.id.share_cancel_title);
//        mTitleContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2qqContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2qqZoneContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2WxContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2WxFContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2SinaContainer.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2Account.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        share2Copy.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                setListener(mListener, v, new Object[]{});
//            }
//        });
//        mSimpleDialog.createOrUpdate(-1, mView);
//    }

    public static boolean isRememberChose = false;




    private void exit(final OnClickListenerContent listener) {
        View view = mInflater.inflate(R.layout.dialog_exit, null);
        mSimpleDialog.createOrUpdate(-1, view);

    }




    protected void setListener(OnClickListenerContent listener, View v, Object... objects) {
        if (listener != null) {
            listener.onClick(v, objects);
        }
    }

    /**
     * 获取SimpleDialog实例
     *
     * @return
     */
    public SimpleDialog getDialog() {
        dismissDialog();
        mSimpleDialog = null;
        if (mStyle == DialogStyle.EXIT ) { //根据需求修改dialog的样式
            mSimpleDialog = new SimpleDialog(mContext);
            mSimpleDialog.setCanceledOnTouchOutside(false);
        } else if (mStyle == DialogStyle.UPLOAD || mStyle==DialogStyle.PAY_STATE) {
            mSimpleDialog = new SimpleDialog(mContext);
            mSimpleDialog.setCancelable(false);
            mSimpleDialog.setCanceledOnTouchOutside(false);
        } else if  (mStyle == DialogStyle.SHARE  || mStyle == DialogStyle.BOTTOM_SELECT) { //根据需求修改dialog的样式
            mSimpleDialog = new SimpleDialog(mContext, R.style.MyTopciDialog);
            mSimpleDialog.setCanceledOnTouchOutside(true);
            mSimpleDialog.changeSize(DeviceUtils.getScreenWidthAndHeight((Activity) mContext)[0], -1);
            mSimpleDialog.setDialogAnim(R.style.dialogWindowAnim);
        }else if (mStyle == DialogStyle.SENT_CHAT) {
            mSimpleDialog = new SimpleDialog(mContext,R.style.style_diolog_no_bg);
            mSimpleDialog.setCanceledOnTouchOutside(true);
            mSimpleDialog.changeSize(DeviceUtils.getScreenWidthAndHeight((Activity) mContext)[0], -1);
            mSimpleDialog.setDialogAnim(R.style.replydialogWindowAnim);
//            mSimpleDialog.setOnDismissListener(this);
            //点击返回键  dialog与软键盘一同消失
            mSimpleDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismissDialog();
                    }
                    return false;
                }
            });
        } else {
            mSimpleDialog = new SimpleDialog(mContext);
            mSimpleDialog.setCanceledOnTouchOutside(true);
        }
        return mSimpleDialog;
    }

    public void dismissDialog() {
        if (mSimpleDialog != null && mSimpleDialog.isShowing()) {
            try {
                mSimpleDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShowDialog(){
        if (mSimpleDialog != null){
            return mSimpleDialog.isShowing();
        }else {
            return false;
        }
    }

    protected void Toast(int resId) {
        Toast.makeText(mContext, mContext.getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    public interface OnClickListenerContent {
        public void onClick(View view, Object... content);
    }

}
