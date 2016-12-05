package service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.aispeech.aios.bridge.IBridgeAidlInterface;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.presenter.CustomizeCommandBackupPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMusicPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.presenter.SystemPresenter;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;

public class MainService extends Service {
    private MainBinder mBinder;
    private MainServiceConnection mainServiceConnection;
    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mBinder == null)
            mBinder = new MainBinder();
        mainServiceConnection = new MainServiceConnection();

        initPresenter();
    }

    private void initPresenter() {
        //定制启动提示语
        AIOSCustomizeManager.getInstance().setLaunchTips(getResources().getString(R.string.launchtips));
        //定制唤醒词
        CustomizeWakeUpWordsPresenter.getInstance().loadingWakeUpWords();
        //初始化自定义命令
        CustomizeCommandsPresenter.getInstance().regisCommands();
        //初始化自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().regisCustomizeListener();
        //初始化系统命令
        SystemPresenter.getInstance().regisSystemListener();
        //初始化地图
        CustomizeMapsPresenter.getInstance().loadingMap();
        CustomizeMapsPresenter.getInstance().setDefaultMap();
        //初始化音乐
        CustomizeMusicPresenter.getInstance().loadingCustomMusicApp();
    }

    private void unRegisPresenter() {
        //注销自定义命令回馈
        CustomizeCommandBackupPresenter.getInstance().unRegisCustomizeListener();
        //注销系统命令监听
        SystemPresenter.getInstance().unRegisSystemListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(MainService.this, DaemonService.class), mainServiceConnection, Context.BIND_IMPORTANT);
//        mPendingIntent =PendingIntent.getService(this, 0, intent, 0);
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MainBinder extends IBridgeAidlInterface.Stub {

        @Override
        public void doSomething() throws RemoteException {
            Log.i("ljwtest:", "MainService is OK!");
        }
    }

    class MainServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log_i("DaemonService is connecting!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log_i("MainService is killed!");
            //启动守护进程
            MainService.this.startService(new Intent(MainService.this, DaemonService.class));
            MainService.this.bindService(new Intent(MainService.this, DaemonService.class), mainServiceConnection, Context.BIND_IMPORTANT);
        }
    }

    private void log_i(String s) {
        Log.i("ljwtest:", s);
    }

}
