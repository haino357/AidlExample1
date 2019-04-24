package com.example.aidlexample1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private final String TAG = "RemoteService";
    private final String YES = ", Yes !";

    /*
     * AIDL ファイルで定義したインタフェースの中身をここに記載する
     */
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public int getPid() throws RemoteException {
            Log.d(TAG, "getPid()");
            // サービスのプロセスIDを返却する
            return Process.myPid();
        }

        @Override
        public String sayYes(String message) throws RemoteException {
            Log.d(TAG, "sayYes()");
            // サービス側でYESを付けて返却する
            return message + YES;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mBinder;
    }
}
