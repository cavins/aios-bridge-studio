package com.aispeech.aios.bridge.common;

/**
 * 关于地图的一些常量
 */

public class MapOrder {

    /****
     * 从微信接收
     *****/

    //经纬度和位置的广播
    public static final String WECHAT_MAP_ACTION = "action.colink.remotecommand";

    public static final String WECHAT_LAT = "lat";//纬度
    public static final String WECHAT_LNG = "lng";//经度
    public static final String WECHAT_TEXT = "text";//地名

//    Intent intent = new Intent();
//    intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
//    intent.putExtra("KEY_TYPE", 10038);
//    intent.putExtra("POINAME","厦门大学");
//    intent.putExtra("LAT",24.444593);
//    intent.putExtra("LON",118.101011);
//    intent.putExtra("DEV",0);
//    intent.putExtra("STYLE",0);
//    intent.putExtra("SOURCE_APP","Third App");
//    sendBroadcast(intent);


    /****
     * 发送给高德
     *****/

    public static final String MAP_TO_GAODE_ACTION = "AUTONAVI_STANDARD_BROADCAST_RECV";//广播的action
    public static final String KEY_TYPE = "KEY_TYPE";//
    public static final String POINAME = "POINAME";//地点
    public static final String LAT = "LAT";//纬度
    public static final String LON = "LON";//经度
    public static final String DEV = "DEV";//偏移
    public static final String STYLE = "STYLE";//类型
    public static final String SOURCE_APP = "SOURCE_APP";//app名称


}
