package com.example.aidlexample1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// 参考
// https://android.keicode.com/basics/services-bound-with-ipc-aidl.php
// https://developer.android.com/guide/components/bound-services?hl=ja

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String STATUS_START = "サービス開始";
    private static final String STATUS_STOP = "サービス停止";
    private static final String SERVICE_PROCESS_ID = "サービスのプロセスID : ";
    private static final String SERVICE = "SERVICE";

    private TextView mProcessTextView;
    private TextView mStatusTextView;
    private TextView mSayYesTextView;

    private Button mStartButton;
    private Button mStopButton;

    private IRemoteService mIRemoteService;

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        // サービスとの接続が確立できたときに呼び出される
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Following the example above for an AIDL interface,
            // this gets an instance of the IRemoteInterface, which we can use to call on the service

            Log.d(TAG, "onServiceConnected()");
            // サービス上の呼び出せる IRemoteInterface のインスタンスを取得する
            mIRemoteService = IRemoteService.Stub.asInterface(service);

            //サービスから取得した文字列を画面に表示する
            try {
                mProcessTextView.setText(
                        SERVICE_PROCESS_ID +
                                String.valueOf(mIRemoteService.getPid()
                                )
                );

                mSayYesTextView.setText(
                        mIRemoteService.sayYes(SERVICE)
                );
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        // Called when the connection with the service disconnects unexpectedly
        // 意図せずサービスとの接続が切れたときに呼び出される
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected()");
            mIRemoteService = null;
        }
    };

    //---------- Event Listener ----------//
    private View.OnClickListener startServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "startButton.onClick()");
            //サービスを開始する
            Intent serviceIntent = new Intent(getApplicationContext(), RemoteService.class);
            bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);

            mStatusTextView.setText(STATUS_START);
            mStartButton.setEnabled(false);
            mStopButton.setEnabled(true);

        }

    };

    private View.OnClickListener stopServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "stopButton.onClick()");

            //サービスを停止する
            unbindService(mConnection);

            mStatusTextView.setText(STATUS_STOP);
            mProcessTextView.setText("");
            mSayYesTextView.setText("");
            mStopButton.setEnabled(false);
            mStartButton.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProcessTextView = (TextView) findViewById(R.id.processTextView);
        mStatusTextView = (TextView) findViewById(R.id.statusTextView);
        mSayYesTextView = (TextView) findViewById(R.id.sayYesTextView);

        mStartButton = (Button) findViewById(R.id.startButton);
        mStopButton = (Button) findViewById(R.id.stopButton);

        mStartButton.setOnClickListener(startServiceListener);
        mStopButton.setOnClickListener(stopServiceListener);
        mStopButton.setEnabled(false); // STOP ボタンは初期状態では無効

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mIRemoteService != null) {
            unbindService(mConnection);
        }
    }
}
