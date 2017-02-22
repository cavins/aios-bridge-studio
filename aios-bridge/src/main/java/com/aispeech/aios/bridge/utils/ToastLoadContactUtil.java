package com.aispeech.aios.bridge.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.view.TimerDialog;


public class ToastLoadContactUtil {

    private static final String TAG = ToastLoadContactUtil.class.getName();

    private static ToastLoadContactUtil instance = null;
    private TimerDialog ldialog;

    public static ToastLoadContactUtil getInstance() {
        if (instance == null) {
            instance = new ToastLoadContactUtil();
        }
        return instance;
    }


    public void showToastContact(final Context context, final double lat, final double lng) {

        //弹出倒计时对话框
        ldialog = new TimerDialog(context);
        ldialog.setTitle("是否进入导航");

        //确认按钮
        ldialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openMapOperation(context, context.getApplicationContext().getResources().getString(R.string.app_name), lat, lng);
            }
        }, 8);

        //取消按钮
        ldialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ldialog.cancelDialog();
            }
        }, 0);

        ldialog.show();
        ldialog.setButtonType(Dialog.BUTTON_POSITIVE, 8, true);
        ldialog.setButtonType(Dialog.BUTTON_NEGATIVE, 0, true);
    }


    private void openMapOperation(Context context, String appName, double lat, double lon) {
        //dev和style照旧
        String url = "androidauto://navi?sourceApplication=" + appName + "&poiname=fangheng"
                + "&lat=" + lat
                + "&lon=" + lon
                + "&dev=1&style=2";
        Intent intent = new Intent();
        intent.setData(android.net.Uri.parse(url));
        intent.setPackage("com.autonavi.amapautolite");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void cancelDialog() {
        if (ldialog != null) {
            ldialog.cancelDialog();
        }
    }
}
