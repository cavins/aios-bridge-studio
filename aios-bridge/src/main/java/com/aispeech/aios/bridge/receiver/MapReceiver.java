package com.aispeech.aios.bridge.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aispeech.aios.bridge.common.MapOrder;
import com.aispeech.aios.bridge.utils.ToastLoadContactUtil;
import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;

public class MapReceiver extends BroadcastReceiver {
    private static final String TAG = MapReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("JohnLog", TAG + "----action---" + action);
        if (MapOrder.WECHAT_MAP_ACTION.equals(action)) {
            String cLat = intent.getStringExtra(MapOrder.WECHAT_LAT);
            String cLon = intent.getStringExtra(MapOrder.WECHAT_LNG);
            String locationAddress = intent.getStringExtra(MapOrder.WECHAT_TEXT);
            Log.e("JohnLog", TAG + "----cLatString---" + cLat + "----cLngString---" + cLon + "----地址----" + locationAddress);
            double lat = Double.valueOf(cLat).doubleValue();
            double lng = Double.valueOf(cLon).doubleValue();


            CoordinateConverter converter = new CoordinateConverter(context.getApplicationContext());
            // CoordType.GPS 待转换坐标类型
//            converter.from(CoordinateConverter.CoordType.SOSOMAP);
//            converter.from(CoordinateConverter.CoordType.MAPABC);
//            converter.from(CoordinateConverter.CoordType.MAPBAR);
//            converter.from(CoordinateConverter.CoordType.ALIYUN);
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.from(CoordinateConverter.CoordType.GOOGLE);
            converter.from(CoordinateConverter.CoordType.BAIDU);
            // sourceLatLng待转换坐标点 DPoint类型
            // 执行转换操作
            try {
                converter.coord(new DPoint(lat, lng));
                DPoint desLatLng = converter.convert();
                double latitude = desLatLng.getLatitude();
                double longitude = desLatLng.getLongitude();
                boolean aMapDataAvailable = converter.isAMapDataAvailable(latitude, longitude);

                Log.e("JohnLog", TAG
                        + "----cLatStr" +
                        "ing--高德转换--："
                        + latitude
                        + "--高德转换--cLngString---："
                        + longitude
                        + "---是否是高德地图的坐标---：" + aMapDataAvailable);

                ToastLoadContactUtil.getInstance().showToastContact(context.getApplicationContext(), latitude, longitude, locationAddress);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
