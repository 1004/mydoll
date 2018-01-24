package com.qike.telecast.presentation.view.adapters.wrap.index;

import android.view.View;
import android.view.ViewGroup;

import com.qike.telecast.R;
import com.qike.telecast.library.widgets.banner.CustomRotationBannerView2;
import com.qike.telecast.presentation.model.dto.base.BaseItemDto;
import com.qike.telecast.presentation.model.dto.doll.banner.BannerInfo;
import com.qike.telecast.presentation.model.dto.doll.banner.WrapBannerInfo;
import com.qike.telecast.presentation.view.adapters.wrap.base.BaseViewObtion;

import java.util.List;

/**
 * Created by xky on 2017/11/27 0027.
 */
public class BannerWrap extends BaseViewObtion<BaseItemDto> {
    @Override
    public View createView(BaseItemDto bannerInfo, int position, View convertView, ViewGroup parent) {
        convertView = getView(R.layout.wrap_banner_main);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.banner_view = (CustomRotationBannerView2) convertView.findViewById(R.id.banner_view);
        // 设置轮播图宽高比例 600x338 宽高这个尺寸
//        float ratio = 0.375f;
//        int w = Device.getScreenWidthAndHeight(getActivity())[0];
//        int h = (int) (w * ratio);
//        ViewGroup.LayoutParams para = viewHolder.banner_view.getLayoutParams();
//        para.width = w;// 修改宽度
//        para.height = h;
//        viewHolder.banner_view.setLayoutParams(para);
        viewHolder.banner_view.endTimer();
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void updateView(BaseItemDto bannerInfo, int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null || holder.banner_view == null || bannerInfo == null || !(bannerInfo instanceof WrapBannerInfo)) {
            convertView.setVisibility(View.GONE);
            return;
        }
        List<BannerInfo> mBannerList = ((WrapBannerInfo) bannerInfo).getBanner();
        if (mBannerList == null || mBannerList.size() <= 0) {
            convertView.setVisibility(View.GONE);
            return;
        }
        convertView.setVisibility(View.VISIBLE);
        if (mBannerList.size() == 1) {
            holder.banner_view.closeTraining();
        } else {
            holder.banner_view.openTraining();
        }
        holder.banner_view.setData(mBannerList);
        // 先设置数据，再设置点击监听，否则报错
        holder.banner_view.setOnBannerClickListener(new CustomRotationBannerView2.IOnBannerItemClickListener2() {

            @Override
            public void onBannerItemClick(BannerInfo dto) {
                onItemElementClick(null, dto);
            }
        });
    }

    class ViewHolder {
        CustomRotationBannerView2 banner_view;
    }
}
