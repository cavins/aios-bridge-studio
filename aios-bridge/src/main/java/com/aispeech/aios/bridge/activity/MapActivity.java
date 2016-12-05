package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.common.bean.MapInfo;
import com.aispeech.aios.common.bean.PoiBean;
import com.aispeech.aios.common.property.MapProperty;
import com.aispeech.aios.sdk.listener.AIOSMapListener;
import com.aispeech.aios.sdk.manager.AIOSMapManager;
import com.aispeech.aios.sdk.manager.AIOSSettingManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

/**
 * 地图接口示例类
 */
public class MapActivity extends Activity {
    private static final String TAG = "Bridge - MapActivity";
    /**地图监听器**/
    private AIOSMapListener mapListener = new AIOSMapListener() {

        @Override
        public void onStartNavi(@NonNull String packageName, @NonNull PoiBean poiBean) {
            AIOSTTSManager.speak("开始导航");
            Toast.makeText(getApplication() , "开始导航，包名："+packageName+" | 目的地："+poiBean.toString() , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "开始导航，包名："+packageName+" | 目的地："+poiBean.toString());
        }

        @Override
        public void onCancelNavi(@NonNull String packageName) {
            AIOSTTSManager.speak("退出成功");
            Toast.makeText(getApplication() , "退出导航，包名："+packageName , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "退出导航，包名："+packageName);
        }

        @Override
        public void onOverview(@NonNull String packageName) {
            Toast.makeText(getApplication() , "查看全程，包名："+packageName , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "查看全程，包名："+packageName);
        }

        @Override
        public void onRoutePlanning(@NonNull String packageName, @NonNull String strategy) {
            AIOSTTSManager.speak("开始路径规划");
            Toast.makeText(getApplication() , "开始路径规划，包名："+packageName+" | 规划策略："+strategy , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "开始路径规划，包名："+packageName+" | 规划策略："+strategy);
        }

        @Override
        public void onZoom(@NonNull String packageName, int zoomType) {
            Toast.makeText(getApplication() , "地图缩放，包名："+packageName+" | 缩放参数："+zoomType , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "地图缩放，包名："+packageName+" | 缩放参数："+zoomType);
        }

        @Override
        public void onLocate(@NonNull String packageName) {
            Toast.makeText(getApplication() , "定位当前位置，包名："+packageName , Toast.LENGTH_SHORT).show();
            AILog.d(TAG , "定位当前位置，包名："+packageName);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        AIOSMapManager.getInstance().registerMapListener(mapListener);
        //清空原有的地图信息，请根据具体情况决定是否调用。清空后，默认地图将会恢复到AIOS默认的地图（如果安装了）。
//        AIOSMapManager.getInstance().cleanMapInfo();
    }


    public void onAddMaps(View view){
        //设置新地图信息
        MapInfo tencentMap = new MapInfo("高德地图","com.autonavi.amapauto");
        //设置地图是否支持退出导航，默认false
        tencentMap.setCancelNaviSupported(true);
        //设置地图是否支持查看全程，默认false
        tencentMap.setOverviewSupported(true);
        //设置地图是否支持地图缩放，默认false
        tencentMap.setZoomSupported(true);
        //设置地图支持的路径规划策略，默认都不支持。目前AIOS只支持SupportedRoutePlanningStrategy下定义的四种规划策略
        tencentMap.setSupportedRoutePlanningStrategy(MapProperty.SupportedRoutePlanningStrategy.DRIVING_AVOID_CONGESTION , MapProperty.SupportedRoutePlanningStrategy.DRIVING_SAVE_MONEY);

        //添加地图，添加多个地图请见setLocalMapsInfo(List<MapInfo>)
        AIOSMapManager.getInstance().setLocalMapInfo(tencentMap);
    }

    public void onSetDefaultMap(View view){
        //将地图设置为默认地图
        AIOSSettingManager.getInstance().setDefaultMap("com.autonavi.amapauto");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销地图监听器
        AIOSMapManager.getInstance().unregisterMapListener();
    }
}
