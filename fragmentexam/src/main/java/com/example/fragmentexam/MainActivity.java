package com.example.fragmentexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프래그먼트 조작을 위해 프래그먼트 매니저 얻음
        FragmentManager fragmentManager = getSupportFragmentManager();

        ColorFragment colorFragment = (ColorFragment) fragmentManager.findFragmentById(R.id.color_fragment);
        colorFragment.setColor(Color.BLUE);
    }

    public void change(View view) {
    }
}
