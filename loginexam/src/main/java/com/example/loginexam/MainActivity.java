package com.example.loginexam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private CheckBox mSaveCheckBox;

    // 설정 정보 저장 객체
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailEditText = findViewById(R.id.email_edit);
        mSaveCheckBox = findViewById(R.id.save_check);

        //Preference 객체 초기화
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean isChecked = mPreferences.getBoolean("save", false);
        mSaveCheckBox.setChecked(isChecked);
        if(isChecked){
            String email = mPreferences.getString("email", "");
            mEmailEditText.setText(email);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //SharedPreference의 수정 가능한 객체 얻기
        SharedPreferences.Editor editor = mPreferences.edit();

        //저장할 데이터
        editor.putBoolean("save", mSaveCheckBox.isChecked());
        editor.putString("email", mEmailEditText.getText().toString());

        //저장
        editor.apply();
    }
}
