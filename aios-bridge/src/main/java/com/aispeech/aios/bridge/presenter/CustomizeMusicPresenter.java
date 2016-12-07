package com.aispeech.aios.bridge.presenter;

import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.bridge.listener.BridgeMusicListener;
import com.aispeech.aios.common.config.SDKApi;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.SDKNode;
import com.aispeech.aios.sdk.bean.Music;
import com.aispeech.aios.sdk.bean.SearchMusic;
import com.aispeech.aios.sdk.listener.AIOSMusicListener;
import com.aispeech.aios.sdk.manager.AIOSMusicManager;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class CustomizeMusicPresenter {
    private static CustomizeMusicPresenter customizeMusicPresenter;

    public static synchronized CustomizeMusicPresenter getInstance() {

        if (customizeMusicPresenter == null) {

            customizeMusicPresenter = new CustomizeMusicPresenter();
        }
        return customizeMusicPresenter;
    }

    public void loadingCustomMusicApp() {
//        AIOSMusicManager.getInstance().setLocalMusicInfo("酷我音乐", AppPackageName.KUWO_APP);
//        AIOSMusicManager.getInstance().registerMusicListener(new BridgeMusicListener());
        SDKNode.getInstance().publishSticky(SDKApi.MusicApi.LOCAL_MUSIC_CLEAN);
        AIOSMusicManager.getInstance().setDisplayListEnabled(true);
    }

    public void unRegisCustomMusic() {
        AIOSMusicManager.getInstance().unregisterMusicListener();
    }
}
