package com.example.alarmmanagerexam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Main Activity 수행하는 Pending Intent
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent , 0);

        //20초 후의 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 20);

        //20초 후에 한번만 실행되는 알람 등록
        AlarmManager alarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManger.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),sender);


        Toast.makeText(MainActivity.this, "Start Alarm", Toast.LENGTH_SHORT).show();
    }
}
