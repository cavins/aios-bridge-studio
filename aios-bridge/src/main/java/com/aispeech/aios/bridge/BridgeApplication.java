package com.aispeech.aios.bridge;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.common.Common;
import com.aispeech.aios.bridge.listener.BridgeAudioListener;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.utils.PreferenceUtil;
import com.aispeech.aios.common.bean.MajorWakeup;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.listener.AIOSCustomizeListener;
import com.aispeech.aios.sdk.listener.AIOSReadyListener;
import com.aispeech.aios.sdk.manager.AIOSAudioManager;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Application类
 */
public class BridgeApplication extends Application {
    private static final String TAG = "Bridge-BridgeApplication";
    private static Context mContext;

    public static Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("Unknown Error");
        }
        return mContext;
    }

    public static BridgeApplication getApplication() {
        if (mContext == null) {
            throw new RuntimeException("Unknown Error");
        }
        return (BridgeApplication) mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        AIOSForCarSDK.initialize(this, new AIOSReadyListener() {
            @Override
            public void onAIOSReady() {
                AILog.d(TAG, "On aios ready...");
                //使原生快捷唤醒词生效
                AIOSCustomizeManager.getInstance().setNativeShortcutWakeupsEnabled(true);
                //定制录音机
                AIOSCustomizeManager.getInstance().customizeRecorder(
                        PreferenceUtil.getBoolean(mContext, PreferenceUtil.IS_AEC_ENABLED, false),
                        PreferenceUtil.getBoolean(mContext, PreferenceUtil.IS_INTERRUPT_ENABLED, false), false
                );

                //定制主唤醒词
                //定制唤醒词
                CustomizeWakeUpWordsPresenter.getInstance().loadingWakeUpWords();
//                List<MajorWakeup> majorWakeups = new ArrayList<MajorWakeup>();
//                majorWakeups.add(new MajorWakeup("你好小驰", "ni hao xiao chi", 0.13f));
//                AIOSCustomizeManager.getInstance().setMajorWakeup(majorWakeups);

                //定制悬浮窗为全屏
                WindowManager.LayoutParams layoutParams = AIOSUIManager.getInstance().obtainLayoutParams();
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                layoutParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
                layoutParams.alpha = 0.8f;
                AIOSUIManager.getInstance().setLayoutParams(layoutParams);

                //设置音频管理监听器，请实现或者使用以下监听器
                AIOSAudioManager.getInstance().registerAudioListener(new BridgeAudioListener());
                //如果静态注册了自定义命令，请注册以下监听器
                AIOSCustomizeManager.getInstance().registerCustomizeListener(customizeListener);
                //如果对接普通应用（音乐、导航、电话），且不需要AIOS主动拉起该应用，请将应用设置为非守护应用，代码如下:
                //AIOSSystemManager.getInstance().setDaemonEnabled(false);

                //初始化按键唤醒语音控制值
                Common.isAIOSOpen = false;
            }
        });
    }

    AIOSCustomizeListener customizeListener = new AIOSCustomizeListener() {
        @Override
        public void onCommandEffect(@NonNull String command) {
            AILog.i(TAG, "Command effect: " + command);
        }

        @Override
        public void onShortcutWakeUp(@NonNull String pinyin) {
            AILog.i(TAG, "Short cut wakeup:" + pinyin);
        }

        @Override
        public void onCommandSuccess(List<Command> cmdList) {
            AILog.i(TAG, "onCommandSuccess");
        }
    };
}
