package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.utils.PreferenceUtil;
import com.aispeech.aios.common.bean.UIPriority;
import com.aispeech.aios.common.property.UIProperty.UIPriorityProperty;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.listener.AIOSUIListener;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

import java.util.ArrayList;
import java.util.List;

/**
 * UI模块示例类
 */
public class UIActivity extends Activity implements AIOSUIListener {
    private static final String TAG = "Bridge - UIActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        //注册UI监听器
        AIOSUIManager.getInstance().registerUIListener(this);

        //清空AIOS的UI优先级缓存，请根据具体情况决定是否调用
        AIOSUIManager.getInstance().cleanUIPriority();

        CheckBox globalMicCheckBox = (CheckBox) findViewById(R.id.btn_ui_show_global_mic);
        globalMicCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AIOSUIManager.getInstance().setShowGlobalMic(isChecked);
                PreferenceUtil.setBoolean(UIActivity.this, PreferenceUtil.IS_SHOW_GLOBAL_MIC, isChecked);
            }
        });
        CheckBox wechatPublicQrcodeCB = (CheckBox) findViewById(R.id.btn_ui_show_wechat_qrcode);
        wechatPublicQrcodeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AIOSUIManager.getInstance().setShowWechatPublicQrcode(isChecked);
                PreferenceUtil.setBoolean(UIActivity.this, PreferenceUtil.IS_WECHAT_PUBLIC_QRCODE_SWITCH, isChecked);
            }
        });

        initWechatPublicQrcode(wechatPublicQrcodeCB);
        initGlobalMic(globalMicCheckBox);
    }

    private void initGlobalMic(CheckBox checkBox){
        boolean isShowGlobalMic = PreferenceUtil.getBoolean(this, PreferenceUtil.IS_SHOW_GLOBAL_MIC, false);
        checkBox.setChecked(isShowGlobalMic);
        AIOSUIManager.getInstance().setShowGlobalMic(isShowGlobalMic);
    }

    private void initWechatPublicQrcode(CheckBox checkBox){
        boolean isShowWechatPublicQrcode = PreferenceUtil.getBoolean(this, PreferenceUtil.IS_WECHAT_PUBLIC_QRCODE_SWITCH, true);
        checkBox.setChecked(isShowWechatPublicQrcode);
        AIOSUIManager.getInstance().setShowWechatPublicQrcode(isShowWechatPublicQrcode);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销UI监听器
        AIOSUIManager.getInstance().unregisterUIListener();
    }

    public void onClick(View view) {
        int id = view.getId();
        List<UIPriority> uiPriorities = new ArrayList<UIPriority>();
        switch (id) {
            case R.id.btn_ui_1:
                uiPriorities.add(new UIPriority(AIOSForCarSDK.getPackageName(), UIPriorityProperty.HIGHEST_PRIORITY));
                AIOSUIManager.getInstance().addUIPriority(uiPriorities);
                break;
            case R.id.btn_ui_2:
                uiPriorities.add(new UIPriority(AIOSForCarSDK.getPackageName(), UIPriorityProperty.HIGHER_PRIORITY));
                AIOSUIManager.getInstance().addUIPriority(uiPriorities);
                break;
            case R.id.btn_ui_3:
                uiPriorities.add(new UIPriority(AIOSForCarSDK.getPackageName(), UIPriorityProperty.MIDDLE_PRIORITY));
                AIOSUIManager.getInstance().addUIPriority(uiPriorities);
                break;
            case R.id.btn_ui_4:
                uiPriorities.add(new UIPriority(AIOSForCarSDK.getPackageName(), UIPriorityProperty.AIOSUI_PRIORITY));
                AIOSUIManager.getInstance().addUIPriority(uiPriorities);
                break;
            case R.id.btn_ui_del:
                AIOSUIManager.getInstance().delUIPriority(AIOSForCarSDK.getPackageName());
                break;

            case R.id.btn_ui_enable:
                AIOSUIManager.getInstance().setUIPriorityEnabled(true);
                break;
            case R.id.btn_ui_disable:
                AIOSUIManager.getInstance().setUIPriorityEnabled(false);
                break;

            case R.id.btn_customize_layout:
                //获得设置了默认值的layout参数，以便只替换部分属性即可。
                WindowManager.LayoutParams layoutParams = AIOSUIManager.getInstance().obtainLayoutParams();
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                //定制布局参数
                AIOSUIManager.getInstance().setLayoutParams(layoutParams);
                break;
            case R.id.btn_customize_traffic_origin:
                //设置路况显示的起点坐标为200px,200px
                AIOSUIManager.getInstance().setTrafficOrigin(0,0,400,400);
                break;

            case R.id.btn_ui_disappear_always:
                //设置AIOS UI同样遵循UI优先级机制
                AIOSUIManager.getInstance().setUIDisappearAlways(false);
                break;

            default:
                break;
        }
    }

    /**
     * UI优先级生效回调接口
     *
     * @param effectTime     生效时间
     * @param pkgNameBefore  生效前，APP栈栈顶APP包名；
     * @param priorityBefore 生效前，PP栈栈顶APP优先级。
     * @param pkgNameAfter   生效后，APP栈栈顶APP包名；
     * @param priorityAfter  生效后，APP栈栈顶APP优先级。
     */
    @Override
    public void onUIPriorityEffect(long effectTime, @NonNull String pkgNameBefore, int priorityBefore, @NonNull String pkgNameAfter, int priorityAfter) {
        AILog.d(TAG, "UI优先级生效，时间戳：" + effectTime);
        AILog.d(TAG, "生效前：" + pkgNameBefore + "->" + priorityBefore);
        AILog.d(TAG, "生效后：" + pkgNameAfter + "->" + priorityAfter);
        Toast.makeText(getApplicationContext(), "UI优先级生效，时间戳：" + effectTime, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUIStateChanged(boolean isShowing) {
        AILog.d(TAG, "UI状态改变了,isShowing = " + isShowing);
    }
}
