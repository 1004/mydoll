package com.qike.telecast.presentation.view.adapters.wrap.box;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.box.AddressInfo;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by xky on 2017/11/28 0028.
 */
public class BoxAddressWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_box_address);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.updataTv = convertView.findViewById(R.id.box_address_update);
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
        holder.updataTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemElementClick(v,addressInfo);
            }
        });
    }

    class ViewHolder{
        public View updataTv;
        public TextView receiverName;
        public TextView receivePhone;
        public TextView receiveAddress;
    }
}
