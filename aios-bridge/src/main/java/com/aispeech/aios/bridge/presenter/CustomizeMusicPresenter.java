package com.aispeech.aios.bridge.presenter;

import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.bridge.listener.BridgeMusicListener;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSMusicManager;

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
        AIOSMusicManager.getInstance().registerMusicListener(new BridgeMusicListener());
        AIOSMusicManager.getInstance().setLocalMusicInfo("酷我音乐", AppPackageName.KUWO_APP);
    }

    public void unRegisCustomMusic() {
        AIOSMusicManager.getInstance().unregisterMusicListener();
    }
}
