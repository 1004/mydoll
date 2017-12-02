package com.fy.catchdoll.presentation.view.adapters.wrap.recharge;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

/**
 * Created by wst on 2017/12/2.
 */
public class RechargeImgWrap extends BaseViewObtion<BaseItemDto> {
    @Override
    public View createView(BaseItemDto baseItemDto, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_recharge_img);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.dollIcon = (ImageView) convertView.findViewById(R.id.recharge_recommand_img);

        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto baseItemDto, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (!(baseItemDto instanceof BoxDoll)){
            return;
        }
    }

    class ViewHolder{
        public ImageView dollIcon;
    }
}
