package com.qike.telecast.presentation.view.adapters.wrap.base.recy;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;


import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xky on 2017/8/10 0010.
 */
public class RCommonAdapterType<T extends BaseItemDto>  extends RecyclerView.Adapter<RCommonViewH> {


    protected SparseArray<BaseViewObtion<T>> mViewObtains = new SparseArray<BaseViewObtion<T>>();
    protected List<T> mBeans;
    Activity mActivity;

    public RCommonAdapterType(Activity activity) {
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

    public int getCount() {
//        Log.i("viewHolder","getCount"+(mBeans == null ? 0 : mBeans.size()));
        return mBeans == null ? 0 : mBeans.size();
    }

    @Override
    public int getItemCount() {
        return mBeans == null ? 0 : mBeans.size();
    }

    public T getItem(int position) {
        if (mBeans == null || mBeans.size() == 0) {
            return null;
        }

        return mBeans.get(position);
    }


    @Override
    public RCommonViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewObtion<T> obtain = mViewObtains.get(viewType);
        RCommonViewH viewH = new RCommonViewH(obtain.createView(null,-1,null,parent),obtain);
//        Log.i("viewHolder","onCreateViewHolder");
        return viewH;
    }

    @Override
    public void onBindViewHolder(RCommonViewH holder, int position) {
//        Log.i("viewHolder","onBindViewHolder");
        if (holder.getObtion() != null){
            holder.getObtion().updateView(getItem(position), position, holder.itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemType();
    }

}
