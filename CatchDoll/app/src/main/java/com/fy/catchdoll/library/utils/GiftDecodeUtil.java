package com.fy.catchdoll.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xky on 16/4/1.
 */
public class GiftDecodeUtil {
    private static final Pattern EMOTION_URL = Pattern
            .compile("\\{\\:(\\S+?)\\:\\}");
    private static final int giftW = 14,giftH = 14;

    public static SpannableString decodeContanisGiftMsg(Context context,String colorStr,int colorId,String message){
        SpannableString value = SpannableString.valueOf(message);
        if (TextUtils.isEmpty(message)){
            return null;
        }
        value = operateColorTextStr(context,colorStr,message,colorId);
//        value = operateContainesImgMeg(context, message, value, giftW, giftH);
        return value;
    }

    public static SpannableString decodeContainsGiftMsg(Context context,String colorStr,int colorId,String message,String []url){
        SpannableString value = SpannableString.valueOf(message);
        if (TextUtils.isEmpty(message)){
            return null;
        }
        value = operateColorTextStr(context, colorStr, message, colorId);

        return value;
    }

    private static void operateContainesImgMeg(Context mContext,String message,SpannableString value,int w,int h,String []urls){

    }





//    public static SpannableString operateColorTextStr(Context context,String colorStr, String message,
//                                               int colorId) {
//        SpannableString value = SpannableString.valueOf(message);
//
//        if (!TextUtils.isEmpty(colorStr) && message.contains(colorStr)) {
//            String[] split = {};
//            try {
//                split = message.split(colorStr);
//            } catch (PatternSyntaxException e) {
//                if (message.startsWith(colorStr)){
////                    return getSpanStr(context,colorId,0,colorStr.length(),value,message);
//                    return value;
//                }else {
//                    return value;
//                }
//            }
//            if (split == null || split.length==0){
//                return value;
//            }
//            String str = split[0];
//            int start = 0;
//            int end = 0;
//            if (!TextUtils.isEmpty(str)) {
//                start = str.length();
//            }
//
//            end = start + colorStr.length();
//            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context
//                    .getResources().getColor(colorId));
//            if(end>value.length()){
//                return value;
//            }
//            try{
//                value.setSpan(colorSpan, start, end,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }catch(IndexOutOfBoundsException e){
//                return SpannableString.valueOf(message);
//            }
//
//        }
//        return value;
//    }
    public static SpannableString operateColorTextStr(Context context,String colorStr, String message,
                                                      int colorId) {
        SpannableString value = SpannableString.valueOf(message);

        if (!TextUtils.isEmpty(colorStr) && message.contains(colorStr)) {
            int location = message.indexOf(colorStr);
//            String[] split = {};
//            try {
//                split = message.split(colorStr);
//            } catch (PatternSyntaxException e) {
//                if (message.startsWith(colorStr)){
////                    return getSpanStr(context,colorId,0,colorStr.length(),value,message);
//                    return value;
//                }else {
//                    return value;
//                }
//            }
//            if (split == null || split.length==0){
//                return value;
//            }
//            String str = split[0];
            int start = location;
            int end = 0;

            end = start + colorStr.length();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context
                    .getResources().getColor(colorId));
            if(end>value.length()){
                return value;
            }
            try{
                value.setSpan(colorSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }catch(IndexOutOfBoundsException e){
                return SpannableString.valueOf(message);
            }

        }
        return value;
    }

    public static SpannableString operateMultiColorTextStr(Context context, String message,int colorId,String... colorStr) {
        SpannableString value = SpannableString.valueOf(message);
        if (colorStr == null || colorStr.length == 0){
            return value;
        }

        for(int i=0 ;i<colorStr.length ;i++){
            String mColorStr = colorStr[i];
            if (!TextUtils.isEmpty(mColorStr) && message.contains(mColorStr)) {
                int location = message.indexOf(mColorStr);
                int start = location;
                int end = 0;

                end = start + mColorStr.length();
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(context
                        .getResources().getColor(colorId));
                if(end>value.length()){
                    return value;
                }
                try{
                    value.setSpan(colorSpan, start, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }catch(IndexOutOfBoundsException e){
                    return SpannableString.valueOf(message);
                }

            }
        }

        return value;
    }


    private static SpannableString getSpanStr(Context context,int colorId,int start,int end,SpannableString value,String message){
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context
                .getResources().getColor(colorId));
        if(end>value.length()){
            return value;
        }
        try{
            value.setSpan(colorSpan,start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }catch(IndexOutOfBoundsException e){
            return SpannableString.valueOf(message);
        }
        return value;
    }


    /**
     * 压缩图片
     * @param rawMap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressImg(Bitmap rawMap, int width, int height) {
        if (rawMap != null) {
            int rawHeigh = rawMap.getHeight();
            int rawWidth = rawMap.getWidth();
            if (rawHeigh < height) {
                // rawHeigh = height;
                height = rawHeigh;
            }
            if (rawWidth < width) {
                // rawWidth = width;
                width = rawWidth;
            }
            // 计算缩放因子
            float heightScale = ((float) height) / rawHeigh;
            float widthScale = ((float) width) / rawWidth;
            // 新建立矩阵
            Matrix matrix = new Matrix();
            // 判断缩放比例
            float inSampleSize = widthScale < heightScale ? widthScale
                    : heightScale;

            matrix.postScale(inSampleSize, inSampleSize);
            Bitmap newBitmap = null;
            try {
                newBitmap = Bitmap.createBitmap(rawMap, 0, 0, rawWidth,
                        rawHeigh, matrix, true);
            } catch (Throwable e) {
                newBitmap = Bitmap.createBitmap(rawMap, 0, 0, width, height,
                        matrix, true);
            }
            return newBitmap;
        }
        return rawMap;
    }
    /**
     * 压缩图片
     * @param rawMap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressImg2(Bitmap rawMap, int width, int height) {
        if (rawMap != null) {
            if (width != -1 && height != -1){
                return compressImg(rawMap,width,height);
            }
            int rawHeigh = rawMap.getHeight();
            int rawWidth = rawMap.getWidth();
            // 计算缩放因子
            float heightScale = ((float) height) / rawHeigh;
            float widthScale = ((float) width) / rawWidth;

            if (width == -1){
                //高度固定，宽度自适应
                width = (int) (rawWidth*heightScale);
            }else if (height == -1){
                //宽度固定，高度自适应
                height = (int) (rawHeigh*widthScale);
            }

            return compressImg(rawMap,width,height);
        }
        return rawMap;
    }

    public static void MLog(String msg){
        PushLogUtils.i("item", msg);
    }
}
