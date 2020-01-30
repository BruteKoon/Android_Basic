package com.example.implicitintentexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialPhone(View view) {
        EditText editText = findViewById(R.id.phone_number_edit);
        dialPhoneNumber(editText.getText().toString());
    }

    public void dialPhoneNumber(String phoneNumber){
        // 암시적 인텐트인 ACTION_DIAL 설정
        Intent intent = new  Intent(Intent.ACTION_DIAL);

        //URI 형태의 전화번호 데이터로 설정
        intent.setData(Uri.parse("tel:" + phoneNumber));

        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }
    }
}
