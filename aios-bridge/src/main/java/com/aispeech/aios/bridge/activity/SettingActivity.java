package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.common.config.PkgName;
import com.aispeech.aios.common.property.SettingProperty;
import com.aispeech.aios.sdk.listener.AIOSSettingListener;
import com.aispeech.aios.sdk.manager.AIOSSettingManager;

/**
 * 设置模块示例类
 */
public class SettingActivity extends Activity {
    private static final String TAG = "Bridge-SettingActivity";
    private AIOSSettingListener settingListener = new AIOSSettingListener() {

        /**
         * 设置中的对话模式发生改变
         *
         * @param oldStyle 改变前的对话模式，具体含义请参考{@link SettingProperty.DialogueProperty  DialogueProperty}
         * @param newStyle 改变后的对话模式，具体含义请参考{@link SettingProperty.DialogueProperty  DialogueProperty}
         * @see SettingProperty.DialogueProperty#BUBBLE_STYLE
         * @see SettingProperty.DialogueProperty#CLASSICS_STYLE
         */
        @Override
        public void onDialogueChange(int oldStyle, int newStyle) {
            AILog.d(TAG,"对话模式变动，"+oldStyle+"->"+newStyle);
        }

        /**
         * 设置中的语音资源发生改变
         *
         * @param oldRes 改变前的语音资源，具体含义请参考{@link SettingProperty.TtsResProperty  TtsResProperty}
         * @param newRes 改变后的语音资源，具体含义请参考{@link SettingProperty.TtsResProperty  TtsResProperty}
         * @see SettingProperty.TtsResProperty#LIN_ZHILING
         * @see SettingProperty.TtsResProperty#GUO_DEGANG
         */
        @Override
        public void onTtsResChange(@NonNull String oldRes, @NonNull String newRes) {
            AILog.d(TAG,"语音资源变动，"+oldRes+"->"+newRes);
        }

        /**
         * 设置中的默认地图发生改变
         *
         * @param oldPkgName 改变前的地图主包名，AIOS原生支持的地图包名见{@link PkgName.MapPkgName MapPkgName}
         *                   类。
         * @param newPkgName 改变后的地图主包名，AIOS原生支持的地图包名见{@link PkgName.MapPkgName MapPkgName}
         */
        @Override
        public void onDefaultMapChange(@NonNull String oldPkgName, @NonNull String newPkgName) {
            AILog.d(TAG,"默认地图变动，"+oldPkgName+"->"+newPkgName);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //注册设置监听器
        AIOSSettingManager.getInstance().registerSettingListener(settingListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销设置监听器
        AIOSSettingManager.getInstance().unregisterSettingListener();
    }

    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_setting_dialogue:
                //设置对话模式
                AIOSSettingManager.getInstance().setDialogueStyle(SettingProperty.DialogueProperty.CLASSICS_STYLE);
                break;

            case R.id.btn_setting_tts:
                //设置语音资源
                AIOSSettingManager.getInstance().setTtsRes(SettingProperty.TtsResProperty.GUO_DEGANG);
                break;

            case R.id.btn_setting_map:
                //设置默认地图
                AIOSSettingManager.getInstance().setDefaultMap(PkgName.MapPkgName.AMAP);
                break;
            default:
                break;
        }
    }
}
