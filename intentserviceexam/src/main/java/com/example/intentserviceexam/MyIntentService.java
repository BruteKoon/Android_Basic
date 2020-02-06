package com.example.intentserviceexam;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i =0; i <5; i++){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
            //1초마다 로그남기기
            Log.d("MyIntentService", "인텐트 서비스 동작 중" + i);
        }

    }
    //인텐트 서비스는 스스로 stopSelf 메서드를 호출하므로 도중에 stopService를 호출하지 않아도 됨!!!
}
