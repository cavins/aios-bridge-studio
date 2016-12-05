package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.common.property.StatusProperty;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.listener.AIOSStatusListener;
import com.aispeech.aios.sdk.manager.AIOSStatusManager;

/**
 * 状态接口示例类
 */
public class StatusActivity extends Activity implements AIOSStatusListener {
    private static final String TAG = "Bridge-StatusActivity";
    private static final String LJWTAG = "ljwtest:";

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AILog.d(TAG, "AIOS状态：" + AIOSForCarSDK.getAIOSState());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        IntentFilter filter = new IntentFilter("com.aios.bridge.AIOSState");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        AIOSStatusManager.getInstance().unregisterStatusListener();
    }

    public void onClick(View view) {
        AIOSStatusManager.getInstance().registerStatusListener(this);
    }

    //状态接口丰富中....
    /**
     * 人声检测（vad）音量变化
     *
     * @param volumeValue 变化后大小
     */
    @Override
    public void onVadVolumeChange(float volumeValue) {
        AILog.d(TAG, "人声检测音量变动：" + volumeValue);
    }

    /**
     * 倾听状态发生改变
     *
     * @param status 改变后的值，{"start","stop"}
     */
    @Override
    public void onListeningChange(String status) {
        AILog.d(TAG, "倾听状态变动：" + status);
    }

    /**
     * 识别状态发生改变
     *
     * @param status 改变后的值
     * @see StatusProperty#START  START
     * @see StatusProperty#STOP STOP
     */
    @Override
    public void onRecognitionChange(String status) {
        AILog.d(TAG, "识别状态变动：" + status);
    }

    /**
     * 语音识别文本
     *
     * @param inputContext 识别文本
     */
    @Override
    public void onContextInput(String inputContext) {
        AILog.d(TAG, "语音识别结果："+inputContext);
    }

    /**
     * 语音反馈文本
     *
     * @param outputContext 反馈文本
     */
    @Override
    public void onContextOutput(String outputContext) {
        AILog.d(TAG, "语音反馈结果："+ outputContext);
        Log.d(LJWTAG, "语音反馈结果:" + outputContext);
    }

    /**
     * aios状态改变
     *
     * @param status 新状态
     */
    @Override
    public void onAIOSStatusChange(String status) {
        AILog.d(TAG, "AIOS状态改变->"+status);
    }

    /**
     * TTS播报状态改变
     *
     * @see StatusProperty#BUSY 正在播放（BUSY）
     * @see StatusProperty#IDLE 初始化状态（IDLE）
     * @see StatusProperty#WAIT 播放结束（WAIT）
     */
    @Override
    public void onPlayerStatusChange(String status) {
        AILog.d(TAG, "TTS播报状态改变->"+status);
    }

    /**
     * 录音机状态改变
     *
     * @param status 新状态
     * @see StatusProperty#BUSY 正在录音（BUSY）
     * @see StatusProperty#IDLE 空闲中（IDLE）
     */
    @Override
    public void onRecorderStatusChange(String status) {
        AILog.d(TAG, "录音机播报状态改变->"+status);
    }
}
