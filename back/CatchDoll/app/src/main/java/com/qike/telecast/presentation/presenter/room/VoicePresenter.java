package com.qike.telecast.presentation.presenter.room;

import android.app.Activity;
import android.media.MediaPlayer;

import com.qike.telecast.R;

/**
 * Created by xky on 2017/11/30 0030.
 */
public class VoicePresenter {
    private MediaPlayer mBgPlayer ;

    private Activity mActivity;

    public VoicePresenter(Activity activity){
        this.mActivity = activity;
    }

    /**
     * 开始播放背景音乐
     */
    public void startPlayBgVoice(){
       try {
           if (mBgPlayer == null){
               mBgPlayer = MediaPlayer.create(mActivity, R.raw.bgm02);
           }
           mBgPlayer.setLooping(true);
           mBgPlayer.start();
       }catch (Throwable e){

       }
    }


    public void pausePlayBgVoice(){
        try {
            if (mBgPlayer != null){
                if (mBgPlayer.isPlaying()){
                    mBgPlayer.pause();
                }
            }
        }catch (Throwable e){

        }
    }

    public void endPlayBgVoice(){
        try {
            if (mBgPlayer != null) {
                if (mBgPlayer.isPlaying()){
                    mBgPlayer.stop();
                }
                mBgPlayer.reset();
                mBgPlayer.release();
                mBgPlayer = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
