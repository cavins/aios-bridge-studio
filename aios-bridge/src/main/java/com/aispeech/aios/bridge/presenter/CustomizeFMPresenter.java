package com.aispeech.aios.bridge.presenter;

import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.common.BtPhoneOrder;
import com.aispeech.aios.bridge.common.FMOrder;
import com.aispeech.aios.sdk.listener.AIOSRadioListener;
import com.aispeech.aios.sdk.manager.AIOSPhoneManager;
import com.aispeech.aios.sdk.manager.AIOSRadioManager;

/**
 * Created by Administrator on 2016/12/11.
 */

public class CustomizeFMPresenter {


    private static final String FLA = CustomizeFMPresenter.class.getSimpleName();

    private static CustomizeFMPresenter customizeFMPresenter;

    public static synchronized CustomizeFMPresenter getInstance() {

        if (customizeFMPresenter == null) {

            customizeFMPresenter = new CustomizeFMPresenter();
        }
        return customizeFMPresenter;
    }


    /**
     * 注册广播监听和电话监听
     */
    public void registCustomizeListener() {
        AIOSRadioManager.getInstance().registerRadioListener(aiosRadioManager);
    }

    /**
     * 取消监听
     */
    public void unRegisCustomizeListener() {
        AIOSRadioManager.getInstance().unregisterRadioListener();
    }

    AIOSRadioListener aiosRadioManager = new AIOSRadioListener() {
        @Override
        public void onFMPlay(double frequency) {
            Log.e("John", "-----aios---fm---frequency----" + frequency);
            Intent it = new Intent(FMOrder.FM_FREQUENCY);
            it.putExtra(FMOrder.FM_FREQUENCY_KEY, "" + frequency);
            BridgeApplication.getContext().sendBroadcast(it);
        }

        @Override
        public void onAMPlay(double frequency) {
            Log.e("John", "-----aios---fm---frequency----" + frequency);
        }
    };


}
