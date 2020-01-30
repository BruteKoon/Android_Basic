package com.example.for_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE = 1000;
    private EditText mNameEditText;
    private EditText mAgeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면에 Layout 처리
        setContentView(R.layout.activity_main);

        //이름, 나이
        mNameEditText = findViewById(R.id.name_edit);
        mAgeEditText = findViewById(R.id.age_edit);

        //버튼 이벤트 처리
        findViewById(R.id.submit_button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //Second Activity로 전환하려는 Intent
        Intent intent = new Intent(this, SecondActivity.class);

        // 이름, 나이 가져와서 Intent에 추가
        intent.putExtra("name", mNameEditText.getText().toString());
        intent.putExtra("age", mAgeEditText.getText().toString());

        //intent를 통해 다른 Activity 시작
        startActivityForResult(intent, REQUEST_CODE);
    }

    // SecondActivity에서 돌려받은 결과를 처리하는 콜백
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //결과 받고 토스트로 표시
            String result = data.getStringExtra("result");
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }


    }
}
