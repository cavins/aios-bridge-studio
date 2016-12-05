package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.sdk.listener.AIOSRadioListener;
import com.aispeech.aios.sdk.manager.AIOSRadioManager;

/**
 * 收音机接口示例
 */
public class RadioActivity extends Activity implements AIOSRadioListener{
    private static final String TAG = "Bridge-RadioActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AIOSRadioManager.getInstance().unregisterRadioListener();
    }

    public void onClick(View view) {
        AIOSRadioManager.getInstance().registerRadioListener(this);
    }

    /**
     * 执行调频操作
     *
     * @param frequency FM频段
     */
    @Override
    public void onFMPlay(double frequency) {
        AILog.i(TAG, "调频到："+frequency);
    }

    /**
     * 执行调幅操作
     *
     * @param frequency AM频段
     */
    @Override
    public void onAMPlay(double frequency) {
        AILog.i(TAG, "调幅到："+frequency);
    }
}
