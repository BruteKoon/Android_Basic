package com.example.okhttpexam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //소스를 확인하고 싶은 사이트 주소
        new HttpAsyncTask().execute("https://goo.gl/eIXu9l");
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String>{

        //okhttp 클라이언트
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String strUrl = params[0];
            try{
                //요청
                Request request = new Request.Builder().url(strUrl).build();

                //응답
                Response response = client.newCall(request).execute();
                Log.d(TAG, "onCreate : " + response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                Log.d("HttpAsyncTask", s);
            }
        }
    }
}
