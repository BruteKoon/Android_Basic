package com.example.countdownexam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private CountTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.count);
    }

    public void start(View view) {
        mTask = new CountTask();
        mTask.execute(0);
    }

    public void clear(View view) {
        mTask.cancel(true);
        mTextView.setText("0");
    }

    public class CountTask extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            do{
                //1초 쉬기
                try {
                    Thread.sleep(1000);

                    //0부터 10까지 1씩 증가
                    params[0]++;
                    //증가된 값을 텍스트뷰에 표시
                    publishProgress(params[0]);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            } while(params[0] < 10);
            return params[0];
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //텍스트뷰에 증가된 값 표시
            mTextView.setText(String.valueOf(progress[0]));
        }

        @Override
        protected void onPostExecute(Integer result) {
            //종료 시 마지막 값 텍스트 뷰 표시
            mTextView.setText(String.valueOf(result));
        }
    }
}
