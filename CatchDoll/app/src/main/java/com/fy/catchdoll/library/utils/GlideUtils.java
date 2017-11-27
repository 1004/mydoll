package com.fy.catchdoll.library.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.fy.catchdoll.presentation.application.CdApplication;

/**
 * Gilde操作类
 * Created by Yocn on 2017/5/10.
 */

public class GlideUtils {

    public static void load(Context context, String picUrl) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE);
        } catch (Throwable e) {
        }
    }

    public static void loadGifImg(Context context,int rid,ImageView imageView){
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(rid).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void loadGifImg(Context context,String rid,ImageView imageView){
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(rid).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void downloadGifSetImg(Context context,String url,ImageView imageView,RequestListener<String,GlideDrawable> listener){
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(listener).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(Context context, String picUrl, ImageView imageView) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(Context context, int ResId, ImageView imageView) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(ResId).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(Context context, String picUrl, ImageView imageView, int defaultRes) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(Context context, String picUrl, ImageView imageView, Transformation<Bitmap>... bitmapTransformations) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).bitmapTransform(bitmapTransformations).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(Context context, String picUrl, ImageView imageView, int defaultRes, Transformation<Bitmap>... bitmapTransformations) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).bitmapTransform(bitmapTransformations).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    /**
     * 不需要渐现动画
     *
     * @param context
     * @param picUrl
     * @param imageView
     */
    public static void loadWithoutFade(Context context, String picUrl, ImageView imageView) {
//        Glide.with(context).load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).placeholder(imageView.getDrawable()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        } catch (Throwable e) {
        }
    }

    /**
     * 加载图片，并指定加载时应该使用的图片
     * @param context
     * @param picUrl
     * @param defaultId
     * @param imageView
     */
    public static void loadWidthDefaultPic(Context context, String picUrl, int defaultId, ImageView imageView) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).placeholder(defaultId).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //https://github.com/bumptech/glide/issues/803#issuecomment-163528497
    //recommend
    public static void load(RequestManager manager, String picUrl) {
        try {
            manager.load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE);
        } catch (Throwable e) {
        }
    }

    public static void load(RequestManager manager, String picUrl, ImageView imageView) {
        try {
            manager.load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(RequestManager manager, int ResId, ImageView imageView) {
        try {
            manager.load(ResId).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(RequestManager manager, String picUrl, ImageView imageView, int defaultRes) {
        try {
            manager.load(picUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(RequestManager manager, String picUrl, ImageView imageView, Transformation<Bitmap>... bitmapTransformations) {
        try {
            manager.load(picUrl).bitmapTransform(bitmapTransformations).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void load(RequestManager manager, String picUrl, ImageView imageView, int defaultRes, Transformation<Bitmap>... bitmapTransformations) {
        try {
            manager.load(picUrl).bitmapTransform(bitmapTransformations).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        } catch (Throwable e) {
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 每次加载图片都用最新的不使用缓存中内容
     *
     * @param context    上下文
     * @param picUrl
     * @param imageView
     * @param defaultRes
     */
    //每次加载图片都用最新的不使用缓存中内容
    public static void loadSkipMemoryCache(Context context, Object picUrl, ImageView imageView, int defaultRes) {
        if (!isCanLoad(context)) context = CdApplication.getApplication();
        if (context == null) return;
        try {
            Glide.with(context).load(picUrl).crossFade(0).dontAnimate().placeholder(imageView.getDrawable()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        } catch (Throwable e) {
        }
    }

    public static void pauseAllRequest(Context context) {
        try {
            Glide.with(context).pauseRequests();
        } catch (Throwable e) {
        }
    }

    public static void resumeAllRequest(Context context) {
        try {
            Glide.with(context).resumeRequests();
        } catch (Throwable e) {
        }
    }

    public static boolean isRunOnUIThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /**
     * 是否可以执行load操作
     *
     * @param context 上下文
     * @return 是否可以继续load
     */
    private static boolean isCanLoad(Context context) {
        if (!isRunOnUIThread()) {
            return false;
        } else {
            if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {
                return true;
            } else if (context != null && context instanceof Application) {
                return true;
            } else {
                return false;

            }
        }
    }
}
