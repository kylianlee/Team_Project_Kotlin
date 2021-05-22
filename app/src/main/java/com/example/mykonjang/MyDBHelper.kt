package com.example.mykonjang

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object { // 변수 선언
        val DB_NAME = "scholar.db" // db 이름
        val DB_VERSION = 1 // version 정보
        val TABLE_NAME = "scholardata" // 테이블 네임
        val PID = "PID"
        val PSCHOLARNAME = "SCHOLARNAME"
        val PSCHOLARLIST = "SCHOLARLIST"
        val PLIKE = "LIKE"
        val PGRADE = "GRADE"
    }

    private fun showRecord(cursor: Cursor) {
        cursor.moveToFirst()
        val attrcount = cursor.columnCount
        val activity = context as MainActivity
        val activity2 =
            activity?.supportFragmentManager.findFragmentById(R.id.framelayout) as MainFragment
        activity2.binding?.RecyclerView1?.removeAllViewsInLayout() // 월별 장학 리스트

        if (cursor.count == 0) return // 반환할 데이터가 없으면 종료 (없으면 앱 종료됨)
        
        // 데이터 RecyclerView1에 추가
    }

    // db가 처음 생성될 때
    override fun onCreate(db: SQLiteDatabase?) {
        // 테이블 생성
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$PID integer primary key autoincrement, " + // autoincrement : 자동으로 증가되는 값
                "$PSCHOLARNAME text, " +
                "$PSCHOLARLIST text, " +
                "$PLIKE boolean" +
                "$PGRADE float);"
        db!!.execSQL(create_table) // null이 아니면 테이블 생성
    }

    // db 버전이 바뀌었을 때
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;" // 테이블 삭제
        db!!.execSQL(drop_table) // null이 아니면 테이블 삭제
        onCreate(db) // 다시 생성
    }
}
