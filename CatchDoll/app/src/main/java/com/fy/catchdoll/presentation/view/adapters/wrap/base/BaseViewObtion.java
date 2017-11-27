package com.fy.catchdoll.presentation.view.adapters.wrap.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fy.catchdoll.presentation.application.CdApplication;


/**
 * 
 *<p>最基本的itemView</p><br/>
 *<p>TODO (类的详细的功能描述)</p>
 * @since 1.0.0
 * @author xky
 */
public abstract class BaseViewObtion<T> {
	private OnWrapItemClickListener mClickListener;
	private OnWrapItemClickListener mPresenterClickListener;
	protected Activity mActivity;

	public BaseViewObtion() {
		
	}
	public void setOnWrapItemClickListener(OnWrapItemClickListener clickListener){
		mClickListener = clickListener;	
	}
	public void setPresenterOnWrapItemClickListener(OnWrapItemClickListener clickListener){
		mPresenterClickListener = clickListener;
	}

	protected void onItemElementClick(View v,Object ...objects){
		if(mClickListener != null){
			mClickListener.onItemClick(v,objects);
		}
		if (mPresenterClickListener != null){
			mPresenterClickListener.onItemClick(v,objects);
		}
	}
	
	public void setOnActivity(Activity activity) {
		mActivity = activity;
	}
	
	public Activity getActivity() {
		return mActivity;
	}
	public Context getContext(){
		if(mActivity != null){
			return  mActivity;
		}else {
			return CdApplication.getApplication();
		}
	}
	public View getView(int layoutid) {
		if (getActivity() == null){
			return LayoutInflater.from(CdApplication.getApplication()).inflate(layoutid,null);
		}else{
			return getActivity().getLayoutInflater().inflate(layoutid, null);
		}
	}
	/**
	 * 
	 *<p>创建view</p><br/>
	 *<p>TODO(详细描述)</p>
	 * @since 1.0.0
	 * @author xky
	 * @param t
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View createView(T t, int position, View convertView, ViewGroup parent);

	/**
	 * 
	 *<p>更新view中的数据</p><br/>
	 *<p>TODO(详细描述)</p>
	 * @since 1.0.0
	 * @author xky
	 * @param t
	 * @param position
	 * @param convertView
	 */
	public abstract void updateView(T t, int position, View convertView);


	
	
}
