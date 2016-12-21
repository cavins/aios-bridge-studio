package com.aispeech.aios.bridge.common;

import android.content.Intent;

/**
 * 蓝牙发送过来的额一些广播
 */

public class BtPhoneOrder {

    public static final String BT_CONNECTED = "com.android.bt.connected";//蓝牙连接
    public static final String BT_DISCONNECTED = "com.android.bt.disconnected";//蓝牙断开

    public static final String AIOS_BT_ACCEPT = "action.intent.AIOS_ACCEPT";//接听广播
    public static final String AIOS_BT_REJECT = "action.intent.AIOS_REJECT";//拒绝来电的广播

    public static final String Synchronous_PhoneList = "com.AnywheeBt.Synchronous_PhoneList_Aios";//接受联系人广播
    public static final String BT_BTName = "BTName";//接收蓝牙联系人名称的KEY
    public static final String BT_BTNum = "BTNum";//接收蓝牙联系人电话的KEY


    /******************************
     * 蓝牙app发送过来的反馈广播
     *********************************************/

    public static final String BT_OUTGOING_RINGING = "action.bt.AIOS_OUTGOING_RINGING";//蓝牙正在拨打电话状态

    public static final String BT_OUTGOING_OFFHOOK = "action.bt.AIOS_OUTGOING_OFFHOOK";//蓝牙正在通话中

    public static final String BT_OUTGOING_IDLE = "action.bt.AIOS_OUTGOING_IDLE";//蓝牙电话挂断

    public static final String BT_INCOMING_IDLE = "action.bt.AIOS_INCOMING_IDLE";//蓝牙主动挂断电话

    public static final String BT_CALL_ACCEPT = "com.conqueror.bluetootphone.acceptCall";//蓝牙主动接听电话

    public static final String BT_TTS_SPEAK = "com.aispeech.aios.adapter.speak";//蓝牙自定义的语音播报
    public static final String BT_TTS_SPEAK_KEY = "text";//自定义语音的键

//
//    /**
//     * 向语音发送收到来电的广播
//     */
//    String SEND_AIOS_INCOMING_RINGING = "action.bt.AIOS_INCOMING_RINGING";


    public static final String BT_LOAD_CANCEL_CONTACT = "com.conquer.bt.LoadOrCancelContact";//蓝牙是否语音加载联系人
    public static final String BT_AIOS_LOAD_CANCEL_CONTACT = "com.conqueror.bt.aios.LoadOrCancelContact";//蓝牙按键确认或者取消加载联系人


    public static final String AIOS_LOAD_CONTACT = "com.conquer.aios.bt.loadContact";//语音确定加载联系人
    public static final String AIOS_DISLOAD_CONTACT = "com.conquer.aios.bt.cancelLoadContact";//语音取消加载联系人


}
