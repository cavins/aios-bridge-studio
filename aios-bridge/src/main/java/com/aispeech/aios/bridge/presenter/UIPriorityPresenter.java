package com.aispeech.aios.bridge.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.common.bean.UIPriority;
import com.aispeech.aios.common.property.UIProperty;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.listener.AIOSUIListener;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class UIPriorityPresenter implements AIOSUIListener {
    private List<UIPriority> uiPriorities;
    private static UIPriorityPresenter uiPriorityPresenter;

    public static synchronized UIPriorityPresenter getInstance() {
        if(uiPriorityPresenter == null)
            uiPriorityPresenter = new UIPriorityPresenter();
        return uiPriorityPresenter;
    }

    public UIPriorityPresenter() {
        loadingAppPriority();
    }

    public void registerUIListener() {
        AIOSUIManager.getInstance().registerUIListener(this);
    }

    public void unRegisterUIListener() {
        AIOSUIManager.getInstance().unregisterUIListener();
    }

    private void loadingAppPriority() {
        uiPriorities = new ArrayList<UIPriority>();
        uiPriorities.add(new UIPriority(AppPackageName.KUWO_APP, UIProperty.UIPriorityProperty.MIDDLE_PRIORITY));
        uiPriorities.add(new UIPriority(AppPackageName.GAODEMAP_APPLITE, UIProperty.UIPriorityProperty.HIGHER_PRIORITY));
        AIOSUIManager.getInstance().addUIPriority(uiPriorities);
    }

    @Override
    public void onUIPriorityEffect(long effectTime, @NonNull String pkgNameBefore, int priorityBefore, @NonNull String pkgNameAfter, int priorityAfter) {
        Log.i("ljwtest:", "UI优先级生效");
    }

    @Override
    public void onUIStateChanged(boolean isShowing) {

    }
}
