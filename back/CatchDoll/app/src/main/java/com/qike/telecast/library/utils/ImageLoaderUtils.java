package com.qike.telecast.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qike.telecast.R;
import com.qike.telecast.library.utils.tool.DisplayUtil;
import com.qike.telecast.library.widgets.FlexibleRoundedBitmapDisplayer;
import com.qike.telecast.presentation.application.CdApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderUtils {
    public static DisplayImageOptions img_detail_show = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.drawable_default_color).showImageOnFail(R.drawable.drawable_default_color)
            .delayBeforeLoading(100).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new FadeInBitmapDisplayer(500))
            .build();

    public static DisplayImageOptions mNoFadeDisplayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.drawable_default_color).showImageOnFail(R.drawable.drawable_default_color)
            .delayBeforeLoading(100).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT).bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions Gameoptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.drawable_write_color).showStubImage(R.drawable.drawable_default_color)
            .showImageForEmptyUri(R.drawable.drawable_write_color)//url为空时显示的图片
            .showImageOnFail(R.drawable.drawable_write_color)//加载失败显示的图片
            .cacheInMemory()//内存缓存
            .cacheOnDisc()//磁盘缓存
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .displayer(new FlexibleRoundedBitmapDisplayer(Device.dip2px(CdApplication.getApplication(), 15), FlexibleRoundedBitmapDisplayer.CORNER_TOP_LEFT | FlexibleRoundedBitmapDisplayer.CORNER_TOP_RIGHT)) // 自定义增强型BitmapDisplayer
            .build();

    public static DisplayImageOptions mSpecialOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.drawable_write_color).showStubImage(R.drawable.drawable_default_color)
            .showImageForEmptyUri(R.drawable.drawable_write_color)//url为空时显示的图片
            .showImageOnFail(R.drawable.drawable_write_color)//加载失败显示的图片
            .cacheInMemory()//内存缓存
            .cacheOnDisc()//磁盘缓存
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .displayer(new FlexibleRoundedBitmapDisplayer(Device.dip2px(CdApplication.getApplication(), DisplayUtil.dip2px(CdApplication.getApplication(),7)))) // 自定义增强型BitmapDisplayer
            .build();

    public static void realLoadImg(final ImageView iv, final int defaultId, int w, int h, String pic) {
        if (!TextUtils.isEmpty(pic)) {
//            iv.setTag(pic);
//            iv.setMaxWidth(w);
//            iv.setMaxHeight(h);
//            DisplayImageOptions img_detail_show2 = new DisplayImageOptions.Builder().showImageOnLoading(defaultId)
//                    .showImageOnFail(R.drawable.drawable_default_color).delayBeforeLoading(100).cacheInMemory(true)
//                    .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
////                    .displayer(new FadeInBitmapDisplayer(500))
//                    .build();
//
//            ImageAware imageAware = new ImageViewAware(iv, true);
            ImageLoader.getInstance().displayImage(pic, iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    public static void realLoadImg(final ImageView iv, final int defaultId, String pic) {
        if (!TextUtils.isEmpty(pic)) {
            ImageLoader.getInstance().displayImage(pic, iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    public static void realLoadImg(Context mContext, ImageView iv, int defaultId, String pic) {
        if (!TextUtils.isEmpty(pic)) {
            Glide.with(mContext)
                    .load(pic)
                    .placeholder(defaultId)
                    .crossFade()
                    .into(iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    public static void realLoadImg2(final ImageView iv, final int defaultId, int w, int h, String pic) {
        if (!TextUtils.isEmpty(pic)) {
//            iv.setTag(pic);
//            iv.setMaxWidth(w);
//            iv.setMaxHeight(h);
//            DisplayImageOptions img_detail_show2 = new DisplayImageOptions.Builder().showImageOnLoading(defaultId)
//                    .showImageOnFail(R.drawable.drawable_default_color).delayBeforeLoading(100).cacheInMemory(true)
//                    .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                    .bitmapConfig(Bitmap.Config.RGB_565).build();
//
//            ImageAware imageAware = new ImageViewAware(iv, true);
            ImageLoader.getInstance().displayImage(pic, iv);

        } else {
            iv.setImageResource(defaultId);
        }
    }

    public static void realLoadImage(final ImageView iv, final int defaultId, String pic) {
        if (!TextUtils.isEmpty(pic)) {
            ImageLoader.getInstance().displayImage(pic, iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    public static void setRoundedImage(String url, ImageView imageView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, Gameoptions);
    }

    public static void setRoundedImage(String url, ImageView imageView,DisplayImageOptions options) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, imageView, options);
    }


    /**
     * 加载图片，失败或加载中时显示指定图片
     *
     * @author yb
     */
    public static void displayImage(final ImageView iv, final int defaultId, String pic) {
        if (!TextUtils.isEmpty(pic)) {
            ImageLoader.getInstance().displayImage(pic, iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    /**
     * 加载图片，没有渐现效果
     */
    public static void loadImgWithNoFade(final ImageView iv, String pic) {
        if (!TextUtils.isEmpty(pic) && iv != null) {
            ImageLoader.getInstance().displayImage(pic, iv, mNoFadeDisplayImageOptions);
        }
    }

    public static void loadImgWithNoFade(String pic, final ImageView iv) {
        if (!TextUtils.isEmpty(pic) && iv != null) {
            ImageLoader.getInstance().displayImage(pic, iv, mNoFadeDisplayImageOptions);
        }
    }

    //加载图片，没有渐现效果
//    public static void loadImgWithNoFadeByYb(final ImageView iv, String pic) {
//        DisplayImageOptions mNoFadeDisplayImageOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .showImageOnFail(R.drawable.tele_cache_title2)
//                .bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的质量
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .build();
//        try {
//            ImageLoader.getInstance().loadImage(pic, mNoFadeDisplayImageOptions, new SimpleImageLoadingListener() {
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    try {
//                        iv.setImageBitmap(loadedImage);
//                    } catch (Exception e) {
//                        iv.setImageResource(R.drawable.tele_cache_title2);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            iv.setImageResource(R.drawable.tele_cache_title2);
//        }
//    }

    public static void displayImageWithNoCache(final ImageView iv, final int defaultId, String pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)   //设置图片不缓存于内存中
                .cacheOnDisc(false)
                .bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的质量
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)    //设置图片的缩放类型，该方法可以有效减少内存的占用
                .build();
        ImageLoader.getInstance().displayImage(pic, iv, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                try {
                    if(iv != null){
                        iv.setImageResource(defaultId);
                    }
                }catch (Exception e){

                }
                //Toast.makeText(iv.getContext(), "图片过大,加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    /**
     * 以ARGB_8888格式加载图片
     * 不缓存在内存中
     *
     * @param pic
     * @param listener
     */
    public static void loadRealFulImage(String pic, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888) //设置图片的质量
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) //设置图片的缩放类型，该方法可以有效减少内存的占用
                .build();
        ImageLoader.getInstance().loadImage(pic, options, listener);
    }
}
