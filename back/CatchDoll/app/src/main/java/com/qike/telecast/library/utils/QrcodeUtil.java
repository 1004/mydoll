package com.qike.telecast.library.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;


import java.io.File;
import java.util.HashMap;

/**
 * 二维码操作工具类
 * Created by yb on 2017/4/24.
 */
public class QrcodeUtil {
    private static int bgColor = 0xffffffff;//背景颜色
    private static int color = 0xff000000;//二维码颜色
    public static final String TAG = "QrcodeUtil";

//    public static boolean createQrcode(String savePath, String parseString, int width, int height, Bitmap logo) {
//        File file = new File(savePath);
//        if (file.isFile() && file.exists()) {
//            return true;
//        }
//        try {
//            Bitmap bitmap = createQrcode(parseString, width, height, logo);
//            if (bitmap != null) {
//                BitmapUtils.saveFile(bitmap, file.getAbsolutePath(), Bitmap.CompressFormat.PNG, 100);
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            PushLogUtils.i(TAG, "create Qrcode Fail");
//            return false;
//        }
//        return true;
//    }

//    public static Bitmap createQrcode(String parseString, int width, int height, Bitmap logo) {
//        if (TextUtils.isEmpty(parseString) || width <= 0 || height <= 0) {
//            return null;
//        }
//        try {
//            //配置参数
//            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//容错级别
//            hints.put(EncodeHintType.MARGIN, 0);//白边宽度
//            //图像数据转换使用了矩阵转换
//            BitMatrix bitMatrix = new MultiFormatWriter().encode(parseString, BarcodeFormat.QR_CODE, width, height, hints);
//            int[] pixels = new int[width * height];
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    if (bitMatrix.get(x, y)) {
//                        pixels[y * width + x] = color;
//                    } else {
//                        pixels[y * width + x] = bgColor;
//                    }
//                }
//            }
//            //生成二维码的格式,使用ARGB_8888
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//            if (logo != null) {
//                bitmap = addLogo(bitmap, logo);
//            }
//            return bitmap;
//        } catch (Exception e) {
//            PushLogUtils.i(TAG, "create Qrcode Fail");
//            return null;
//        }
//    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }
}
