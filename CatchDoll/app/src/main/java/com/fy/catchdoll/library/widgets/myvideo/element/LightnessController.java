package com.fy.catchdoll.library.widgets.myvideo.element;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings.System;
import android.widget.Toast;

public class LightnessController {
	
	// 判断是否开启了自动亮度调节
	public static boolean isAutoBrightness(Activity act) {
		boolean automicBrightness = false;
		ContentResolver aContentResolver = act.getContentResolver();
		try {
			automicBrightness = System.getInt(aContentResolver,
					System.SCREEN_BRIGHTNESS_MODE) == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		} catch (Exception e) {
			Toast.makeText(act, "无法获取亮度", Toast.LENGTH_SHORT).show();
		}
		return automicBrightness;
	}

	// 改变亮度
//	public static void setLightness(Activity act, int value,VideoElementController controller) {
//		try {
//			if(isAutoBrightness(act)){
//				stopAutoBrightness(act);
//			}
//			int currentLight = getLightness(act);
//			currentLight	=	currentLight + value;
//			if(currentLight > 255){
//				currentLight = 250;
//			}
//			if(currentLight <20){
//				currentLight = 40;
//			}
////			Log.e("test", "value ="+value+"   "+getLightness(act));
//			System.putInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, currentLight);
//			WindowManager.LayoutParams lp = act.getWindow().getAttributes();
//		float currentScrrenLight =	lp.screenBrightness + value/255f;
//		if(currentScrrenLight <0){
//			currentScrrenLight = 0.1f;
//		}
//		if(currentScrrenLight >1){
//			currentScrrenLight = 1;
//		}
//		int c1 = (int) (currentScrrenLight*100);
//		controller.show(ElementType.VIDEO_LIGTH,c1 ,100);
//			lp.screenBrightness =currentScrrenLight;
//			act.getWindow().setAttributes(lp);
//		} catch (Exception e) {
////			Toast.makeText(act, "无法改变亮度", Toast.LENGTH_SHORT).show();
//		}
//	}

	// 获取亮度
	public static int getLightness(Activity act) {
		return System.getInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, -1);
	}

	// 停止自动亮度调节
	public static void stopAutoBrightness(Activity activity) {
		System.putInt(activity.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE,
				System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	// 开启亮度自动调节
	public static void startAutoBrightness(Activity activity) {
		System.putInt(activity.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE,
				System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}
}
