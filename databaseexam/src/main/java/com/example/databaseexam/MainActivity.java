package com.example.databaseexam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Memo Activity 시작
                startActivityForResult(new Intent(MainActivity.this, MemoActivity.class), REQUEST_CODE_INSERT);
            }
        });

        ListView listview = findViewById(R.id.memo_list);
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        Cursor cursor = dbHelper.getReadableDatabase().query(MemoContract.MemroEntry.TABLE_NAME,
                null,null,null,null,null,null);

        mAdapter = new MemoAdapter(this, cursor);
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);

                //클릭할 시점의 아이템을 얻음
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                //커서에서 제목과 내용을 얻음
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemroEntry.COLUMN_NAME_TABLE));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemroEntry.COLUMN_NAME_CONTENTS));

                //인텐트에 id와 함께 저장
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);

                //MemoActivity와 시작
                startActivityForResult(intent, REQUEST_CODE_INSERT);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long deleteId = id;

                //삭제 할것인지 다이얼로그 표시
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("메모삭제");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = MemoDbHelper.getInstance(MainActivity.this).getWritableDatabase();
                        int deletedCount = db.delete(MemoContract.MemroEntry.TABLE_NAME, MemoContract.MemroEntry._ID + "=" + deleteId, null);
                        if(deletedCount == 0){
                            Toast.makeText(MainActivity.this, "삭제에 문제가 발생하였다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mAdapter.swapCursor(getMemoCursor());
                            Toast.makeText(MainActivity.this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                return true;

                            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //메모가 정상적으로 삽입됬으면 메모 목록 갱신
        if(requestCode == REQUEST_CODE_INSERT && resultCode== RESULT_OK){
            mAdapter.swapCursor(getMemoCursor());
        }
    }

    private Cursor getMemoCursor(){
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(MemoContract.MemroEntry.TABLE_NAME, null,null,null,null,null,MemoContract.MemroEntry._ID+" DESC");
    }




    private static class MemoAdapter extends CursorAdapter{

        public MemoAdapter(Context context, Cursor c){
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemroEntry.COLUMN_NAME_TABLE)));
        }


    }
}
