package com.qike.telecast.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/27.
 */
public class BitmapUtils {
    public static long getBitmapSize(String imgPath) {
        long result = 0;
        if (!TextUtils.isEmpty(imgPath)) {
            File file = new File(imgPath);
            if (file.exists() && file.isFile()) {
                result = file.length();
            }
        }
        PushLogUtils.i("BitmapUtils", "getBitmapSize imgPath bitmap size : " + result);
        return result;
    }

    public static long getBitmapSize(Bitmap bitmap) {
        long result = 0;
        if (bitmap != null) {
            result = bitmap.getRowBytes() * bitmap.getHeight();
        }
        PushLogUtils.i("BitmapUtils", "getBitmapSize Bitmap bitmap size : " + result);
        return result;
    }

    public static int[] getBitmapWidthAndHeight(String imgPath) {
        int[] result = new int[2];
        if (TextUtils.isEmpty(imgPath)) {
            return result;
        }
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;//只读边,不读内容
            BitmapFactory.decodeFile(imgPath, newOpts);
            newOpts.inJustDecodeBounds = false;
            result[0] = newOpts.outWidth;
            result[1] = newOpts.outHeight;
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 像素压缩法
     *
     * @param imgPath
     * @return
     */
    //像素压缩法
    public static Bitmap compressBitmapByPixel(String imgPath, int maxWidth, int maxHeight) {
        if (TextUtils.isEmpty(imgPath) || maxWidth <= 0 || maxHeight <= 0) {
            return null;
        }
        Bitmap bitmap = null;
        int[] wh = getBitmapWidthAndHeight(imgPath);
        int width = wh[0];
        int height = wh[1];
        PushLogUtils.i("BitmapUtils", "maxWidth:" + maxWidth + " maxHeight " + maxHeight + "  width " + wh[0] + " height " + wh[1]);
        if (width <= 0 || height <= 0) {
            return null;
        }
        int be = 1;
        if (width >= height && width > maxWidth) {//缩放比,用高或者宽其中较大的一个数据进行计算
            if (width % maxWidth >= 5) {
                be = (int) (width / maxWidth);
                be++;
            } else {
                be = (int) (width / maxWidth);
            }
        } else if (width < height && height > maxHeight) {
            if (height % maxHeight >= 5) {
                be = (int) (height / maxHeight);
                be++;
            } else {
                be = (int) (height / maxHeight);
            }
        }
        PushLogUtils.i("BitmapUtils", "be:" + be);
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        return bitmap;
    }

    /**
     * 质量压缩法
     *
     * @param bitmap
     * @return
     */
    //质量压缩法
    public static Bitmap compressBitmapByQuality(Bitmap bitmap, int maxSize) {
        if (bitmap == null || maxSize <= 0) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
            while (baos.toByteArray().length > maxSize) {//循环判断如果压缩后图片是否大于指定大小,大于继续压缩
                PushLogUtils.i("BitmapUtils", "compressBitmapByQuality bitmap size : " + baos.toByteArray().length);
                baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                options -= 5;//图片质量每次减少5
                if (options <= 5) options = 5;//如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                if (options == 5) break;//如果图片的质量已降到最低则，不再进行压缩
            }
        } catch (Exception e) {
            return null;
        }
        return bitmap;
    }

    public static void saveFile(Bitmap bm, String fileName,Bitmap.CompressFormat format,int quality) throws IOException {
        File myCaptureFile = new File(fileName);
        File parentFile = myCaptureFile.getParentFile();
        if(parentFile != null && !parentFile.exists()){
            parentFile.mkdirs();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(format, quality, bos);
        bos.flush();
        bos.close();
    }
}
