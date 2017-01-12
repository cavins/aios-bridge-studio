package com.aispeech.aios.bridge.utils;

import android.content.Context;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.presenter.CustomizeBlueToothPhonePresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandBackupPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeFMPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.presenter.SystemPresenter;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class AIOSCommon {

    private Context mContext;
    private static AIOSCommon aiosCommon;

    public AIOSCommon() {
        mContext = BridgeApplication.getContext();
    }

    public static synchronized AIOSCommon getInstance() {

        if (aiosCommon == null) {

            aiosCommon = new AIOSCommon();
        }
        return aiosCommon;
    }

    public void initPresenter() {
        ////定制启动提示语
        AIOSCustomizeManager.getInstance().setLaunchTips("语音已启动，您可以使用征仔你好或者你好征仔来唤醒");
        //定制主唤醒词
        CustomizeWakeUpWordsPresenter.getInstance().loadingWakeUpWords();
        //默认开启AEC
        AIOSCustomizeManager.getInstance().setAECEnabled(true);
        //初始化自定义命令
        CustomizeCommandsPresenter.getInstance().regisCommands();
        //初始化快捷命令
        CustomizeCommandsPresenter.getInstance().regisCustomizeShortcutListener();
        //初始化自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().regisCustomizeListener();
        //初始化系统命令
        SystemPresenter.getInstance().regisSystemListener();
        //初始化地图
//        CustomizeMapsPresenter.getInstance().loadingMap();
//        CustomizeMapsPresenter.getInstance().setDefaultMap();
        //初始化蓝牙
        CustomizeBlueToothPhonePresenter.getInstance().registCustomizeListener();

        //初始化fm
        CustomizeFMPresenter.getInstance().registCustomizeListener();
        //开启APP扫描功能，生成APP的打开与关闭/退出命令
        AIOSCustomizeManager.getInstance().setScanAppEnabled(true);
        //开启默认UI优先级
        AIOSUIManager.getInstance().setUIPriorityEnabled(true);
//        UIPriorityPresenter.getInstance().registerUIListener();
        //注销部分默认快捷命令
        cancelDefaultwords();
        //初始化音乐
//        CustomizeMusicPresenter.getInstance().loadingCustomMusicApp();
    }

    public void cancelDefaultwords() {
        AIOSCustomizeManager.getInstance().disableNativeShortcutWakeup(new String[]{"tui chu dao hang", "jie shu dao hang", "guan bi dao hang", "qv xiao dao hang", "ting zhi dao hang"});
    }

    public void unRegisPresenter() {
        //注销自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().unRegisCustomizeListener();
        //注销系统命令监听
        SystemPresenter.getInstance().unRegisSystemListener();
    }

    public void restartPresenter() {
        unRegisPresenter();
        initPresenter();
    }
}
