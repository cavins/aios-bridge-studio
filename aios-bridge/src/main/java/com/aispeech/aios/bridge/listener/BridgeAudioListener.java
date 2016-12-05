package com.aispeech.aios.bridge.listener;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.utils.BridgeAudioUtil;
import com.aispeech.aios.sdk.listener.AIOSAudioListener;

/**
 * 音频管理监听器实现类
 */
public class BridgeAudioListener implements AIOSAudioListener {

    /**
     * 执行静音操作，静音通道等参数请根据实际情况设置。AIOS需要静音时回调该方法
     */
    @Override
    public void onMute() {
        BridgeAudioUtil.setStreamMute(BridgeApplication.getContext(), true);
    }

    /**
     * 执行取消静音操作，静音通道等参数请根据实际情况设置。AIOS需要取消静音时回调该方法。
     */
    @Override
    public void onUnMute() {
        BridgeAudioUtil.setStreamMute(BridgeApplication.getContext(), false);
    }
}
