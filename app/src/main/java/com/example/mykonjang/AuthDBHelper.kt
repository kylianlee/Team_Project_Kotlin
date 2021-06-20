package com.example.mykonjang

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AuthDBHelper(val context: Context?):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object{
        val DB_NAME = "auth.db" // db 이름
        val DB_VERSION = 1 // version 정보
        val TABLE_NAME = "authdata" // 테이블 네임
        val NUM = "NUM"
        val ID = "ID"
        val PASSWORD = "PASSWORD"
        val GRADE = "GRADE"
    }

    fun insertUser(user: AuthData): Boolean {
        val values = ContentValues()
        values.put(ID, user.id)
        values.put(PASSWORD, user.password)
        values.put(GRADE, user.grade)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values) > 0
        db.close()
        return flag
    }

    fun isSignedUp(user: Array<String>): Boolean {
        val id = user[0]
        val pw = user[1]
        val strsql = "select * from $TABLE_NAME where $ID = '$id' and $PASSWORD = '$pw'"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0
        cursor.close()
        db.close()
        return flag
    }

    fun isValidId(id: String):Boolean{
        val strsql = "select * from $TABLE_NAME where $ID = '$id'"
        val db = readableDatabase
        val cursor  = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        cursor.close()
        db.close()
        return flag
    }

    fun getUsersGrade(id: String): Double{
        val strsql = "select * from $TABLE_NAME where $ID = '$id'"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        if(cursor.count == 0)
            return -1.0
        val grade = cursor.getColumnIndex(GRADE).toDouble()
        cursor.close()
        db.close()
        return grade
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME(" +
                "$NUM integer primary key autoincrement, " +
                "$ID text, " +
                "$PASSWORD text, " +
                "$GRADE real);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }
}