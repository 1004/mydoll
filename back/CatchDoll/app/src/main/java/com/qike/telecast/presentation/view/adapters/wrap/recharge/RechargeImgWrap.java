package com.qike.telecast.presentation.view.adapters.wrap.recharge;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qike.telecast.R;
import com.qike.telecast.library.utils.ImageLoaderUtils;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.doll.banner.BannerInfo;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

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
        if (!(baseItemDto instanceof BannerInfo)){
            return;
        }

        BannerInfo info = (BannerInfo) baseItemDto;
        ImageLoaderUtils.displayImage(holder.dollIcon, R.drawable.drawable_default_color, info.getImage());

    }

    class ViewHolder{
        public ImageView dollIcon;
    }
}
