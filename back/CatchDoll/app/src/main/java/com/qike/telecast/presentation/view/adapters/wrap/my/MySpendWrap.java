package com.qike.telecast.presentation.view.adapters.wrap.my;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.CommonUtil;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.my.MySpendDto;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class MySpendWrap extends BaseViewObtion<BaseItemDto>{
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_spend_item);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.spendTitle = (TextView) convertView.findViewById(R.id.spend_title);
        viewHolder.spendMoney = (TextView) convertView.findViewById(R.id.spend_gold);
        convertView.setTag(viewHolder);
        return convertView;    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof MySpendDto)){
            return;
        }
        MySpendDto dollBox = (MySpendDto) baseItemDto;
        holder.spendTitle.setText(dollBox.getRemarks());
        holder.spendMoney.setText(dollBox.getNumber()+" "+ CommonUtil.getMoneyUnit());
    }

    class ViewHolder{
        public TextView spendTitle;
        public TextView spendMoney;
    }
}
