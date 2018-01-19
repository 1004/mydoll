package com.fy.catchdoll.presentation.presenter.my;

import com.fy.catchdoll.library.utils.PreferencesUtils;
import com.fy.catchdoll.presentation.application.CdApplication;

/**
 * Created by xky on 2018/1/17 0017.
 */
public class SettingPresenter {
    public static final String BGMUSIC_KEY = "bgmusic_key";

    public static boolean getBgMusiceIsOpen(){
        return PreferencesUtils.loadPrefBoolean(CdApplication.getApplication(),BGMUSIC_KEY,true);
    }

    public static void setBgMusicIsOpen(boolean isOpen){
        PreferencesUtils.savePrefBoolean(CdApplication.getApplication(),BGMUSIC_KEY,isOpen);
    }
}
