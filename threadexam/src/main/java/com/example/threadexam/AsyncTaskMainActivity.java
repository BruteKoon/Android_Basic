package com.example.threadexam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskMainActivity extends AppCompatActivity {
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private DownLoadTask mDownLoadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView);
        mProgressBar = findViewById(R.id.progressBar);

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDownLoadTask != null && !mDownLoadTask.isCancelled()){
                    mDownLoadTask.cancel(true);
                }
            }
        });


    }

    public void download(View view) {
        mDownLoadTask = new DownLoadTask();
        mDownLoadTask.execute();
    }

    class DownLoadTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //오래 걸리는 일
            for(int i = 0; i<=100; i++){
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                final int percent = i;

                //UI 갱신요청
                publishProgress(percent);

                //취소됨
                if(isCancelled()) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //UI 갱신요청
            mTextView.setText(values[0] + "%");
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsyncTaskMainActivity.this, "취소됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(AsyncTaskMainActivity.this, "완료됨", Toast.LENGTH_SHORT).show();
        }
    }
}
