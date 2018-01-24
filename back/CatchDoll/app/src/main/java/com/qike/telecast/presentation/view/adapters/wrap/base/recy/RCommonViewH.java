package com.qike.telecast.presentation.view.adapters.wrap.base.recy;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;


/**
 * Created by xky on 2017/8/10 0010.
 */
public class RCommonViewH<T extends BaseViewObtion> extends RecyclerView.ViewHolder{
    private T obtion;

    public RCommonViewH(View itemView,T obtion) {
        super(itemView);
        this.obtion = obtion;
    }

    public T getObtion() {
        return obtion;
    }

}
