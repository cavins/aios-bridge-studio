package service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.aispeech.aios.bridge.BridgeApplication;
import com.aispeech.aios.bridge.IBridgeAidlInterface;
import com.aispeech.aios.bridge.R;
import com.aispeech.aios.bridge.common.Common;
import com.aispeech.aios.bridge.presenter.CustomizeBlueToothPhonePresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandBackupPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeCommandsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeFMPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMapsPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeMusicPresenter;
import com.aispeech.aios.bridge.presenter.CustomizeWakeUpWordsPresenter;
import com.aispeech.aios.bridge.presenter.SystemPresenter;
import com.aispeech.aios.bridge.presenter.UIPriorityPresenter;
import com.aispeech.aios.bridge.utils.AIOSCommon;
import com.aispeech.aios.sdk.AIOSForCarSDK;
import com.aispeech.aios.sdk.manager.AIOSCustomizeManager;
import com.aispeech.aios.sdk.manager.AIOSUIManager;

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
        AIOSCommon.getInstance().initPresenter();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(MainService.this, DaemonService.class), mainServiceConnection, Context.BIND_IMPORTANT);
//        mPendingIntent =PendingIntent.getService(this, 0, intent, 0);
//        return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
