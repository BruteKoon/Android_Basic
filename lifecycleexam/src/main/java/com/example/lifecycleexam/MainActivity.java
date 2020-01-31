package com.example.lifecycleexam;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";


    private TextView mLevelText;
    private TextView mScoreText;
    private int mLevel = 0;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLevelText = findViewById(R.id.level_text);
        mScoreText = findViewById(R.id.score_text);

        if(savedInstanceState == null){

        }
        else{
            mLevel = savedInstanceState.getInt(STATE_LEVEL);
            mScore = savedInstanceState.getInt(STATE_SCORE);
            mLevelText.setText("레벨 "+ mLevel);
            mScoreText.setText("점수 " + mScore);
        }

    }

    public void onLevelUp(View view) {
        mLevel++;
        mLevelText.setText("레벨 "+ mLevel);
    }

    public void onScoreUp(View view) {
        mScore++;
        mScoreText.setText("점수 " + mScore);
    }

    /**
     * 핸드폰 회전 시 데이터 유지시키기 위함
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //상태 저장
        outState.putInt(STATE_SCORE, mScore);
        outState.putInt(STATE_LEVEL, mLevel);

        super.onSaveInstanceState(outState);
    }
}
