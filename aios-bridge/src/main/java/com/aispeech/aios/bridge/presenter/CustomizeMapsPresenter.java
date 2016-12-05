package com.aispeech.aios.bridge.presenter;

import android.support.annotation.NonNull;

import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.common.bean.MapInfo;
import com.aispeech.aios.common.bean.PoiBean;
import com.aispeech.aios.sdk.listener.AIOSMapListener;
import com.aispeech.aios.sdk.manager.AIOSMapManager;
import com.aispeech.aios.sdk.manager.AIOSSettingManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class CustomizeMapsPresenter {
    private static CustomizeMapsPresenter customizeMapsPresenter;
    private List<MapInfo> mapInfos;
    /**
     * 接入的地图名，是语音指明唤醒的名字，如“打开高德地图”
     */
    public static final String OPEN_GAODEMAP = "高德地图";
    public static final String OPEN_BAIDU_MAP = "百度地图";

    public static synchronized CustomizeMapsPresenter getInstance() {

        if (customizeMapsPresenter == null) {

            customizeMapsPresenter = new CustomizeMapsPresenter();
        }
        return customizeMapsPresenter;
    }

    public CustomizeMapsPresenter() {
        regisMapListener();
        loadingMap();
    }

    public void loadingMap() {
        mapInfos = new ArrayList<MapInfo>();
        mapInfos.add(new MapInfo(OPEN_GAODEMAP, AppPackageName.GAODEMAP_APPLITE));
        mapInfos.add(new MapInfo(OPEN_BAIDU_MAP, AppPackageName.BAIDUMAP_APP));
        AIOSMapManager.getInstance().setLocalMapList(mapInfos);
    }

    //高德地图设为默认地图
    public void setDefaultMap() {
        AIOSSettingManager.getInstance().setDefaultMap(AppPackageName.GAODEMAP_APPLITE);
    }

    public void regisMapListener() {
        AIOSMapManager.getInstance().registerMapListener(customizeMapListener);
    }

    public void unregisMapListener() {
        AIOSMapManager.getInstance().unregisterMapListener();
    }

    private AIOSMapListener customizeMapListener = new AIOSMapListener() {
        @Override
        public void onStartNavi(@NonNull String packageName, @NonNull PoiBean poiBean) {

        }

        @Override
        public void onCancelNavi(@NonNull String packageName) {

        }

        @Override
        public void onOverview(@NonNull String packageName) {

        }

        @Override
        public void onRoutePlanning(@NonNull String packageName, @NonNull String strategy) {

        }

        @Override
        public void onZoom(@NonNull String packageName, int zoomType) {

        }

        @Override
        public void onLocate(@NonNull String packageName) {

        }
    };

}
