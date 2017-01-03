package service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.aispeech.aios.bridge.IBridgeAidlInterface;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.presenter.CustomizeBlueToothPhonePresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandBackupPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeFMPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMusicPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.presenter.SystemPresenter;
import com.aispeech.aios.bridge.presenter.UIPriorityPresenter;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

public class MainService extends Service {
//    private MainBinder mBinder;
//    private MainServiceConnection mainServiceConnection;
    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if(mBinder == null)
//            mBinder = new MainBinder();
//        mainServiceConnection = new MainServiceConnection();

        initPresenter();
    }

    private void initPresenter() {
        ////定制启动提示语
        AIOSCustomizeManager.getInstance().setLaunchTips("语音已启动，您可以使用征仔你好或者你好征仔来唤醒");
        //定制主唤醒词
        CustomizeWakeUpWordsPresenter.getInstance().loadingWakeUpWords();
        //默认开启AEC
        AIOSCustomizeManager.getInstance().setAECEnabled(true);
        //初始化自定义命令
        CustomizeCommandsPresenter.getInstance().regisCommands();
        //初始化快捷命令
        CustomizeCommandsPresenter.getInstance().regisCustomizeShortcutListener();
        //初始化自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().regisCustomizeListener();
        //初始化系统命令
        SystemPresenter.getInstance().regisSystemListener();
        //初始化地图
//        CustomizeMapsPresenter.getInstance().loadingMap();
//        CustomizeMapsPresenter.getInstance().setDefaultMap();
        //初始化蓝牙
        CustomizeBlueToothPhonePresenter.getInstance().registCustomizeListener();

        //初始化fm
        CustomizeFMPresenter.getInstance().registCustomizeListener();
        //开启APP扫描功能，生成APP的打开与关闭/退出命令
        AIOSCustomizeManager.getInstance().setScanAppEnabled(true);
        //开启默认UI优先级
        AIOSUIManager.getInstance().setUIPriorityEnabled(true);
//        UIPriorityPresenter.getInstance().registerUIListener();
        //注销部分默认快捷命令
        cancelDefaultwords();
        //初始化音乐
//        CustomizeMusicPresenter.getInstance().loadingCustomMusicApp();
    }

    private void cancelDefaultwords() {
        AIOSCustomizeManager.getInstance().disableNativeShortcutWakeup(new String[]{"tui chu dao hang", "jie shu dao hang", "guan bi dao hang", "qv xiao dao hang", "ting zhi dao hang"});
    }

    private void unRegisPresenter() {
        //注销自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().unRegisCustomizeListener();
        //注销系统命令监听
        SystemPresenter.getInstance().unRegisSystemListener();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        this.bindService(new Intent(MainService.this, DaemonService.class), mainServiceConnection, Context.BIND_IMPORTANT);
//        mPendingIntent =PendingIntent.getService(this, 0, intent, 0);
//        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    class MainBinder extends IBridgeAidlInterface.Stub {
//
//        @Override
//        public void doSomething() throws RemoteException {
//            Log.i("ljwtest:", "MainService is OK!");
//        }
//    }

//    class MainServiceConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            log_i("DaemonService is connecting!");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            log_i("MainService is killed!");
//            //启动守护进程
//            MainService.this.startService(new Intent(MainService.this, DaemonService.class));
//            MainService.this.bindService(new Intent(MainService.this, DaemonService.class), mainServiceConnection, Context.BIND_IMPORTANT);
//        }
//    }

    private void log_i(String s) {
        Log.i("ljwtest:", s);
    }

}
