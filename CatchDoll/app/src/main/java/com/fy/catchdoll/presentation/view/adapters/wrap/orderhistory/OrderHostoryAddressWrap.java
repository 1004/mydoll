package com.fy.catchdoll.presentation.view.adapters.wrap.orderhistory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.AddressInfo;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class OrderHostoryAddressWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_history_address);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.receiverName = (TextView) convertView.findViewById(R.id.box_address_receive_tv);
        viewHolder.receivePhone  = (TextView) convertView.findViewById(R.id.box_address_receive_phone);
        viewHolder.receiveAddress  = (TextView) convertView.findViewById(R.id.box_address_receive_address);

        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof AddressInfo)){
            return;
        }
        final AddressInfo addressInfo = (AddressInfo) baseItemDto;
        holder.receiverName.setText(mActivity.getResources().getString(R.string.string_address_name,addressInfo.getName()));
        holder.receivePhone.setText(mActivity.getResources().getString(R.string.string_address_phone,addressInfo.getPhone()));
        holder.receiveAddress.setText(mActivity.getResources().getString(R.string.string_address_address,addressInfo.getAddress()));
    }

    class ViewHolder{
        public TextView receiverName;
        public TextView receivePhone;
        public TextView receiveAddress;
    }
}
