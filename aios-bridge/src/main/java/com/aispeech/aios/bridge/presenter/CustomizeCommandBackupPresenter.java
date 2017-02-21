package com.aispeech.aios.bridge.presenter;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.bridge.common.BtPhoneOrder;
import com.aispeech.aios.bridge.common.FMOrder;
import com.aispeech.aios.bridge.utils.APPUtil;
import com.aispeech.aios.bridge.utils.ShellCmd;
import com.aispeech.aios.bridge.utils.SystemDefaultUtil;
import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.bean.ShortcutWakeup;
import com.aispeech.aios.sdk.listener.AIOSCustomizeListener;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.ArrayList;
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
            Log.e("john", "-----aios command----" + command);
            if (CustomizeCommandsPresenter.OPEN_FM.equals(command)) {//打开fm
//                AIOSTTSManager.speak("为您打开fm");
//                APPUtil.getInstance().openApplication(AppPackageName.FM_APP);
                BridgeApplication.getContext().sendBroadcast(new Intent(FMOrder.FM_ON));
            } else if (CustomizeCommandsPresenter.CLOSE_FM.equals(command)) {//关闭fm
                BridgeApplication.getContext().sendBroadcast(new Intent(FMOrder.FM_OFF));
            } else if (CustomizeCommandsPresenter.OPEN_EDOG.equals(command)) {
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
            } else if(CustomizeCommandsPresenter.SET_VOLUME_1.equals(command)) {
                AIOSTTSManager.speak("音量已经调到一");
                SystemDefaultUtil.getInstance().setVolumeLevel(11);
            } else if(CustomizeCommandsPresenter.SET_VOLUME_2.equals(command)) {
                AIOSTTSManager.speak("音量已经调到二");
                SystemDefaultUtil.getInstance().setVolumeLevel(12);
            } else if(CustomizeCommandsPresenter.SET_VOLUME_3.equals(command)) {
                AIOSTTSManager.speak("音量已经调到三");
                SystemDefaultUtil.getInstance().setVolumeLevel(13);
            } else if(CustomizeCommandsPresenter.SET_VOLUME_4.equals(command)) {
                AIOSTTSManager.speak("音量已经调到四");
                SystemDefaultUtil.getInstance().setVolumeLevel(14);
            } else if(CustomizeCommandsPresenter.SET_VOLUME_5.equals(command)) {
                AIOSTTSManager.speak("音量已经调到五");
                SystemDefaultUtil.getInstance().setVolumeLevel(15);
            } else if(CustomizeCommandsPresenter.SET_BRIGHTNESS_1.equals(command)) {
                AIOSTTSManager.speak("亮度已经调到一");
                try {
                    SystemDefaultUtil.getInstance().setScreenBrightnessLevel(1);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(CustomizeCommandsPresenter.SET_BRIGHTNESS_2.equals(command)) {
                AIOSTTSManager.speak("亮度已经调到二");
                try {
                    SystemDefaultUtil.getInstance().setScreenBrightnessLevel(2);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(CustomizeCommandsPresenter.SET_BRIGHTNESS_3.equals(command)) {
                AIOSTTSManager.speak("亮度已经调到三");
                try {
                    SystemDefaultUtil.getInstance().setScreenBrightnessLevel(3);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(CustomizeCommandsPresenter.SET_BRIGHTNESS_4.equals(command)) {
                AIOSTTSManager.speak("亮度已经调到四");
                try {
                    SystemDefaultUtil.getInstance().setScreenBrightnessLevel(4);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(CustomizeCommandsPresenter.SET_BRIGHTNESS_5.equals(command)) {
                AIOSTTSManager.speak("亮度已经调到五");
                try {
                    SystemDefaultUtil.getInstance().setScreenBrightnessLevel(5);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
            } else if(CustomizeCommandsPresenter.CURRENT_VOLUME.equals(command)) {
                int currentVolume = SystemDefaultUtil.getInstance().getCurrentVolume();
                AIOSTTSManager.speak("当前音量" + currentVolume + "级");
            } else if(CustomizeCommandsPresenter.CURRENT_BRIGHTNESS.equals(command)) {
                int currentBrightness = SystemDefaultUtil.getInstance().getCurrentBrightness();
                AIOSTTSManager.speak("当前亮度" + currentBrightness + "级");
            } else if(CustomizeCommandsPresenter.EXIT_AND_FINISH_MAP.equals(command)) {
                APPUtil.getInstance().closeApplication(AppPackageName.BAIDUMAP_APP);
                APPUtil.getInstance().closeApplication(AppPackageName.GAODEMAP_APPLITE);
            } else if(CustomizeCommandsPresenter.OPEN_WECHAT.equals(command)) {
                AIOSTTSManager.speak("对不起，暂不支持此功能");
            }
        }

        /**
         * 当识别到定制的快捷唤醒词时执行
         *
         * @param pinyin 识别的快捷唤醒拼音
         */
        @Override
        public void onShortcutWakeUp(@NonNull String pinyin) {
            Log.i("ljwtest:", "pinyin" + pinyin);
            if (CustomizeCommandsPresenter.BT_LOAD_CONTACT_1.equals(pinyin) ||
//            if (
                    CustomizeCommandsPresenter.BT_LOAD_CONTACT_2.equals(pinyin)) {
                AIOSTTSManager.speak("联系人正在导入中");
                BridgeApplication.getContext().sendBroadcast(new Intent(BtPhoneOrder.AIOS_LOAD_CONTACT));
            } else if (CustomizeCommandsPresenter.BT_CANCEL_CONTACT_1.endsWith(pinyin)
                    || CustomizeCommandsPresenter.BT_CANCEL_CONTACT_2.endsWith(pinyin)
                    || CustomizeCommandsPresenter.BT_CANCEL_CONTACT_3.endsWith(pinyin)
                    || CustomizeCommandsPresenter.BT_CANCEL_CONTACT_4.endsWith(pinyin)
                    ) {
                AIOSTTSManager.speak("联系人未导入");
                BridgeApplication.getContext().sendBroadcast(new Intent(BtPhoneOrder.AIOS_DISLOAD_CONTACT));
            }
//            else if(CustomizeCommandsPresenter.TUI_CHU_DAO_HANG.equals(pinyin) || CustomizeCommandsPresenter.GUAN_BI_DAO_HANG.equals(pinyin)
//                    || CustomizeCommandsPresenter.JIE_SHU_DAO_HANG.equals(pinyin) || CustomizeCommandsPresenter.QU_XIAO_DAO_HANG.equals(pinyin)
//                    || CustomizeCommandsPresenter.TING_ZHI_DAO_HANG.equals(pinyin)) {
//
//            }
            Log.e("JohnLog", "Short cut wakeup:" + pinyin);
            //定制快捷唤醒词
            List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
            shortcutWakeupList.add(new ShortcutWakeup("chao yang", 0.99f));
            AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
        }
//        else if(CustomizeCommandsPresenter.OPEN_HOME_QUICKSET.equals(pinyin)) {
//                AIOSTTSManager.speak("已为您打开快捷设置");
//                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.openquickset"));
//            } else if(CustomizeCommandsPresenter.CLOSE_HOME_QUICKSET.equals(pinyin)) {
//                AIOSTTSManager.speak("已为您关闭快捷设置");
//                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.closequickset"));
//            } else if("da kai che men ".equals(pinyin))
//                AIOSTTSManager.speak("已为您打开车门");
//              else if("guan bi che men".equals(pinyin))
//                AIOSTTSManager.speak("已为您关闭车门");
//        }

        @Override
        public void onCommandSuccess(List<Command> cmdList) {

        }
    };
}
