package com.fy.catchdoll.presentation.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.config.Config;
import tv.feiyunlu.qike.com.qikecorelibrary.libs.libs.core.config.Configuration;

/**
 * Created by xky on 2017/11/10 0010.
 */
@Config(globalBasePath = "$sdcard/catchdoll", publicBasePath = "$global/$pkgname/", sign = 0)
public class CdApplication extends Application{
    private static Application mApp;
    public static String mCacheApkDir;


    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        coreInit();
        initImageLoader();
    }

    private void coreInit() {
        long time = System.currentTimeMillis();

        Configuration config = Configuration.getConfiguration();
        config.buildConfig(this);
        config.setCacheTTL(5 * 60 * 1000);
        mCacheApkDir = config.getPublicBasePath() + "/apk";
    }


    private void initImageLoader() {
        long time = System.currentTimeMillis();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(getImageDownloadConfig());
    }

    private ImageLoaderConfiguration getImageDownloadConfig() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration mInnerImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(10)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(defaultOptions)
                        // 2 Mb
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheSize(Integer.MAX_VALUE).discCacheFileCount(Integer.MAX_VALUE)
                        // 缓存到文件的最大数据
                .imageDownloader(DefaultConfigurationFactory.createImageDownloader(this))
                        // .memoryCache(new LruMemoryCache(2* 1024 * 1024))
                .memoryCache(new WeakMemoryCache()).memoryCacheSizePercentage(13).memoryCacheSize(2 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        return mInnerImageLoaderConfiguration;
    }

    private void initData() {
        mApp = this;
    }

    public static Context getApplication(){
        return mApp;
    }

}
