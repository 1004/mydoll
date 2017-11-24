package com.fy.catchdoll.library.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.DeviceUtils;


/**
 * 统一管理的Dialog
 */
public class SimpleDialog extends Dialog implements Window.Callback
{

	public static final int MATCH_PARENT_W = 0x01;
	public static final int WRAP_CONTENT_W = 0x02;
	private final int MIDDLEVIEW = 1;

	private TextView mTextViewTitle;
	private TextView mTextViewInfo = null;
	private ImageView mImageViewIcon = null;

	private Button mButton1 = null;
	private Button mButton2 = null;
	private Button mButton3 = null;
	private View mLayoutTop;
	private View mLayoutMiddle = null;
	private View mLayoutBottom = null;

	private ViewGroup mMilldeView = null;
	private RelativeLayout contentLayout;

	private ViewGroup mDialogView;

	private CallBackforBackKey callBack;
	private Context mContext;

	public SimpleDialog(Context context) {
		super(context, R.style.MyDialog);
		setContentView(R.layout.simple_dialog2);
		setTitle(R.string.dialog_title);
		mMilldeView = (ViewGroup) findViewById(R.id.relativelayout_dialog_infoview);
		mTextViewInfo = (TextView) findViewById(android.R.id.text1);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		mContext = context;
		mTextViewInfo.setVisibility(View.GONE);
	}

	public SimpleDialog(Context context, int style) {
		super(context, style);
		setContentView(R.layout.simple_dialog2);
		setTitle(R.string.dialog_title);
		mMilldeView = (ViewGroup) findViewById(R.id.relativelayout_dialog_infoview);
		mTextViewInfo = (TextView) findViewById(android.R.id.text1);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		mContext = context;
		mTextViewInfo.setVisibility(View.GONE);

	}

	public SimpleDialog(Context context, int top, int bottom) {
		super(context, R.style.MyDialog);
		setContentView(R.layout.simple_dialog2);
		setTitle(R.string.dialog_title);
		mMilldeView = (ViewGroup) findViewById(R.id.relativelayout_dialog_infoview);

		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lp.setMargins(0, top, 0, bottom);
		mTextViewInfo = (TextView) findViewById(android.R.id.text1);
		mTextViewInfo.setLayoutParams(lp);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		mContext = context;
		mTextViewInfo.setVisibility(View.GONE);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refreshDialogView();
	}

	public void hideSoftInputFronWindow() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void createOrUpdate(int title, int message) {
		this.createOrUpdate(title, message, null);
	}

	public void createOrUpdate(int title, View contentView) {
		this.createOrUpdate(title, contentView, null);
	}

	public void createOrUpdate(int title, int message, CallBackforBackKey callBack) {
		this.createOrUpdate(title, message, null, callBack);
	}

	public void createOrUpdate(int title, View contentView, CallBackforBackKey callBack) {
		this.createOrUpdate(title, -1, contentView, callBack);
	}

	/** 入口 */
	public void createOrUpdate(int title, int message, View contentView, CallBackforBackKey callBack) {
		this.callBack = callBack;
		this.setTitle(title);
		if (contentView != null) {
			this.setView(contentView);
		} else {
			if (message != -1) {
				this.setInfo(message);
			}
		}
	}

	public void createOrUpdate(View contentView,int mode){
		if (contentView != null) {
			this.setView(contentView,mode);
		}
	}


	public void setInfo(String str) {
		setMiddleViewVisibility(true);
		if (mMilldeView == null) {
			mMilldeView = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
			mTextViewInfo = (TextView) findViewById(android.R.id.text1);
		}
		setView(mMilldeView);
		mTextViewInfo.setVisibility(View.VISIBLE);
		mTextViewInfo.setText(str);
	}

	public void showTranContnetBg() {
		if (mDialogView == null) {
			mDialogView = (ViewGroup) findViewById(R.id.relativelayout_dialog_view);
		}
		mDialogView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
	}

	/**
	 * 设置整个布局的padding
	 *
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setDialogPadding(int left, int top, int right, int bottom) {
		if (mDialogView == null) {
			mDialogView = (ViewGroup) findViewById(R.id.relativelayout_dialog_view);
		}
		mDialogView.setPadding(left, top, right, bottom);
	}

	/**
	 * 设置整个布局的大小
	 */
	public void setDialogSize(int width, int height) {
		if (mDialogView == null) {
			mDialogView = (ViewGroup) findViewById(R.id.relativelayout_dialog_view);
		}
		LayoutParams params = mDialogView.getLayoutParams();
		params.width = (int) (DeviceUtils.getDensity(getContext()) * width);
		params.height = (int) (DeviceUtils.getDensity(getContext()) * height);
		mDialogView.setLayoutParams(params);
	}

	public void setTitleSize(int width, int height) {
		//        if (mLayoutTop == null) mLayoutTop = findViewById (R.id.relativelayout_dialog_titlelayout);
		//
		//        LayoutParams params = mLayoutTop.getLayoutParams ();
		//        params.width = (int) (DeviceUtils.getDensity (getContext()) * width);
		//        params.height = (int) (DeviceUtils.getDensity (getContext()) * height);
		//        mLayoutTop.setLayoutParams (params);

	}

	/**
	 * 设置内容部分(contentLayout)的padding
	 *
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setPadding(int left, int top, int right, int bottom) {
		if (contentLayout == null) {
			contentLayout = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		}
		contentLayout.setPadding(left, top, right, bottom);
	}

	public void setInfo(int strRes) {
		setInfo(getContext().getString(strRes));
	}

	public void setInfo(View view) {
		setMiddleViewVisibility(true);

		if (mMilldeView == null) {
			mMilldeView = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mMilldeView.addView(view, MIDDLEVIEW, params);
	}

	public void setInfo(View view, RelativeLayout.LayoutParams params) {
		setMiddleViewVisibility(true);

		if (mMilldeView == null) {
			mMilldeView = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		}
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mMilldeView.addView(view, MIDDLEVIEW, params);
	}

	/** 可控制的View */
	private View contentView;

	public void setView(View view) {
		contentLayout = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		contentLayout.setVisibility(RelativeLayout.VISIBLE);
		contentLayout.removeAllViews();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		this.contentView = view;
		contentLayout.addView(view, params);
	}
	public void setView(View view,int mode) {
		contentLayout = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		contentLayout.setVisibility(RelativeLayout.VISIBLE);
		contentLayout.removeAllViews();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mode == MATCH_PARENT_W ?LayoutParams.MATCH_PARENT: LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		this.contentView = view;
		contentLayout.addView(view, params);
	}

	public void setView(View view, RelativeLayout.LayoutParams params) {
		contentLayout = (RelativeLayout) findViewById(R.id.relativelayout_dialog_infolayout);
		contentLayout.setVisibility(RelativeLayout.VISIBLE);
		contentLayout.removeAllViews();
		this.contentView = view;
		contentLayout.addView(view, params);
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	public void setTitle(String str) {
		if (!TextUtils.isEmpty(str)) {
			//    		setTopViewVisibility (true);
			//    		mTextViewTitle = (TextView) findViewById (android.R.id.title);
			//    		mTextViewTitle.setVisibility (View.GONE);
			//    		mTextViewTitle.setText (str);
		}
	}

	public void setTitle(int strRes) {
		if (strRes != -1) {
			setTitle(getContext().getString(strRes));
		}
	}

	public void setIcon(int res) {
		//        setTopViewVisibility (true);
		//        mImageViewIcon = (ImageView) findViewById (android.R.id.icon);
		//        mImageViewIcon.setVisibility (View.VISIBLE);
		//        mImageViewIcon.setImageResource (res);
	}

	// public void setButton1Enabled(boolean enabled) {
	// setBottomViewVisibility(true);
	// if (mButton1 == null)
	// mButton1 = (Button) findViewById(android.R.id.button1);
	// mButton1.setEnabled(enabled);
	// mButton1.setBackgroundResource(R.drawable.button_disable);
	// }
	//
	// public void setButton2Enabled(boolean enabled) {
	// setBottomViewVisibility(true);
	// if (mButton2 == null)
	// mButton2 = (Button) findViewById(android.R.id.button1);
	// mButton2.setEnabled(enabled);
	// mButton2.setBackgroundResource(R.drawable.button_disable);
	// }
	//
	// public void setButton3Enabled(boolean enabled) {
	// setBottomViewVisibility(true);
	// if (mButton3 == null)
	// mButton3 = (Button) findViewById(android.R.id.button1);
	// mButton3.setEnabled(enabled);
	// mButton3.setBackgroundResource(R.drawable.button_disable);
	// }

	public void hideButton1() {
		if (mButton1 == null) {
			mButton1 = (Button) findViewById(android.R.id.button1);
		}
		findViewById(R.id.mzw_dialog_divider).setVisibility(View.GONE);
		mButton1.setVisibility(View.GONE);
	}

	public void hideButton2() {
		if (mButton2 == null) {
			mButton2 = (Button) findViewById(android.R.id.button2);
		}
		findViewById(R.id.mzw_dialog_divider).setVisibility(View.GONE);
		mButton2.setVisibility(View.GONE);
	}

	public void showButton1() {
		if (mButton1 == null) {
			mButton1 = (Button) findViewById(android.R.id.button1);
		}
		findViewById(R.id.mzw_dialog_divider).setVisibility(View.VISIBLE);
		mButton1.setVisibility(View.VISIBLE);
	}

	public void showButton2() {
		if (mButton2 == null) {
			mButton2 = (Button) findViewById(android.R.id.button2);
		}
		findViewById(R.id.mzw_dialog_divider).setVisibility(View.VISIBLE);
		mButton2.setVisibility(View.VISIBLE);
	}

	/** 显示在右侧 (一般是确定) **/
	public void setButton1(String str, View.OnClickListener listener) {
		setBottomViewVisibility(true);
		if (mButton1 == null) {
			mButton1 = (Button) findViewById(android.R.id.button1);
		}
		if (str != null) {
			mButton1.setText(str);
		}
		mButton1.setVisibility(View.VISIBLE);
		if (listener != null) {
			mButton1.setOnClickListener(listener);
		}
	}

	/** 显示在右侧 (一般是确定) **/
	public void setButton1(int strRes, View.OnClickListener listener) {
		setButton1(getContext().getString(strRes), listener);
	}

	/** 显示在左侧 (一般是取消) **/
	public void setButton2(String str, View.OnClickListener listener) {
		setBottomViewVisibility(true);
		if (mButton2 == null) {
			mButton2 = (Button) findViewById(android.R.id.button2);
		}
		if (str != null) {
			mButton2.setText(str);
		}
		mButton2.setVisibility(View.VISIBLE);
		if (listener != null) {
			mButton2.setOnClickListener(listener);
		}
	}

	/** 显示在左侧 (一般是取消) **/
	public void setButton2(int strRes, View.OnClickListener listener) {
		setButton2(getContext().getString(strRes), listener);
	}

	public void setButton3(String str, View.OnClickListener listener) {
		setBottomViewVisibility(true);
		mButton3 = (Button) findViewById(android.R.id.button3);
		View view = findViewById(R.id.mzw_dialog_button3_divider);
		if (str != null)
			mButton3.setText(str);
		mButton3.setVisibility(View.VISIBLE);
		view.setVisibility(View.VISIBLE);
		if (listener != null)
			mButton3.setOnClickListener(listener);
	}

	public void setButton3Visibility(int visibility) {
		if (mButton3 != null) {
			mButton3.setVisibility(visibility);
			View view = findViewById(R.id.mzw_dialog_button3_divider);
			view.setVisibility(visibility);
		}

		/*    	mButton3 = (Button) findViewById (android.R.id.button3);
		    	View view =  findViewById (R.id.mzw_dialog_button3_divider);
		    	if (str != null) mButton3.setText (str);
		    	mButton3.setVisibility (View.VISIBLE);
		    	view.setVisibility (View.VISIBLE);
		    	if (listener != null) mButton3.setOnClickListener (listener);*/
	}

	public void setButton3(int strRes, View.OnClickListener listener) {
		setButton3(getContext().getString(strRes), listener);
	}

	public void setTopViewVisibility(boolean visible) {
		//        if (mLayoutTop == null) mLayoutTop = findViewById (R.id.relativelayout_dialog_titlelayout);
		//        mLayoutTop.setVisibility ((visible ? View.VISIBLE : View.GONE));
	}

	private void setMiddleViewVisibility(boolean visible) {

		if (mLayoutMiddle == null)
			mLayoutMiddle = findViewById(R.id.relativelayout_dialog_infolayout);
		mLayoutMiddle.setVisibility((visible ? View.VISIBLE : View.GONE));
	}

	public void setBottomViewVisibility(boolean visible) {

		if (mLayoutBottom == null)
			mLayoutBottom = findViewById(R.id.linearlayout_dialog_bottomlayout);
		mLayoutBottom.setVisibility((visible ? View.VISIBLE : View.GONE));
	}

	public View getmLayoutBottom() {
		if (mLayoutBottom == null) {
			mLayoutBottom = findViewById(R.id.linearlayout_dialog_bottomlayout);
		}
		return mLayoutBottom;
	}

	/**
	 * 在dialog被show后如果有对dialog进行修改， 调用refreshDialogView()
	 */
	public void refreshDialogView() {
		mDialogView = (ViewGroup) findViewById(R.id.relativelayout_dialog_view);
	}

	public Button getmButton1() {
		return mButton1;
	}

	public Button getmButton2() {
		return mButton2;
	}

	public Button getmButton3() {
		return mButton3;
	}

	// 当用户从最小化状态回来后让其强制刷新UI
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (mDialogView != null)
			setContentView(mDialogView);
	}

	public void setCallBack(CallBackforBackKey callBack) {
		this.callBack = callBack;
	}

	public CallBackforBackKey getCallBack() {
		return callBack;
	}

	/** back键按下后回调 */
	public static interface CallBackforBackKey {

		void extraOperate();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (callBack != null) {
				callBack.extraOperate();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 *
	 *<p>改变dialog的大小</p><br/>
	 * @since 1.0.0
	 * @author xky
	 * @param width -1:不做任何改变
	 * @param height -1:不做任何改变
	 */
	public void changeSize(int width, int height) {
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		if (width != -1) {
			params.width = width;
		}
		if (height != -1) {
			params.height = height;
		}
		window.setGravity(Gravity.BOTTOM);
		window.setAttributes(params);
	}

	public void setDialogAnim(int animStyle) {
		Window window = getWindow();
		window.setWindowAnimations(animStyle);
	}

	View mFocusView = null;

	public void setFocusView(View view) {
		mFocusView = view;
	}

	@Override
	public void dismiss() {
		if (mFocusView != null) {
			DeviceUtils.hideIme(mContext, mFocusView);
		}
		super.dismiss();
	}

}
