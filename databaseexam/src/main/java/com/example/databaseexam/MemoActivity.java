package com.example.databaseexam;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        mTitleEditText = findViewById(R.id.title_edit);
        mContentEditText = findViewById(R.id.contents_edit);
        Intent intent = getIntent();
        if (intent != null){
            mMemoId = intent.getLongExtra("id", -1);
            String title = intent.getStringExtra("title");
            String contents = intent.getStringExtra("contents");
            mTitleEditText.setText(title);
            mContentEditText.setText(contents);
        }
    }

    @Override
    public void onBackPressed() {
        //DB에 저장하는 처리
        String title = mTitleEditText.getText().toString();
        String contents = mContentEditText.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemroEntry.COLUMN_NAME_TABLE, title);
        contentValues.put(MemoContract.MemroEntry.COLUMN_NAME_CONTENTS, contents);
        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();
        if(mMemoId == -1) {
            long newRowId = db.insert(MemoContract.MemroEntry.TABLE_NAME, null, contentValues);

            if (newRowId == -1) {
                Toast.makeText(this, "저장에 문제 발생", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //기존 메모 내용을 업데이트 처리
            int count = db.update(MemoContract.MemroEntry.TABLE_NAME, contentValues, MemoContract.MemroEntry._ID + "=" + mMemoId, null);

            if(count == 0){
                Toast.makeText(this, "수정에 문제가 발생하였다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "메모가 수정되었다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }

        //뒤로가기 원래의 동작 실행됨
        super.onBackPressed();
    }
}
