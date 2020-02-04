package com.example.threadexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        mProgressBar = findViewById(R.id.progressBar);
    }

    public void download(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //오래 걸리는 일
                for(int i = 0; i<=100; i++){
                    try{
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    final int percent = i;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //ui 갱신
                            mTextView.setText(percent + "%");
                            mProgressBar.setProgress(percent);
                        }
                    });

                }
            }
        }).start();
    }
}
