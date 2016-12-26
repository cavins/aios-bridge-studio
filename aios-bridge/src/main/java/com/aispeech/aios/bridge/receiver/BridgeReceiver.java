package com.aispeech.aios.bridge.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.activity.MainActivity;
import com.aispeech.aios.bridge.activity.MapActivity;
import com.aispeech.aios.bridge.activity.MusicActivity;
import com.aispeech.aios.bridge.activity.PhoneActivity;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.bridge.utils.APPUtil;
import com.aispeech.aios.common.config.SDKBroadcast;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

public class BridgeReceiver extends BroadcastReceiver {
    private static final String TAG = "Bridge - BridgeReceiver";
    private static final String AISPEECH_START_ACTION ="com.aispeech.aios.adapter.startlistening";
    private static final String AISPEECH_STOP_ACTION ="com.aispeech.aios.adapter.stoplistening";

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

        } else if("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Intent intent1 = new Intent(BridgeApplication.getContext(), MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(intent1);
        } else if("action_change_local_map".equals(action)) {
            String mapType = intent.getStringExtra("maptype");
            CustomizeMapsPresenter.getInstance().changeDefaultMap(mapType);
        } else if("com.conqueror.parkingMonitoring".equals(action)) {
            Log.e("ljwtest:", "进入停车监控");
            AIOSForCarSDK.disableAIOS();
        } else if("com.conqueror.CancelparkingMonitoring".equals(action)) {
            Log.e("ljwtest:", "退出停车监控");
            AIOSForCarSDK.enableAIOS();
        } else if("com.wanma.action.PLAY_TTS".equals(action)) {
            String edogText = intent.getStringExtra("content");
            Log.d("ljwtest:", "语音测试20161222" + edogText);
            if(!TextUtils.isEmpty(edogText))
                AIOSTTSManager.speak(edogText);
        } else if(AISPEECH_START_ACTION.equals(action)) {
            Log.e("ljwtest", "收到AISPEECH_START_ACTION按键广播");
            AIOSSystemManager.getInstance().startInteraction();
        } else if(AISPEECH_STOP_ACTION.equals(action)) {
            Log.e("ljwtest", "收到AISPEECH_STOP_ACTION按键广播");
            AIOSSystemManager.getInstance().stopInteraction();
        }
    }
}
