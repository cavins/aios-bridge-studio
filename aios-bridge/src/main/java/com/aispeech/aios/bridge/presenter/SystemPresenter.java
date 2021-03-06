package com.aispeech.aios.bridge.presenter;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.utils.ShellCmd;
import com.aispeech.aios.bridge.utils.SystemDefaultUtil;
import com.aispeech.aios.common.property.SystemProperty;
import com.aispeech.aios.sdk.listener.AIOSSystemListener;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class SystemPresenter implements AIOSSystemListener {
    private static SystemPresenter systemPresenter;
    private Timer timer;
    private BrightTimeOutTimer brightDrawbleTimer;

    public static synchronized SystemPresenter getInstance() {

        if (systemPresenter == null) {

            systemPresenter = new SystemPresenter();
        }
        return systemPresenter;
    }

    public void regisSystemListener() {
        //注册系统监听器
        AIOSSystemManager.getInstance().registerSystemListener(this);
    }

    public void unRegisSystemListener() {
        AIOSSystemManager.getInstance().unregisterSystemListener();
    }
    @Nullable
    @Override
    public String onSetVolume(String changeType) {
        Log.d("ljwtest:", "bridge音量控制:" + changeType);
        String text;
        if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_RAISE)) {
            SystemDefaultUtil.getInstance().setVolumeUp();
            text = "音量已增大";
        } else if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_LOWER)) {
            SystemDefaultUtil.getInstance().setVolumeDown();
            text = "音量已减小";
        } else if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_MAX)) {
            SystemDefaultUtil.getInstance().setMaxVolume();
            text = "音量已经调到最大";
        } else if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_MIN)) {
            SystemDefaultUtil.getInstance().setMinVolume();
            text = "音量已经调到最小";
        } else if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_MUTE)) {
            SystemDefaultUtil.getInstance().setMuteVolume();
            text = "媒体音已静音";
        } else if (changeType.equals(SystemProperty.VolumeProperty.VOLUME_UNMUTE)) {
            SystemDefaultUtil.getInstance().setUnMuteVolume();
            text = "取消静音";
        } else {
            text = "暂不支持此功能";

        }
        return text;
    }

    @Nullable
    @Override
    public String onSetBrightness(String changeType) {
        String text;
        try{
            if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_RAISE)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessUp();
                text = "亮度已增加";
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.displaybrightess"));
//                startTimerAfterBrightDisplay();
            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_LOWER)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessDown();
                text = "亮度已减小";
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.displaybrightess"));
            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_MAX)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessMax();
                text = "亮度已调到最大";
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.displaybrightess"));
            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_MIN)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessMin();
                text = "亮度已调到最小";
                BridgeApplication.getContext().sendBroadcast(new Intent("com.aios.displaybrightess"));
            } else {
                text = "暂不支持此功能";

            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "暂不支持此功能";
    }

    @Nullable
    @Override
    public String onOpenState(String openName) {
        String text;
        if (openName.equals(SystemProperty.CommonStateProperty.STATE_BLUETOOTH)) {
            text = "蓝牙已打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_WIFI)) {
            SystemDefaultUtil.getInstance().setWIFIState(true);
            text = "WIFI已打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_HOT)) {
            SystemDefaultUtil.getInstance().setHotSpotState(true);
            text = "热点已打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_SCREEN)) {
            SystemDefaultUtil.getInstance().openScreen();
            text = "屏幕打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_EDOG)) {
            text = "电子狗已打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_DRIVING_RECORDER)) {
            text = "行车记录仪已打开";

        } else if (openName.equals(SystemProperty.CommonStateProperty.STATE_TIRE_PRESSURE)) {
            text = "胎压已打开";

        } else {
            text = "暂不支持此功能";

        }
        return text;    }

    @Nullable
    @Override
    public String onCloseState(String closeName) {
        String text;
        if (closeName.equals(SystemProperty.CommonStateProperty.STATE_BLUETOOTH)) {
            text = "蓝牙已关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_WIFI)) {
            SystemDefaultUtil.getInstance().setWIFIState(false);
            text = "WIFI已关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_HOT)) {
            SystemDefaultUtil.getInstance().setHotSpotState(false);
            text = "热点已关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_SCREEN)) {
            SystemDefaultUtil.getInstance().closeScreen();
            text = "屏幕关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_EDOG)) {
            text = "电子狗已关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_DRIVING_RECORDER)) {
            text = "行车记录仪已关闭";

        } else if (closeName.equals(SystemProperty.CommonStateProperty.STATE_TIRE_PRESSURE)) {
            text = "胎压已关闭";

        } else {
            text = "暂不支持此功能";

        }
        return text;
    }

    /**
     * 开启闹钟计时
     */
    private void startTimerAfterBrightDisplay() {
        brightDrawbleTimer = new BrightTimeOutTimer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.i("ljwtest:", "闹钟响啦");
//            }
//        }, 1000, 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Log.i("ljwtest:", "闹钟响啦");
                    ShellCmd.execCommand("input tap 100 200", true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 闹钟到点提醒
     */
    class BrightTimeOutTimer extends TimerTask {

        @Override
        public void run() {
            Log.i("ljwtest:", "闹钟响啦");
            if(timer != null && brightDrawbleTimer != null)
                brightDrawbleTimer.cancel();
        }
    }

    @Nullable
    @Override
    public String onSnapShot() {
        return null;
    }

    @Override
    public void onOpenApp(@NonNull String packageName) {

    }

    @Override
    public void onCloseApp(@NonNull String packageName) {

    }
}
