package com.aispeech.aios.bridge.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.activity.MainActivity;
import com.aispeech.aios.bridge.activity.MapActivity;
import com.aispeech.aios.bridge.activity.MusicActivity;
import com.aispeech.aios.bridge.activity.PhoneActivity;
import com.aispeech.aios.bridge.common.Common;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.bridge.utils.AIOSCommon;
import com.aispeech.aios.bridge.utils.APPUtil;
import com.aispeech.aios.bridge.utils.SystemDefaultUtil;
import com.aispeech.aios.common.config.SDKBroadcast;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.Timer;
import java.util.TimerTask;

import service.MainService;

public class BridgeReceiver extends BroadcastReceiver {
    private static final String TAG = "Bridge - BridgeReceiver";
    private static final String AISPEECH_START_ACTION ="com.aispeech.aios.adapter.startlistening";
    private static final String AISPEECH_STOP_ACTION ="com.aispeech.aios.adapter.stoplistening";
    private Timer timer;

    /**
     * 语音启动的广播
     */
    String AIOS_START = "audiomanager.intent.action.AIOS_START";

    /**
     * 语音停止的广播
     */
    String AIOS_STOP = "audiomanager.intent.action.AIOS_STOP";

    @Override
    public void onReceive(Context context, Intent intent) {
        AILog.i(TAG, "onReceive - " + intent.getAction());

        String action = intent.getAction();
        if (SDKBroadcast.Action.BOOT_PHONE.equals(action)) {
            //模拟打开电话应用，并完成电话监听器的初始化
            Intent phoneIntent = new Intent(BridgeApplication.getContext(), PhoneActivity.class);
            phoneIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(phoneIntent);

        } else if (SDKBroadcast.Action.BOOT_MUSIC.equals(action)) {
            //模拟打开音乐应用，并完成音乐监听器的初始化
            Intent musicIntent = new Intent(BridgeApplication.getContext(), MusicActivity.class);
            musicIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(musicIntent);

        } else if (SDKBroadcast.Action.BOOT_MAP.equals(action)) {
            //模拟打开地图应用，并完成地图监听器的初始化
            Intent mapIntent = new Intent(BridgeApplication.getContext(), MapActivity.class);
            mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(mapIntent);

        }
        else if("com.conqueror.action.jw.CompleteBoot".equals(action)) {
            Intent intent1 = new Intent(BridgeApplication.getContext(), MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(intent1);
        }
        else if("action_change_local_map".equals(action)) {
            String mapType = intent.getStringExtra("maptype");
            CustomizeMapsPresenter.getInstance().changeDefaultMap(mapType);
        } else if("com.conqueror.acc.NotifyAIOSEnterParking".equals(action)) {
            Log.e("ljwtest:", "进入停车监控");
//            AIOSForCarSDK.disableAIOS();
            AIOSSystemManager.getInstance().setACCOff();
        } else if("com.conqueror.CancelparkingMonitoring".equals(action)) {
            Log.e("ljwtest:", "退出停车监控");
//            AIOSForCarSDK.enableAIOS();
            AIOSSystemManager.getInstance().setACCOn();
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if(!wifiManager.isWifiEnabled())
                wifiManager.setWifiEnabled(true);
        } else if("com.wanma.action.PLAY_TTS".equals(action)) {
            String edogText = intent.getStringExtra("content");
            if(!TextUtils.isEmpty(edogText))
                AIOSTTSManager.speak(edogText);
        } else if(AISPEECH_START_ACTION.equals(action)) {
            if(!Common.isAIOSOpen) {
                AIOSSystemManager.getInstance().startInteraction();
            } else {
                AIOSSystemManager.getInstance().stopInteraction();
            }
        }
//        else if(AISPEECH_STOP_ACTION.equals(action)) {
//            Log.e("ljwtest", "收到AISPEECH_STOP_ACTION按键广播");
//            if(APPUtil.getInstance().isAppIsInBackground("com.aispeech.aios"))
//                AIOSTTSManager.speak("语音退到后台");
//            AIOSSystemManager.getInstance().stopInteraction();
//        }
        else if(AIOS_START.equals(action)) {
            Common.isAIOSOpen = true;
        } else if(AIOS_STOP.equals(action)) {
            Common.isAIOSOpen = false;
        } else if("com.ljw.servicealivetest".equals(action)) { //测试服务是否运行
            boolean isServiceRun = APPUtil.getInstance().isServiceRun(MainService.class.getName());
            Log.d("ljwtestservice", "isServiceRun:" + MainService.class.getName() + ":" + isServiceRun);
        } else if("com.ljw.aiosalivetest".equals(action)) { //测试语音播报功能
            AIOSTTSManager.speak("语音播报正常");
        } else if("com.ljw.wakeupalivetest".equals(action)) { //测试唤醒功能
            AIOSSystemManager.getInstance().startInteraction();
        } else if("com.ljw.enableaiostest".equals(action)) { //直接使能语音
            AIOSForCarSDK.enableAIOS();
        }
        else if("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
        }
        else if("com.ljw.rayee.syncsystemvol".equals(action)) { //同步外部手动调节音量
            int volume = intent.getIntExtra("currentvolume", 999);
            SystemDefaultUtil.getInstance().syncSystemVol(volume);
        } else if("com.ljw.restartpresenter".equals(action)) {
            AIOSCommon.getInstance().restartPresenter();
        }
    }

//    private void startUpCount() { //finish after oncreate 5000ms later
//        timer = new Timer(true);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                finish();
//            }
//        }, 5000, 5000);
//    }
}
