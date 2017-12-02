package com.fy.catchdoll.presentation.view.adapters.wrap.recharge;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.recharge.RechargeItem;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeItemWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_recharge_item);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.rechareCount = (TextView) convertView.findViewById(R.id.recharge_count);
        viewHolder.rechargeMoney = (TextView) convertView.findViewById(R.id.recharge_money);
        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof RechargeItem)){
            return;
        }
        RechargeItem dollBox = (RechargeItem) baseItemDto;
        holder.rechareCount.setText(dollBox.getNumber());
        holder.rechargeMoney.setText("￥"+dollBox.getPrice());
    }

    class ViewHolder{
        public TextView rechareCount;
        public TextView rechargeMoney;
    }
}
