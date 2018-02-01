package com.fy.catchdoll.presentation.view.adapters.wrap.recharge;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fy.catchdoll.R;
import com.fy.catchdoll.library.utils.ImageLoaderUtils;
import com.fy.catchdoll.presentation.model.dto.base.BaseItemDto;
import com.fy.catchdoll.presentation.model.dto.box.BoxDoll;
import com.fy.catchdoll.presentation.model.dto.doll.banner.BannerInfo;
import com.fy.catchdoll.presentation.view.adapters.wrap.base.BaseViewObtion;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.base.datainterface.impl.ImageLoader;

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

        final BannerInfo info = (BannerInfo) baseItemDto;
        ImageLoaderUtils.displayImage(holder.dollIcon,R.drawable.drawable_default_color,info.getImage());
        holder.dollIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemElementClick(v,info);
            }
        });

    }

    class ViewHolder{
        public ImageView dollIcon;
    }
}
