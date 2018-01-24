package com.qike.telecast.presentation.view.adapters.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.qike.telecast.R;
import com.qike.telecast.library.utils.ImageLoaderUtils;
import com.qike.telecast.library.widgets.banner.CustomRotationBannerView2;
import com.qike.telecast.presentation.model.dto.doll.banner.BannerInfo;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>处理banner图的adapter</p><br/>
 * <p>无线轮训</p><br/>
 *
 * @author xky
 * @since 1.0.0
 */
public class RotationBannerViewPagerAdapter2 extends PagerAdapter {
    private List<BannerInfo> mBanners;
    List<SoftReference<View>> mCache = new ArrayList<SoftReference<View>>();
    private LayoutInflater mInflater;
    private CustomRotationBannerView2.IOnBannerItemClickListener2 mBannerListener;
    private Context mContext;

    public RotationBannerViewPagerAdapter2(List<BannerInfo> banners, Context context) {
        mBanners = (banners == null ? new ArrayList<BannerInfo>() : banners);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        clearCache();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View convertView = getCache();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tele_banner_content_layout, null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.bannerimg);
        if (mBanners.size() == 0) {
            return convertView;
        }
        final BannerInfo dto = mBanners.get(position % mBanners.size());//取出数据
//        ImageLoaderUtils.realLoadImg(img, R.drawable.drawable_default_color, img.getWidth(), img.getHeight(), dto.getPicture());
//        GlideUtils.load(mContext, dto.getImage(), img, R.drawable.drawable_default_color);
        ImageLoaderUtils.setRoundedImage(dto.getImage(),img,ImageLoaderUtils.mSpecialOptions);
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mBannerListener != null) {
                    mBannerListener.onBannerItemClick(dto);
                }
            }
        });
        convertView.setTag(position);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = container.findViewWithTag(position);
        putCache(view);
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    private void putCache(View view) {
        if (view == null) {
            return;
        }
        SoftReference<View> reference = new SoftReference<View>(view);
        mCache.add(reference);
    }

    private View getCache() {
        if (mCache.size() == 0) {
            return null;
        }
        SoftReference<View> reference = mCache.get(mCache.size() - 1);
        View convertView = null;
        if (reference != null) {
            convertView = reference.get();
            mCache.remove(mCache.size() - 1);
        }
        return convertView;
    }

    public void setOnBannerClickListener(CustomRotationBannerView2.IOnBannerItemClickListener2 bannerListener) {
        mBannerListener = bannerListener;
    }

    private void clearCache() {
        mCache.clear();
    }

    class ViewHolder {
        public ImageView img;
    }

}
