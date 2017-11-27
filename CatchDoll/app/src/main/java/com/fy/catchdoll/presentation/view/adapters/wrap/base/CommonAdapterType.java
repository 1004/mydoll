package com.fy.catchdoll.presentation.view.adapters.wrap.base;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapterType<T extends BaseItemDto> extends BaseAdapter {

	protected SparseArray<BaseViewObtion<T>> mViewObtains = new SparseArray<BaseViewObtion<T>>();
	protected List<T> mBeans;
	protected int MTYPESIZEMAX = 10;
	Activity mActivity;

	public CommonAdapterType(Activity activity) {
		mBeans = new ArrayList<T>();
		mActivity = activity;
	}

	public void addViewObtains(int type, BaseViewObtion<T> obt) {
		mViewObtains.put(type, obt);
		obt.setOnActivity(mActivity);
	}

	public List<T> getList() {
		return mBeans;
	}

	/**
	 * 设置列表数据
	 * @param beans
	 */
	public void setList(List<T> beans) {
		mBeans = beans;
	}

	/**
	 * 添加列表
	 * @param beans
	 */
	public void addList(List<T> beans) {
		if (mBeans == null)
			mBeans = new ArrayList<T>();
		mBeans.addAll(beans);
	}
	
	public void addBean(T t){
		if(mBeans != null){
			mBeans.add(t);
		}
	}

	/**
	 * 
	 *<p>移除</p><br/>
	 *<p>TODO(详细描述)</p>
	 * @since 1.0.0
	 * @author xky
	 * @param t
	 */
	public void remove(T t) {
		mBeans.remove(t);
	}

	public int getPosition(T t) {
		return mBeans.indexOf(t);
	}

	/**
	 * 
	 *<p>清空</p><br/>
	 *<p>TODO(详细描述)</p>
	 * @since 1.0.0
	 * @author xky
	 */
	public void clear() {
		if (mBeans != null) {
			mBeans.clear();
		}
	}

	@Override
	public int getCount() {
		return mBeans == null ? 0 : mBeans.size();
	}

	@Override
	public T getItem(int position) {
		if (mBeans == null || mBeans.size() == 0) {
			return null;
		}
		return mBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseViewObtion<T> obtain = mViewObtains.get(getItemViewType(position));
		if (convertView == null) {
			convertView = mViewObtains.get(getItemViewType(position))
					.createView(getItem(position), position, convertView, parent);
		}
		obtain.updateView(getItem(position), position, convertView);
		return convertView;

	}

	@Override
	public int getViewTypeCount() {
		return MTYPESIZEMAX;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).getItemType();
	}

}
