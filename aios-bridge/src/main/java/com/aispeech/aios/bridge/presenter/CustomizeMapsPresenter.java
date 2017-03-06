package com.aispeech.aios.bridge.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.common.AppPackageName;
import com.aispeech.aios.common.bean.MapInfo;
import com.aispeech.aios.common.bean.PoiBean;
import com.aispeech.aios.common.property.MapProperty;
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
    public static String CURRENT_MAPS = AppPackageName.GAODEMAP_APPLITE;
    private List<MapInfo> mapInfos;
    private static final String mapUrl = "baidumap://map/navi?query=";
    /**
     * 接入的地图名，是语音指明唤醒的名字，如“打开高德地图”
     */
//    public static final String OPEN_GAODEMAP = "高德地图";
    public static final String OPEN_BAIDU_MAP = "百度导航HD";

    public static synchronized CustomizeMapsPresenter getInstance() {

        if (customizeMapsPresenter == null) {

            customizeMapsPresenter = new CustomizeMapsPresenter();
        }
        return customizeMapsPresenter;
    }

    public CustomizeMapsPresenter() {
//        regisMapListener();
//        loadingMap();
        setDefaultMap();
    }

    public void loadingMap() {
        //设置新地图信息
        MapInfo baiduMap = new MapInfo(OPEN_BAIDU_MAP,AppPackageName.BAIDUMAP_APP);
        //设置地图是否支持退出导航，默认false
        baiduMap.setCancelNaviSupported(true);
        //设置地图是否支持查看全程，默认false
        baiduMap.setOverviewSupported(true);
        //设置地图是否支持地图缩放，默认false
        baiduMap.setZoomSupported(true);
        //设置地图支持的路径规划策略，默认都不支持。目前AIOS只支持SupportedRoutePlanningStrategy下定义的四种规划策略
        baiduMap.setSupportedRoutePlanningStrategy(MapProperty.SupportedRoutePlanningStrategy.DRIVING_AVOID_CONGESTION , MapProperty.SupportedRoutePlanningStrategy.DRIVING_SAVE_MONEY);

//        //添加地图，添加多个地图请见setLocalMapsInfo(List<MapInfo>)
//        AIOSMapManager.getInstance().setLocalMapInfo(baiduMap);
//        mapInfos.add(new MapInfo(OPEN_BAIDU_MAP, AppPackageName.BAIDUMAP_APP));
        AIOSMapManager.getInstance().setLocalMapInfo(baiduMap);
    }

    //高德地图设为默认地图
    public void setDefaultMap() {
        AIOSSettingManager.getInstance().setDefaultMap(AppPackageName.GAODEMAP_APPLITE);
    }

    public void regisMapListener() {
        AIOSMapManager.getInstance().registerMapListener(customizeMapListener);
    }

    public void changeDefaultMap(String mapType) {
        if(mapType.equals("gaode")) {
            AIOSSettingManager.getInstance().setDefaultMap(AppPackageName.GAODEMAP_APPLITE);
            CURRENT_MAPS = AppPackageName.GAODEMAP_APPLITE;
        }
        else {
            AIOSSettingManager.getInstance().setDefaultMap(AppPackageName.BAIDUMAP_APP);
            CURRENT_MAPS = AppPackageName.BAIDUMAP_APP;
        }
    }

    public void unregisMapListener() {
        AIOSMapManager.getInstance().unregisterMapListener();
    }

    private AIOSMapListener customizeMapListener = new AIOSMapListener() {
        @Override
        public void onStartNavi(@NonNull String packageName, @NonNull PoiBean poiBean) {
            Intent intent = new Intent(mapUrl + poiBean.getName() + poiBean.getAddress());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BridgeApplication.getContext().startActivity(intent);
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
