package com.aispeech.aios.bridge.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.activity.MainActivity;
import com.aispeech.aios.bridge.activity.MapActivity;
import com.aispeech.aios.bridge.activity.MusicActivity;
import com.aispeech.aios.bridge.activity.PhoneActivity;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.common.config.SDKBroadcast;

public class BridgeReceiver extends BroadcastReceiver {
    private static final String TAG = "Bridge - BridgeReceiver";

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
        }
    }
}
