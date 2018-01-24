package com.qike.telecast.module.database;

import android.os.Environment;
import android.text.TextUtils;

import com.qike.telecast.library.utils.PreferencesUtils;
import com.qike.telecast.presentation.application.CdApplication;


public class DatabasePath {
	public static final  String EXENAL_GAME_DATABASE_PATH="catchdoll/database/";
	public static final String SAVE_PATH = "save_path"; // 保存地址
	public static String getDownloadSavePath() {
			String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			sdcardPath = sdcardPath.endsWith("/") ? sdcardPath : sdcardPath + "/";
			String tempDownloadPath = sdcardPath ;
			String downloadPath = PreferencesUtils.loadPrefString(CdApplication.getApplication(), SAVE_PATH,
					tempDownloadPath);
			downloadPath = TextUtils.isEmpty(downloadPath) ? tempDownloadPath : downloadPath;
			return downloadPath ;
		}
}

