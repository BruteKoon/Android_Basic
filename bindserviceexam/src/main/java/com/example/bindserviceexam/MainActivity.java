package com.example.bindserviceexam;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BindService mService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //서비스에 바인딩
        Intent intent = new Intent(this, BindService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //서비스와 연결해제
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * bindService()를 통해 서비스와 연결될 때의 콜백 정의
     */
    private ServiceConnection mConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //BinderService의 MyBinder와 연결될것이며, IBinder 타입으로 넘어오는 것을 캐스팅하여 사용
            BindService.MyBinder binder = (BindService.MyBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //예기치 않은 종료
        }
    };

    public void getCountValue(View view) {
        if(mBound){
            Toast.makeText(this, "카운팅 : " + mService.getCount(),Toast.LENGTH_SHORT).show();
        }
    }

    public void onStopService(View view) {
        Intent intent = new Intent(this, BindService.class);
        stopService(intent);
    }

    public void onStartService(View view) {
        Intent intent = new Intent(this, BindService.class);
        startService(intent);

    }
}
