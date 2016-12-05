package service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.aispeech.aios.bridge.IBridgeAidlInterface;


public class DaemonService extends Service {
    private DaemonBinder daemonBinder;
    private DaemonServiceConnection daemonServiceConnection;
    public DaemonService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(daemonBinder == null)
            daemonBinder = new DaemonBinder();
        daemonServiceConnection = new DaemonServiceConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.bindService(new Intent(DaemonService.this, MainService.class), daemonServiceConnection, Context.BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return daemonBinder;
    }

    private void log_i(String s) {
        Log.i("ljwtest:", s);
    }

    class DaemonBinder extends IBridgeAidlInterface.Stub {

        @Override
        public void doSomething() throws RemoteException {
            log_i("DaemonService is connecting!");
        }
    }

    class DaemonServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            log_i("DaemonService is connecting!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            log_i("DaemonService is killed!");
            //启动守护进程
            DaemonService.this.startService(new Intent(DaemonService.this, MainService.class));
            DaemonService.this.bindService(new Intent(DaemonService.this, MainService.class), daemonServiceConnection, Context.BIND_IMPORTANT);
        }
    }
}
