package com.serviceprovider;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.Random;

public class MyServiceProvider extends Service {

    private static final String TAG = MyServiceProvider.class.getSimpleName();

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN = 0;
    private final int MAX = 100;

    public static final int GET_RANDOM_NUMBER_FLAG = 0;

    private class RandomNumberRequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            LogsUtils.printLog(TAG, "28 : handleMessage: ");
            switch (msg.what) {
                case GET_RANDOM_NUMBER_FLAG:
                    Message messageSendRandomNumber = Message.obtain(null, GET_RANDOM_NUMBER_FLAG);
                    messageSendRandomNumber.arg1 = getRandomNumber();
                    try {
                        msg.replyTo.send(messageSendRandomNumber);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
            super.handleMessage(msg);
        }
    }

    private Messenger randomNumberMessenger = new Messenger(new RandomNumberRequestHandler());

    @Override
    public IBinder onBind(Intent intent) {
        LogsUtils.printLog(TAG, "46 : onBind: ");
        return randomNumberMessenger.getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        LogsUtils.printLog(TAG, "51 : onRebind: ");
        super.onRebind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        LogsUtils.printLog(TAG, "57 : onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogsUtils.printLog(TAG, "62 : onDestroy: ");
        stopRandomNumberGenerator();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogsUtils.printLog(TAG, "67 : onStartCommand: ");
        mIsRandomGeneratorOn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    private void startRandomNumberGenerator() {
        LogsUtils.printLog(TAG, "83 : startRandomNumberGenerator: ");
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000);
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;
                    LogsUtils.printLog(TAG, "Random Number: " + mRandomNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRandomNumberGenerator() {
        LogsUtils.printLog(TAG, "98 : stopRandomNumberGenerator: ");
        mIsRandomGeneratorOn = false;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogsUtils.printLog(TAG, "104 : onUnbind: ");
        return super.onUnbind(intent);
    }

    public int getRandomNumber() {
        return mRandomNumber;
    }

}