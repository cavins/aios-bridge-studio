package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.utils.APPUtil;
import com.aispeech.aios.bridge.utils.PreferenceUtil;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.bean.Command;
import com.aispeech.aios.sdk.bean.ShortcutWakeup;
import com.aispeech.aios.sdk.listener.AIOSCustomizeListener;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义命令示例类
 */
public class CustomizeActivity extends Activity {
    private static final String TAG = "Bridge -CustomizeActivity";
    private static final String OPEN_AIR_CONDITION = "/condition/open";
    private static final String OPEN_FM = "/condition/openfm";
    private static final String OPEN_DOOR = "da kai che men";
    private static final String CLOSE_DOOR = "guan bi che men";

    private CheckBox aecSwitch;
    private CheckBox interruptSwitch;
    private CheckBox aiosSwitch;
    private CheckBox nativeShortcutSwitch;

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
            if (CustomizeCommandsPresenter.OPEN_FM.equals(command)) {
                AIOSTTSManager.speak("为您打开fm");
                APPUtil.getInstance().openApplication(AppPackageName.FM_APP);
            } else if(CustomizeCommandsPresenter.OPEN_EDOG.equals(command)) {
                AIOSTTSManager.speak("为您打开电子狗");
                APPUtil.getInstance().openApplication(AppPackageName.EDOG_APP);
            } else if(CustomizeCommandsPresenter.OPEN_RADAR.equals(command)) {
                AIOSTTSManager.speak("为您打开雷达");
            } else if(CustomizeCommandsPresenter.OPEN_XIMALAYA.equals(command)) {
                AIOSTTSManager.speak("为您打开喜马拉雅FM");
                APPUtil.getInstance().openApplication(AppPackageName.FILE_XIMALAYA_APP);
            } else if(CustomizeCommandsPresenter.OPEN_BAIDUMAP.equals(command)) {
                AIOSTTSManager.speak("为您打开百度地图");
                APPUtil.getInstance().openApplication(AppPackageName.BAIDUMAP_APP);
            } else if(CustomizeCommandsPresenter.OPEN_GAODEMAP.equals(command)) {
                AIOSTTSManager.speak("为您打开高德地图");
                APPUtil.getInstance().openApplication(AppPackageName.GAODEMAP_APPLITE);
            }
            AILog.i(TAG, "Command effect: "+command);
        }

        /**
         * 当识别到定制的快捷唤醒词时执行
         *
         * @param pinyin 识别的快捷唤醒拼音
         */
        @Override
        public void onShortcutWakeUp(@NonNull String pinyin) {
            if (OPEN_DOOR.equals(pinyin)) {
                AIOSTTSManager.speak("车门已打开");
            } else if (CLOSE_DOOR.endsWith(pinyin)) {
                AIOSTTSManager.speak("车门已关闭");
            }
            AILog.i(TAG, "Short cut wakeup:"+pinyin);
        }

        @Override
        public void onCommandSuccess(List<Command> cmdList) {
            AILog.i(TAG, "onCommandSuccess");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        //注册自定义命令监听器，以便在自定义的命令生效时作自己的处理
        AIOSCustomizeManager.getInstance().registerCustomizeListener(customizeListener);

        //清空AIOS保存的自定义命令缓存，请根据具体情况决定是否调用
        AIOSCustomizeManager.getInstance().cleanCommands();

        aiosSwitch = (CheckBox) findViewById(R.id.switch_aios);
        aecSwitch = (CheckBox) findViewById(R.id.switch_aec);
        interruptSwitch = (CheckBox) findViewById(R.id.switch_interrupt);
        nativeShortcutSwitch = (CheckBox) findViewById(R.id.switch_native_shortcut);
        aecSwitch.setChecked(PreferenceUtil.getBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_AEC_ENABLED, false));
        interruptSwitch.setChecked(PreferenceUtil.getBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_INTERRUPT_ENABLED, false));
        nativeShortcutSwitch.setChecked(PreferenceUtil.getBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_NATIVE_SHORTCUT_ENABLE, true));

        aiosSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //是否启用AIOS
                PreferenceUtil.setBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_AIOS_SWITCH, isChecked);
                if(isChecked){
                    //off -> on
                    AIOSForCarSDK.enableAIOS();
                }else{
                    //on -> off
                    AIOSForCarSDK.disableAIOS();
                }
            }
        });
        aecSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtil.setBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_AEC_ENABLED, isChecked);
                AIOSCustomizeManager.getInstance().setAECEnabled(isChecked);
            }
        });
        interruptSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //是否开启打断
                PreferenceUtil.setBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_INTERRUPT_ENABLED, isChecked);
                AIOSCustomizeManager.getInstance().setInterruptEnabled(isChecked);
            }
        });

        nativeShortcutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //是否使用AIOS原生的快捷唤醒词
                PreferenceUtil.setBoolean(BridgeApplication.getContext(), PreferenceUtil.IS_NATIVE_SHORTCUT_ENABLE, isChecked);
                AIOSCustomizeManager.getInstance().setNativeShortcutWakeupsEnabled(isChecked);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_customize_reg:
                //注册一条自定义命令，命令串不支持中文、标点和特殊符号，一个命令串可对应多种说法
                //[我想|我要|帮我]，可选项 -> []
                //(开了|打开)，必选项 -> ()
//                Command command = new Command(OPEN_FM, new String[]{"开启fm","打开fm","帮我开fm","开下fm"});
                //注册单个命令，注册多个消息请见regCommands(List<Command>)
//                List<Command> commands = new ArrayList<Command>();
//                commands.add(command);
//                AIOSCustomizeManager.getInstance().regCommands(CustomizeCommandsPresenter.getInstance().getAllCommands());
                break;
            case R.id.btn_customize_clean:
                //清空SDK注册的所有自定义命令
                AIOSCustomizeManager.getInstance().cleanCommands();
                break;
            case R.id.btn_customize_scan_disable:
                //关闭APP扫描功能，不生成APP的打开与关闭/退出命令
                AIOSCustomizeManager.getInstance().setScanAppEnabled(false);
                break;
            case R.id.btn_customize_scan_enable:
                //开启APP扫描功能，生成APP的打开与关闭/退出命令
                AIOSCustomizeManager.getInstance().setScanAppEnabled(true);
                break;
            case R.id.btn_reversed_channel_enable:
                //打开通道反转
                AIOSCustomizeManager.getInstance().setReversedChannelEnabled(true);
                break;
            case R.id.btn_reversed_channel_disable:
                //关闭通道反转
                AIOSCustomizeManager.getInstance().setReversedChannelEnabled(false);
                break;
            case R.id.btn_customize_major_wakeup:
                //定制主唤醒词
                AIOSCustomizeManager.getInstance().setMajorWakeup("你好志玲", "ni hao zhi ling", 0.12f);
                break;
            case R.id.btn_customize_launch_tips:
                //定制启动提示语
                AIOSCustomizeManager.getInstance().setLaunchTips("小女子随时为您服务");
                break;
            case R.id.btn_customize_quick_wakeup:
                //定制快捷唤醒词
                List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
                shortcutWakeupList.add(new ShortcutWakeup(OPEN_DOOR, 0.12f));
                shortcutWakeupList.add(new ShortcutWakeup(CLOSE_DOOR, 0.12f));
                AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
                break;
            case R.id.btn_customize_disable_single:
                //禁用单个原生快捷唤醒词
                AIOSCustomizeManager.getInstance().disableNativeShortcutWakeup(new String[]{"tui chu dao hang"});
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听器
        AIOSCustomizeManager.getInstance().unregisterCustomizeListener();
    }
}
