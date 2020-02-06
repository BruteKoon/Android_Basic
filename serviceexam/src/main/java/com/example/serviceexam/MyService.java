package com.example.serviceexam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();
    private Thread mThread;
    private int mCount = 0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mThread == null){
            //스레드 초기화 및 시작
            mThread = new Thread("My Thread"){
                @Override
                public void run() {
                    for(int i =0; i<100; i++){
                        try{
                            mCount++;
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            break;
                        }
                        Log.d("My Service", "서비스 동작 중 " + mCount);
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
        Log.d(TAG, "onDestory: ");

        //StopService에 의해 호출
        //스레드를 정지시킴
        if (mThread != null){
            mThread.interrupt();
            mThread = null;
        }
    }
}
