package com.example.bindserviceexam;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BindService extends Service {

    private static final String TAG = "BinderService";
    private Thread mThread;
    private int mCount = 0;

    public BindService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mThread == null){
            //스레드 초기화 및 시작
            mThread = new Thread("My Thread"){
                @Override
                public void run() {
                    for(int i = 0; i < 100; i++){
                        try{
                            mCount++;
                            //1초마다 쉬기
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            break;
                        }
                    }
                }
            };
            mThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mThread != null){
            mThread.interrupt();
            mThread = null;
        }
    }

    //BindService의 레퍼런스를 반환하는 Binder 객체
    public class MyBinder extends Binder {
        public BindService getService(){
            return BindService.this;
        }
    }

    private IBinder mBinder = new MyBinder();


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind : ");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind : ");
        return super.onUnbind(intent);
    }

    public int getCount(){
        return mCount;
    }
}
