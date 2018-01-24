package com.qike.telecast.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * 向bitmap添加文字
 * Created by yb on 2017/5/25.
 */
public class DrawTextAndPicToBitmap extends AsyncTask<Object, Object, String> {
    public static final int BITMAP_MAX_SIZE = 102400;//最大100kb 压缩到的最大大小，单位B

    @Override
    protected String doInBackground(Object... params) {
        if (params == null || params.length < 13) {
            return null;
        }
        String from = null;
        //图片路径
        if (params[0] != null && params[0] instanceof String) {
            from = (String) params[0];
        } else {
            return null;
        }
        String to = null;
        //处理后的图片路径
        if (params[1] != null && params[1] instanceof String) {
            to = (String) params[1];
        } else {
            return null;
        }
        String text = null;
        //需要绘制的文字
        if (params[2] != null && params[2] instanceof String) {
            text = (String) params[2];
        } else {
            text = "";
        }
        String textColor = null;
        //需要绘制文字的颜色
        if (params[3] != null && params[3] instanceof String) {
            textColor = (String) params[3];
        } else {
            textColor = "#ffffff";
        }
        String bgColor = null;
        //需要绘制文字的背景颜色
        if (params[4] != null && params[4] instanceof String) {
            bgColor = (String) params[4];
        } else {
            bgColor = "";
        }
        int textSize = 35;
        //需要绘制文字的大小
        if (params[5] != null && params[5] instanceof Integer) {
            textSize = (int) params[5];
        }
        int x = 0;
        //需要绘制的文字x坐标
        if (params[6] != null && params[6] instanceof Integer) {
            x = (int) params[6];
        }
        int y = 0;
        //需要绘制的文字y坐标
        if (params[7] != null && params[7] instanceof Integer) {
            y = (int) params[7];
        }
        String qrPath = "";
        //需要绘制的二维码网址
        if (params[8] != null && params[8] instanceof String) {
            qrPath = (String) params[8];
        }
        int qrX = 0;
        //二维码x坐标
        if (params[9] != null && params[9] instanceof Integer) {
            qrX = (int) params[9];
        }
        int qrY = 0;
        //二维码y坐标
        if (params[10] != null && params[10] instanceof Integer) {
            qrY = (int) params[10];
        }
        int qrWidth = 0;
        //二维码宽度
        if (params[11] != null && params[11] instanceof Integer) {
            qrWidth = (int) params[11];
        }
        int qrHeight = 0;
        //二维码高度
        if (params[12] != null && params[12] instanceof Integer) {
            qrHeight = (int) params[12];
        }
        return drawTextToBitmap(from, to, text, textColor, bgColor, textSize, x, y, qrPath, qrX, qrY, qrWidth, qrHeight);
    }

    /**
     * 向bitmap上画字
     *
     * @param path
     * @param gText
     * @param textColor
     * @param textSize
     * @param x
     * @param y
     * @return
     */
    //向bitmap上画字画二维码
    public String drawTextToBitmap(String path, String savePath, String gText, String textColor, String bgColor, int textSize, int x, int y, String qrPath, int qrX, int qrY, int qrWidth, int qrHeight) {
        long time = System.currentTimeMillis();
        Bitmap bitmap = null;
        try {
            PushLogUtils.i("DrawTextAndPicToBitmap", "text : " + gText + " textColor: " + textColor + " textSize " + textSize + " x " + x + " y " + y);
            if (TextUtils.isEmpty(path) || (TextUtils.isEmpty(gText) && TextUtils.isEmpty(qrPath))) {
                return path;
            }
            if (TextUtils.isEmpty(textColor)) {
                textColor = "#ffffff";
            }
            if (textSize <= 0) {
                textSize = 35;
            }
            int[] wh = BitmapUtils.getBitmapWidthAndHeight(path);
            //获取图片
            bitmap = getBitmap(path);
            if (bitmap == null) {
                return null;
            }
            //等比例转换坐标
            int be = 1;
            int bitmapWidth = bitmap.getWidth();
            if (wh[0] != bitmapWidth) {
                if (wh[0] % bitmapWidth >= 5) {
                    be = wh[0] / bitmap.getWidth();
                    be++;
                } else {
                    be = wh[0] / bitmap.getWidth();
                }
            }
            textSize = textSize / be;
            x = x / be;
            y = y / be;
            qrX = qrX / be;
            qrY = qrY / be;
            qrWidth = qrWidth / be;
            qrHeight = qrHeight / be;
            PushLogUtils.i("DrawTextAndPicToBitmap", "result be: " + be + " textsize " + textSize + " x " + x + " y " + y);
            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }

            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            if (!TextUtils.isEmpty(gText)) {
                Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

                //画文字配置
                fontPaint.setColor(Color.parseColor(textColor));
                fontPaint.setTextSize(textSize);

                float textLegth = fontPaint.measureText(gText);//文字长度
                float textHeight = fontPaint.getFontMetrics().bottom - fontPaint.getFontMetrics().top;//文字高度
                float textBGPanding = (textHeight - textSize) / 2 + 10;//背景padding距离 暂定10

                //画文字背景配置
                if (!TextUtils.isEmpty(bgColor)) {
                    bgPaint.setStyle(Paint.Style.FILL);
                    bgPaint.setColor(Color.parseColor(bgColor));
                    bgPaint.setAntiAlias(true);
                    RectF rectF = new RectF(x, y, x + textLegth + textBGPanding * 2, y + textHeight);
                    //画背景
                    canvas.drawRoundRect(rectF, textHeight / 2, textHeight / 2, bgPaint);
                }
                //画文字
                canvas.drawText(gText, x + textBGPanding, y - fontPaint.getFontMetrics().top, fontPaint);
            }
            PushLogUtils.i("DrawTextAndPicToBitmap", "QR Path:" + qrPath);
            //画二维码
            if (!TextUtils.isEmpty(qrPath) && qrWidth > 0 && qrHeight > 0) {
//                Bitmap qrBitmap = QrcodeUtil.createQrcode(qrPath, qrWidth, qrHeight, null);
                Bitmap qrBitmap = null;
                if (qrBitmap == null) {
                    PushLogUtils.i("DrawTextAndPicToBitmap", "QR Bitmap is null");
                    return null;
                }
                canvas.drawBitmap(qrBitmap, qrX, qrY, null);
            }
            try {
                BitmapUtils.saveFile(bitmap, savePath, Bitmap.CompressFormat.JPEG, 100);
            } catch (Exception e) {
                e.printStackTrace();
                savePath = null;
            }
        } catch (Throwable error) {
            error.printStackTrace();
            savePath = null;
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        PushLogUtils.i("DrawTextAndPicToBitmap", "use time : " + (System.currentTimeMillis() - time));
        return savePath;
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        //像素压缩
        if (BitmapUtils.getBitmapSize(path) > BITMAP_MAX_SIZE && CommonConstant.screenWidth > 0 && CommonConstant.screenHeight > 0) {
            bitmap = BitmapUtils.compressBitmapByPixel(path, CommonConstant.screenWidth, CommonConstant.screenHeight);
        }
//        //质量压缩 并不会改变bitmap在内存中的大小，故废弃
//        if (BitmapUtils.getBitmapSize(bitmap) > BITMAP_MAX_SIZE) {
//            bitmap = BitmapUtils.compressBitmapByQuality(bitmap, BITMAP_MAX_SIZE);
//        }
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeFile(path);
        }
        return bitmap;
    }
}
