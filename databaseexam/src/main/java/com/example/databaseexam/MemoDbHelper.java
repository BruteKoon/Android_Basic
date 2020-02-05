package com.example.databaseexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;

    public static synchronized MemoDbHelper getInstance(Context context){
        //액티비티의 context가 메모리 릭을 발생할 수 있으므로
        //application context를 사용하는것이 좋다.
        if (sInstance == null){
            sInstance = new MemoDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    //DB의 버전으로 1부터 시작하고 스키마가 변경될때 숫자를 올린다.
    private static final int DB_VERSION = 1;

    //DB 파일명
    private static final String DB_NAME = "Memo.db";

    //테이블 생성 SQL 문
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    MemoContract.MemroEntry.TABLE_NAME,
                    MemoContract.MemroEntry._ID,
                    MemoContract.MemroEntry.COLUMN_NAME_TABLE,
                    MemoContract.MemroEntry.COLUMN_NAME_CONTENTS);

    //테이블 삭제 SQL 문
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MemoContract.MemroEntry.TABLE_NAME;

    //생성자를 private로 직접 인스턴트화 방지하고
    //getInstance를 통해 인스턴스 얻어야함
    private MemoDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DB 스키마가 변경될 때 여기서 데이터를 백업하고
        //테이블을 삭제 후 재생성 및 데이터 복원
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
