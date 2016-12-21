package com.aispeech.aios.bridge.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.common.BtPhoneOrder;
import com.aispeech.aios.sdk.bean.Contact;
import com.aispeech.aios.sdk.bean.ShortcutWakeup;
import com.aispeech.aios.sdk.listener.AIOSPhoneListener;

import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSPhoneManager;
import com.aispeech.aios.sdk.manager.AIOSSystemManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.ArrayList;
import java.util.List;

import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_CANCEL_CONTACT_1;
import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_CANCEL_CONTACT_2;
import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_CANCEL_CONTACT_3;
import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_CANCEL_CONTACT_4;
import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_LOAD_CONTACT_1;
import static com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter.BT_LOAD_CONTACT_2;


public class CustomizeBlueToothPhonePresenter {


    private static final String FLA = CustomizeBlueToothPhonePresenter.class.getSimpleName();

    private static CustomizeBlueToothPhonePresenter customizeCommandBTPhonePresenter;

    public static synchronized CustomizeBlueToothPhonePresenter getInstance() {

        if (customizeCommandBTPhonePresenter == null) {

            customizeCommandBTPhonePresenter = new CustomizeBlueToothPhonePresenter();
        }
        return customizeCommandBTPhonePresenter;
    }


    private boolean isBTConnected = false; //临时变量，在Demo中用，请在正式代码中替换


    /**
     * 注册广播监听和电话监听
     */
    public void registCustomizeListener() {
        AIOSPhoneManager.getInstance().registerPhoneListener(aiosPhoneListener);
        AIOSPhoneManager.getInstance().setShowIncomingUI(false);

        IntentFilter intent = new IntentFilter(BtPhoneOrder.BT_CONNECTED); //蓝牙连接
        intent.addAction(BtPhoneOrder.BT_DISCONNECTED);//蓝牙断开
        intent.addAction(BtPhoneOrder.Synchronous_PhoneList);//同步联系人
        intent.addAction(BtPhoneOrder.BT_OUTGOING_RINGING);//蓝牙拨打电话
        intent.addAction(BtPhoneOrder.BT_CALL_ACCEPT);//蓝牙接听电话
        intent.addAction(BtPhoneOrder.BT_OUTGOING_OFFHOOK);//蓝牙正在通话中
        intent.addAction(BtPhoneOrder.BT_INCOMING_IDLE);//蓝牙电话主动挂断
        intent.addAction(BtPhoneOrder.BT_OUTGOING_IDLE);//蓝牙电话挂断
        intent.addAction(BtPhoneOrder.BT_TTS_SPEAK);//蓝牙自定义语音
        intent.addAction(BtPhoneOrder.BT_LOAD_CANCEL_CONTACT);//语音加载联系人
        intent.addAction(BtPhoneOrder.BT_AIOS_LOAD_CANCEL_CONTACT);//蓝牙按确定或者取消加载联系人
        BridgeApplication.getContext().registerReceiver(receiver, intent);
    }

    public void unRegisCustomizeListener() {
        AIOSPhoneManager.getInstance().unregisterPhoneListener();
        BridgeApplication.getContext().unregisterReceiver(receiver);
    }

    AIOSPhoneListener aiosPhoneListener = new AIOSPhoneListener() {
        @Override
        public boolean getBTStatus() {
            Log.e("JohnLog", FLA + "---aios---bt--getbtState-----" + isBTConnected);
            return isBTConnected;
        }

        @Override
        public void rejectIncoming() {
            Log.e("JohnLog", FLA + "--aios---bt--rejectIncoming-----" + isBTConnected);
            AIOSPhoneManager.getInstance().callEnd();
            BridgeApplication.getContext().sendBroadcast(new Intent(BtPhoneOrder.AIOS_BT_REJECT));
        }

        @Override
        public void acceptIncoming() {
            Log.e("JohnLog", FLA + "--aios---bt--rejectIncoming-----" + isBTConnected);
            BridgeApplication.getContext().sendBroadcast(new Intent(BtPhoneOrder.AIOS_BT_ACCEPT));
            AIOSPhoneManager.getInstance().callOffhook();
        }

        @Override
        public void makeCall(@Nullable String name, String number) {

        }

        @Override
        public String onSyncContactsResult(boolean isSuccess) {

            return isSuccess ? "联系人同步成功" : "联系人同步失败";
        }
    };


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("JohnLog", FLA + "-----bt--aios--receiver--action----" + action);

            if (action.equals(BtPhoneOrder.BT_CONNECTED)) {
                isBTConnected = true;
//                AIOSTTSManager.speak("蓝牙已连接");
            } else if (action.equals(BtPhoneOrder.BT_DISCONNECTED)) {
                isBTConnected = false;
//                AIOSTTSManager.speak("蓝牙已断开");
            } else if (action.equals(BtPhoneOrder.Synchronous_PhoneList)) {//同步联系人
                String btName = intent.getStringExtra(BtPhoneOrder.BT_BTName);
                String btNumber = intent.getStringExtra(BtPhoneOrder.BT_BTNum);
                Log.e("JohnLog", FLA + "-----bt--aios--receiver--btName----" + btName);
                Log.e("JohnLog", FLA + "-----bt--aios--receiver--btNumber----" + btNumber);

                updatePhoneNum(btName, btNumber);
            } else if (action.equals(BtPhoneOrder.BT_OUTGOING_RINGING)) {//蓝牙拨打电话
                AIOSPhoneManager.getInstance().outgoingCallRing();

            } else if (action.equals(BtPhoneOrder.BT_CALL_ACCEPT)) {//蓝牙接听电话
                AIOSPhoneManager.getInstance().callOffhook();

            } else if (action.equals(BtPhoneOrder.BT_OUTGOING_OFFHOOK)) {//蓝牙电话通话中
                AIOSPhoneManager.getInstance().callOffhook();
            } else if (action.equals(BtPhoneOrder.BT_INCOMING_IDLE) ||
                    action.equals(BtPhoneOrder.BT_OUTGOING_IDLE)) { //蓝牙电话挂断
                AIOSPhoneManager.getInstance().callEnd();
                AIOSSystemManager.getInstance().startRecorder();

            } else if (action.equals(BtPhoneOrder.BT_TTS_SPEAK)) {//语音播报
                String aiosText = intent.getStringExtra(BtPhoneOrder.BT_TTS_SPEAK_KEY);
                AIOSTTSManager.speak(aiosText);

            } else if (action.equals(BtPhoneOrder.BT_LOAD_CANCEL_CONTACT)) {//语音加载联系人
                registQuickWakeUp();

            } else if (action.equals(BtPhoneOrder.BT_AIOS_LOAD_CANCEL_CONTACT)) {
                unregistQuickWakeUp();
            }
        }
    };

    public void registQuickWakeUp() {
        //定制快捷唤醒词
        List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
        shortcutWakeupList.add(new ShortcutWakeup(BT_LOAD_CONTACT_1, 0.05f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_LOAD_CONTACT_2, 0.05f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_1, 0.05f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_2, 0.05f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_3, 0.05f));
        shortcutWakeupList.add(new ShortcutWakeup(BT_CANCEL_CONTACT_4, 0.05f));
        AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
    }

    public void unregistQuickWakeUp() {
        //定制快捷唤醒词
        List<ShortcutWakeup> shortcutWakeupList = new ArrayList<ShortcutWakeup>();
        shortcutWakeupList.add(new ShortcutWakeup("chao yang", 0.99f));
        AIOSCustomizeManager.getInstance().registerShortcutWakeups(shortcutWakeupList);
    }


    /**
     * 更新联系人
     *
     * @param name
     * @param num
     */
    public void updatePhoneNum(String name, String num) {
        Log.e("tcm", "name： " + name);
        Log.e("tcm", "num： " + num);
        String[] nameArray = name.split(",");
        String[] numArray = num.split(",");
        ArrayList<Contact> infos = new ArrayList<Contact>();
        Log.e("JohnLog", "nameArray： " + nameArray.length);
        for (int i = 0; i < nameArray.length; i++) {
            int index = getIndex(infos, nameArray[i]);
            Log.e("tcm", "index： " + index);
            Log.e("tcm", "nameArray： " + nameArray[i]);
            Log.e("tcm", "numArray： " + numArray[i]);
            if (index < 0) {
                Contact contactInfo = new Contact();
                contactInfo.setName(nameArray[i]);
                Contact.PhoneInfo phoneInfo = new Contact.PhoneInfo();
                phoneInfo.setNumber(numArray[i]);
                phoneInfo.setFlag("");
                contactInfo.addPhoneInfo(phoneInfo);
                infos.add(contactInfo);
            } else {
                Contact.PhoneInfo phoneInfo = new Contact.PhoneInfo();
                phoneInfo.setNumber(numArray[i]);
                phoneInfo.setFlag("");
                infos.get(index).addPhoneInfo(phoneInfo);
            }
        }
        if (infos.size() > 0) {
            AIOSPhoneManager.getInstance().syncContacts(infos); //将联系人同步给AIOS}
        }

    }

    /**
     * 获取联系人
     *
     * @param mList
     * @param name
     * @return
     */
    private int getIndex(ArrayList mList, String name) {
        for (int i = 0; i < mList.size(); i++) {
            Contact tmp = (Contact) mList.get(i);
            if (tmp.getName() != null && tmp.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

}