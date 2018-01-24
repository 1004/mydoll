package com.qike.telecast.presentation.view.adapters.wrap.orderhistory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.box.SendState;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by wst on 2017/12/2.
 */
public class SentStateWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_history_state);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mTimeTv = (TextView) convertView.findViewById(R.id.send_order_time);
        viewHolder.mStateTv = (TextView) convertView.findViewById(R.id.send_order_state);
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof SendState)){
            return;
        }
        SendState sendState = (SendState) baseItemDto;
        holder.mStateTv.setText(mActivity.getResources().getString(R.string.string_send_state_str,sendState.getStatus()));
        holder.mTimeTv.setText(mActivity.getResources().getString(R.string.string_send_time_str,sendState.getCreated_at()));
    }

    class ViewHolder{
        public TextView mTimeTv;
        public TextView mStateTv;

    }

}
