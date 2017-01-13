package com.aispeech.aios.bridge.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.aispeech.ailog.AILog;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.sdk.bean.Contact;
import com.aispeech.aios.sdk.listener.AIOSPhoneListener;
import com.aispeech.aios.sdk.manager.AIOSPhoneManager;
import com.aispeech.aios.sdk.manager.AIOSTTSManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话功能Demo，主要用来演示AIOSPhoneListener，可在蓝牙APP中实现此监听器
 */

public class PhoneActivity extends Activity implements AIOSPhoneListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private boolean isBTConnected = false; //临时变量，在Demo中用，请在正式代码中替换
    private TextView textInfo;
    private TextView textConnectState;
    private TextView textShowUIState;
    private EditText contactName;
    private EditText contactNumber;
    private Button btnInAccept;
    private Button btnInReject;
    private Button btnOutAccept;
    private Button btnHangUp;
    private Button btnIncoming;
    private Button btnOutgoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        AIOSPhoneManager.getInstance().registerPhoneListener(this);

        textInfo = (TextView) findViewById(R.id.text_phone_info);
        if (null != savedInstanceState) {
            textInfo.setText(savedInstanceState.getString("text"));
        }

        textConnectState = (TextView) findViewById(R.id.text_connect_state);
        textShowUIState = (TextView) findViewById(R.id.text_ui_state);
        btnInAccept = (Button) findViewById(R.id.button_accept);
        btnInReject = (Button) findViewById(R.id.button_reject);
        btnOutAccept = (Button) findViewById(R.id.button_accept_simulate);
        btnHangUp = (Button) findViewById(R.id.button_hangup);
        btnIncoming = (Button) findViewById(R.id.button_incoming);
        btnOutgoing = (Button) findViewById(R.id.button_outgoing);

        ((CheckBox) findViewById(R.id.switch_connect)).setOnCheckedChangeListener(this);
        ((CheckBox) findViewById(R.id.switch_show_ui)).setOnCheckedChangeListener(this);

        contactName = (EditText) findViewById(R.id.edit_contact_name);
        contactNumber = (EditText) findViewById(R.id.edit_contact_number);

        disableAllButton();
        btnIncoming.setEnabled(true);
        btnOutgoing.setEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", textInfo.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销电话监听器
        AIOSPhoneManager.getInstance().unregisterPhoneListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sync_contacts:
                //同步联系人方法，二者选其一
                syncContactAsList();
                break;

            case R.id.button_clear_contacts:
                AIOSPhoneManager.getInstance().cleanContacts();
                break;

            case R.id.button_incoming:
                //有来电
                if(isBTConnected){
                    String name = contactName.getText().toString();
                    String number = contactNumber.getText().toString();
                    AIOSPhoneManager.getInstance().incomingCallRing(name, number);
                    String context = "您有" + name + "(" + number + ")的来电，接听还是拒绝？   -----无法唤醒";
                    textInfo.setText(context);
                    disableAllButton();
                    btnInAccept.setEnabled(true);
                    btnInReject.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            case R.id.button_outgoing:
                //去电
                if(isBTConnected) {
                    AILog.e("SDK-AIOSPhoneManager模拟拨打电话");
                    AIOSPhoneManager.getInstance().outgoingCallRing();
                    textInfo.setText("拨打电话中....     -----无法唤醒");
                    disableAllButton();
                    btnOutAccept.setEnabled(true);
                    btnHangUp.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            case R.id.button_accept:
                //接听来电
                if(isBTConnected) {
                    AIOSPhoneManager.getInstance().callOffhook();
                    textInfo.setText("正在通话中（来电）....   -----无法唤醒");
                    disableAllButton();
                    btnHangUp.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            case R.id.button_reject:
                //拒绝来电
                if(isBTConnected) {
                    AIOSPhoneManager.getInstance().callEnd();
                    textInfo.setText("已拒绝来电。");
                    disableAllButton();
                    btnIncoming.setEnabled(true);
                    btnOutgoing.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            case R.id.button_accept_simulate:
                //模拟对方接听电话
                if(isBTConnected) {
                    AIOSPhoneManager.getInstance().callOffhook();
                    textInfo.setText("对方已接受来电，通话中....      -----无法唤醒");
                    disableAllButton();
                    btnHangUp.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            case R.id.button_hangup:
                //对方或者我方挂断电话
                if(isBTConnected) {
                    AIOSPhoneManager.getInstance().callEnd();
                    textInfo.setText("对方或者我方已挂断电话。");
                    disableAllButton();
                    btnOutgoing.setEnabled(true);
                    btnIncoming.setEnabled(true);
                }else{
                    AIOSTTSManager.speak(getString(R.string.bluetooth_disconnect));
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_connect:
                isBTConnected = isChecked;
                AIOSPhoneManager.getInstance().setBTStatus(isBTConnected);
                if (isChecked) {
                    AIOSTTSManager.speak("蓝牙已连接");
                    textInfo.setText("蓝牙已连接");
                    if (textConnectState != null) {
                        textConnectState.setText("已连接");
                    }
                } else {
                    AIOSTTSManager.speak("蓝牙已断开");
                    textInfo.setText("蓝牙已断开");
                    if (textConnectState != null) {
                        textConnectState.setText("已断开");
                    }
                }
                break;
            case R.id.switch_show_ui:
                AIOSPhoneManager.getInstance().setShowIncomingUI(isChecked);
                if (isChecked) {
                    if (textShowUIState != null) {
                        textShowUIState.setText("显示UI");
                    }
                } else {
                    if (textShowUIState != null) {
                        textShowUIState.setText("不显示UI");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean getBTStatus() {
        return isBTConnected;
    }

    @Override
    public void rejectIncoming() {
        textInfo.setText("已拒接来电。");
        AILog.e("已拒接来电");
        disableAllButton();
        btnIncoming.setEnabled(true);
        btnOutgoing.setEnabled(true);
    }

    @Override
    public void acceptIncoming() {
        AIOSPhoneManager.getInstance().callOffhook();
        textInfo.setText("正在通话中（来电）....   -----无法唤醒");
        AILog.e("已接听来电");
        disableAllButton();
        btnHangUp.setEnabled(true);
    }

    @Override
    public void makeCall(String name, String number) {
        String content = "模拟打电话给" + name + "(" + number + ")";
        textInfo.setText(content);
        //do something
        disableAllButton();
        btnOutAccept.setEnabled(true);
        btnHangUp.setEnabled(true);
    }

    @Override
    public String onSyncContactsResult(boolean isSuccess) {
        if (isSuccess) {
            //do something 联系人同步成功
            return "联系人同步成功";
        } else {
            //do something 联系人同步失败
            return "联系人同步失败";
        }
    }
    /**
     * 此处方法较为简单，只添加2个联系人，如若多个联系人，建议使用循环
     */
    private void syncContactAsList() {
        List<Contact> contacts = new ArrayList<Contact>();
        Contact contact = new Contact();
        Contact.PhoneInfo phoneInfo = new Contact.PhoneInfo();

        /*-------------------- 添加第一个联系人 ----------------------*/
        contact.setName("张三"); //第一个联系人
        phoneInfo.setNumber("10086"); //张三的第一个号码
        phoneInfo.setFlag("家庭");
        contact.addPhoneInfo(phoneInfo);

        phoneInfo = new Contact.PhoneInfo();
        phoneInfo.setNumber("1008601"); //张三的第二个号码
        contact.addPhoneInfo(phoneInfo);

        contacts.add(contact);
        /*-------------------- 添第一个联系人结束 ----------------------*/

        /*-------------------- 添加第二个联系人 ----------------------*/
        contact = new Contact();
        phoneInfo = new Contact.PhoneInfo();

        contact.setName("张麻子"); //第一个联系人
        phoneInfo.setNumber("1008611"); //张麻子的第一个号码
        contact.addPhoneInfo(phoneInfo);

        contacts.add(contact);
        /*-------------------- 添加第二个联系人结束 ----------------------*/

        AIOSPhoneManager.getInstance().syncContacts(contacts); //将联系人同步给AIOS
    }


    private void disableAllButton(){
        btnInAccept.setEnabled(false);
        btnInReject.setEnabled(false);
        btnOutAccept.setEnabled(false);
        btnHangUp.setEnabled(false);
        btnIncoming.setEnabled(false);
        btnOutgoing.setEnabled(false);
    }
}
