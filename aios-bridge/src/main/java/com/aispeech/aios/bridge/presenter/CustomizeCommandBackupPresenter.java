package com.aispeech.aios.bridge.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.bridge.utils.APPUtil;
import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.listener.AIOSCustomizeListener;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

/**
 * 自定义命令的反馈
 */
public class CustomizeCommandBackupPresenter {
    private static CustomizeCommandBackupPresenter customizeCommandBackupPresenter;

    public static synchronized CustomizeCommandBackupPresenter getInstance() {

        if (customizeCommandBackupPresenter == null) {

            customizeCommandBackupPresenter = new CustomizeCommandBackupPresenter();
        }
        return customizeCommandBackupPresenter;
    }

    public void regisCustomizeListener() {
        AIOSCustomizeManager.getInstance().registerCustomizeListener(customizeListener);
    }

    public void unRegisCustomizeListener() {
        AIOSCustomizeManager.getInstance().unregisterCustomizeListener();
    }

    /**
     * 自定义命令监听器
     **/
    private AIOSCustomizeListener customizeListener = new AIOSCustomizeListener() {

        /**
         * 执行注册的自定义命令时调用
         * @param command 自定义命令
         */
        @Override
        public void onCommandEffect(@NonNull String command) {
            Log.i("ljwtest:", "接收到的命令" + command);
            if (CustomizeCommandsPresenter.OPEN_FM.equals(command)) {
                AIOSTTSManager.speak("为您打开fm");
                APPUtil.getInstance().openApplication(AppPackageName.FM_APP);
            } else if(CustomizeCommandsPresenter.OPEN_EDOG.equals(command)) {
                AIOSTTSManager.speak("为您打开电子狗");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.wanma.action.EDOG_ON"));
            }else if(CustomizeCommandsPresenter.CLOSE_EDOG.equals(command)){
                AIOSTTSManager.speak("为您关闭电子狗");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.wanma.action.EDOG_OFF"));
            } else if(CustomizeCommandsPresenter.OPEN_RADAR.equals(command)) {
                AIOSTTSManager.speak("为您打开雷达");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.wanma.action.RADAR_ON"));
            } else if(CustomizeCommandsPresenter.CLOSE_RADAR.equals(command)) {
                AIOSTTSManager.speak("为您关闭雷达");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.wanma.action.RADAR_OFF"));
            } else if(CustomizeCommandsPresenter.OPEN_XIMALAYA.equals(command)) {
                AIOSTTSManager.speak("为您打开喜马拉雅FM");
                APPUtil.getInstance().openApplication(AppPackageName.FILE_XIMALAYA_APP);
            } else if(CustomizeCommandsPresenter.OPEN_BAIDUMAP.equals(command)) {
                AIOSTTSManager.speak("为您打开百度地图");
                APPUtil.getInstance().openApplication(AppPackageName.BAIDUMAP_APP);
            } else if(CustomizeCommandsPresenter.OPEN_GAODEMAP.equals(command)) {
                AIOSTTSManager.speak("为您打开高德地图");
                APPUtil.getInstance().openApplication(AppPackageName.GAODEMAP_APPLITE);
            } else if(CustomizeCommandsPresenter.SHUTDOWN.equals(command)) {
                AIOSTTSManager.speak("为您关机中");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.action.shutdown"));
            }else if(CustomizeCommandsPresenter.REBOOT.equals(command)) {
                AIOSTTSManager.speak("为您重启中");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.action.reboot"));
            } else if (CustomizeCommandsPresenter.OPEN_BT.equals(command)) {
                AIOSTTSManager.speak("为您打开蓝牙电话");
                APPUtil.getInstance().openApplication(AppPackageName.BLUETOOTH_APP);
            } else if (CustomizeCommandsPresenter.CLOSE_BT.equals(command)) {
                AIOSTTSManager.speak("为您关闭蓝牙电话");
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.action.closeBlueToothPhone"));
            }
        }

        /**
         * 当识别到定制的快捷唤醒词时执行
         *
         * @param pinyin 识别的快捷唤醒拼音
         */
        @Override
        public void onShortcutWakeUp(@NonNull String pinyin) {
            //TODO
        }

        @Override
        public void onCommandSuccess(List<Command> cmdList) {

        }
    };
}
