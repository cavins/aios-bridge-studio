package com.aispeech.aios.bridge.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aispeech.aios.bridge.common.MapOrder;
import com.aispeech.aios.bridge.utils.ToastLoadContactUtil;

import java.util.Map;

public class MapReceiver extends BroadcastReceiver {
    private static final String TAG = MapReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("JohnLog", TAG + "----action---" + action);
        if (MapOrder.WECHAT_MAP_ACTION.equals(action)) {
            String cLat = intent.getStringExtra(MapOrder.WECHAT_LAT);
            String cLon = intent.getStringExtra(MapOrder.WECHAT_LNG);
            Log.e("JohnLog", TAG + "----cLatString---" + cLat + "----cLngString---" + cLon);
            double lat = Double.valueOf(cLat).doubleValue();
            double lng = Double.valueOf(cLon).doubleValue();
            String cityName = intent.getStringExtra(MapOrder.WECHAT_TEXT);
            Log.e("JohnLog", TAG + "----cLat---" + lat + "----cLng---" + lng + "----cityName---" + cityName);
            ToastLoadContactUtil.getInstance().showToastContact(context.getApplicationContext(), lat, lng);
        }
    }
}
