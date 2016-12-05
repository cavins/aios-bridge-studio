package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.utils.PreferenceUtil;
import com.aispeech.aios.bridge.utils.SystemDefaultUtil;
import com.aispeech.aios.common.property.SystemProperty;
import com.aispeech.aios.sdk.SDKNode;
import com.aispeech.aios.sdk.listener.AIOSSystemListener;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

/**
 * 系统接口示例类。见{@link SystemDefaultUtil}类，该类未实现的操作大部分依赖于具体的ROM，所以AIOS也未实现。
 */
public class SystemActivity extends Activity implements AIOSSystemListener {
    private TextView textInfo;
    private CheckBox accSwitch;
    private CheckBox recorderSwitch;
    private CheckBox voiceSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        textInfo = (TextView) findViewById(R.id.text_system_info);
        accSwitch = (CheckBox) findViewById(R.id.switch_acc);
        recorderSwitch = (CheckBox) findViewById(R.id.switch_recorder);
        voiceSwitch = (CheckBox) findViewById(R.id.switch_voice_wakeup);

        accSwitch.setChecked(true);
        recorderSwitch.setChecked(true);
        voiceSwitch.setChecked(PreferenceUtil.getBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_WAKEUP_SWITCH, true));
        accSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //谨记在使用该接口时，需要初始化AIOSForCarSDK
                if (isChecked) {
                    AIOSSystemManager.getInstance().setACCOn();
                } else {
                    AIOSSystemManager.getInstance().setACCOff();
                }
            }
        });

        recorderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //谨记在使用该接口时，需要初始化AIOSForCarSDK
                if (isChecked) {
                    AIOSSystemManager.getInstance().startRecorder();
                } else {
                    AIOSSystemManager.getInstance().stopRecorder();
                }
            }
        });

        voiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtil.setBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_WAKEUP_SWITCH, isChecked);
                AIOSSystemManager.getInstance().setVoiceWakeupEnabled(isChecked);
            }
        });

        //注册系统监听器
        AIOSSystemManager.getInstance().registerSystemListener(this);

        if (savedInstanceState != null) {
            textInfo.setText(savedInstanceState.getString("text_saved"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销系统监听器
        AIOSSystemManager.getInstance().unregisterSystemListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text_saved", textInfo.getText().toString());
    }

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
        textInfo.setText(text);
        return text;
    }

    @Override
    public String onSetBrightness(String changeType) {
        String text;
        try{
            if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_RAISE)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessUp();
                text = "亮度已增加";

            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_LOWER)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessDown();
                text = "亮度已减小";

            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_MAX)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessMax();
                text = "亮度已调到最大";

            } else if (changeType.equals(SystemProperty.BrightnessProperty.BRIGHTNESS_MIN)) {
                SystemDefaultUtil.getInstance().setScreenBrightnessMin();
                text = "亮度已调到最小";

            } else {
                text = "暂不支持此功能";

            }
            textInfo.setText(text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "暂不支持此功能";
    }

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
        textInfo.setText(text);
        return text;
    }

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
        textInfo.setText(text);
        return text;
    }

    @Override
    public String onSnapShot() {
        textInfo.setText("我要拍照");
        return "咔嚓";
    }

    /**
     * 打开指定应用
     *
     * @param packageName 应用包名
     */
    @Override
    public void onOpenApp(@NonNull String packageName) {
        AIOSTTSManager.speak("为您打开指定应用");
    }

    /**
     * 关闭指定应用
     *
     * @param packageName 应用包名
     */
    @Override
    public void onCloseApp(@NonNull String packageName) {
        AIOSTTSManager.speak("为您关闭指定应用");
    }
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_recorder_restart:
                //谨记在使用该接口时，需要初始化AIOSForCarSDK
                AIOSSystemManager.getInstance().restartRecorder();
                break;

            case R.id.btn_speak_long:
                //播报一段长文本
                AIOSTTSManager.speak("深圳，别称鹏城，广东省辖市，地处广东省南部，珠江三角洲东岸，与香港一水之隔，东临大亚湾和大鹏湾，西濒珠江口和伶仃洋，南隔深圳河与香港相连，北部与东莞、惠州接壤。" +
                        "深圳是中国改革开放建立的第一个经济特区，是中国改革开放的窗口，已发展为有一定影响力的国际化城市，创造了举世瞩目的“深圳速度”，同时享有“设计之都”、“钢琴之城”、“创客之城”等美誉。" +
                        "深圳市域边界设有中国最多的出入境口岸。深圳也是重要的边境口岸城市，皇岗口岸实施24小时通关。");
                break;
//            case R.id.btn_interrupt_speak:
            //打断播报，暂缓实现
            //AIOSSystemManager.getInstance().interruptTTS();
//                break;
            case R.id.btn_start_interaction:
                //手动唤醒AIOS
                AIOSSystemManager.getInstance().startInteraction();
                break;
            case R.id.btn_auth_state:
                //获取AIOS授权状态
                AIOSTTSManager.speak(AIOSSystemManager.getInstance().isAuthSucceeded()?"授权已通过":"授权失败");
                break;
            default:
                break;
        }
    }
}
