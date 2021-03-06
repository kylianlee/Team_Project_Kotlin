package com.example.mykonjang

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykonjang.databinding.FragmentScholarBinding


class MyDBHelper(val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FindAdapter
    private lateinit var mainMonthAdapter: MainMonthAdapter
    private lateinit var mainListAdapter: MainListAdapter
    var binding: FragmentScholarBinding? = null

    companion object { // 변수 선언
        const val DB_NAME = "scholar.db" // db 이름
        const val DB_VERSION = 1 // version 정보
        const val TABLE_NAME = "scholardata" // 테이블 네임
        const val PID = "PID"
        const val PSCHOLARNAME = "SCHOLARNAME"
        const val PSCHOLARTYPE = "SCHOLARTYPE"
        const val PSCHOLARLIST = "SCHOLARLIST"
        const val PLIKE = "LIKE"
        const val PGRADE = "GRADE"
        const val PSCHOLARMONTH = "SCHOLARMONTH"
    }

    /* AddFragment 데이터 추가 함수 */
    fun insertProduct(scholar: ScholarData): Boolean {
        val values = ContentValues() // ContentValues 객체
        values.put(PSCHOLARNAME, scholar.scholarName) // values에 값 넣음
        values.put(PSCHOLARTYPE, scholar.scholarType)
        values.put(PSCHOLARLIST, scholar.scholarList)
        values.put(PLIKE, scholar.like)
        Log.i("scholarLike", scholar.like.toString())
        values.put(PGRADE, scholar.grade)
        values.put(PSCHOLARMONTH, scholar.scholarMonth)

        val db = writableDatabase // 데이터 읽고 쓸수 있게 함
        val flag = db.insert(TABLE_NAME, null, values) > 0 // 데이터 삽입
        db.close()
        return flag
    }

    /* AddFragment 데이터 삭제 함수 */
    fun deleteProduct(pid: String): Boolean {
        val strsql = "select * from $TABLE_NAME where $PID = '$pid';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0
        if (flag) {
            cursor.moveToFirst() // cursor의 첫번째 데이터만 지움 (id가 중복되지 않으므로)
            db.delete(TABLE_NAME, "$PID=?", arrayOf(pid))
        }
        cursor.close()
        db.close()

        return flag
    }

    /* AddFragment 데이터 갱신 함수 */
    fun updateProduct(scholar: ScholarData): Boolean {
        val pId = scholar.pId
        val strsql = "select * from $TABLE_NAME where $PID = '$pId';"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0
        if (flag) {
            cursor.moveToFirst() // cursor의 첫번째 데이터만 지움 (id가 중복되지 않으므로)
            val values = ContentValues()
            values.put(PSCHOLARNAME, scholar.scholarName) // values에 값 넣음
            values.put(PSCHOLARTYPE, scholar.scholarType)
            values.put(PSCHOLARLIST, scholar.scholarList)
            values.put(PLIKE, scholar.like)
            values.put(PGRADE, scholar.grade)
            values.put(PSCHOLARMONTH, scholar.scholarMonth)
            db.update(TABLE_NAME, values, "$PID=?", arrayOf(pId.toString()))
        }
        cursor.close()
        db.close()

        return flag
    }

    /* AddFragment */
    private fun showAddRecord(cursor: Cursor) {
        cursor.moveToFirst()
        val attrcount = cursor.columnCount // 개수
        val activity = context as MainActivity
        val activity2 =
            activity.supportFragmentManager.findFragmentById(R.id.framelayout) as AddFragment
        activity2.binding?.tableLayout?.removeAllViewsInLayout()

        // 타이틀 만들기 (동적 생성이기 때문에 크기를 줘야됨)
        val tablerow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        tablerow.layoutParams = rowParam

        val viewParam = TableRow.LayoutParams(0, 200, 1f)
        for (i in 0 until attrcount) { // 속성값 설정
            val textView = TextView(activity) // textview 생성
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 13.0f
            textView.gravity = Gravity.CENTER
            tablerow.addView(textView)
        }
        activity2.binding?.tableLayout?.addView(tablerow) // xml에 row 추가됨

        if (cursor.count == 0) return // 반환할 데이터가 없으면 종료 (없으면 앱 종료됨)

        // 레코드 추가하기
        do {
            val row = TableRow(activity)
            row.layoutParams = rowParam

            // row를 선택할 때마다 plainText에 뜸
            row.setOnClickListener {
                for (i in 0 until attrcount) {
                    val textView = row.getChildAt(i) as TextView // TextView 객체
                    when (textView.tag) {
                        0 -> activity2.binding?.pId?.setText(textView.text)
                        1 -> activity2.binding?.pScholarName?.setText(textView.text)
                        2 -> activity2.binding?.pScholarType?.setText(textView.text)
                        3 -> activity2.binding?.pScholarList?.setText(textView.text)
                        4 -> activity2.binding?.pLike?.isChecked =
                            textView.text.toString() == "1"
                        5 -> activity2.binding?.pGrade?.setText(textView.text)
                        6 -> activity2.binding?.pMonth?.setText(textView.text)
                    }
                }
            }

            for (i in 0 until attrcount) { // 속성값 설정
                val textView = TextView(activity) // textview 생성
                textView.tag = i // 태그 값 (id값 식별하기 위한 변수)
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.textSize = 13.0f
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }
            activity2.binding?.tableLayout?.addView(row) // xml에 row 추가됨
        } while (cursor.moveToNext()) // moveToNext : 커서의 다음으로 이동 -> 다음에 읽을게 있으면
    }

    /* FindFragment */
    private fun showFindRecord(cursor: Cursor) {
        val scData = mutableListOf<ScholarData>()
        cursor.moveToFirst()
        val attrcount = cursor.columnCount // 개수
        val activity = context as MainActivity
        val activity2 =
            activity.supportFragmentManager.findFragmentById(R.id.framelayout) as FindFragment
        val activity3 = activity.supportFragmentManager.beginTransaction().addToBackStack(null)

        if (cursor.count != 0) {
            do {
                for (i in 0 until attrcount) {
                    if (i % 7 == 6) {
                        val tf: Boolean = cursor.getString(i - 2) == "1"
                        scData.apply {
                            add(
                                ScholarData(
                                    cursor.getString(i - 6).toInt(),
                                    cursor.getString(i - 5),
                                    cursor.getString(i - 4),
                                    cursor.getString(i - 3),
                                    tf,
                                    cursor.getString(i - 1).toFloat(),
                                    cursor.getString(i)
                                )
                            )
                        }
                    }
                }
            } while (cursor.moveToNext())
        }

        adapter = FindAdapter(scData)

        adapter.itemClickListener = object : FindAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: FindAdapter.ViewHolder,
                view: View,
                data: ScholarData,
                position: Int
            ) {
                if (holder.findCheckBox.isChecked) {
                    data.like = true
                    !holder.findCheckBox.isChecked
                } else {
                    data.like = false
                    holder.findCheckBox.isChecked
                }
                updateProduct(data)
            }
        }

        adapter.itemClickListener2 = object : FindAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: FindAdapter.ViewHolder,
                view: View,
                data: ScholarData,
                position: Int
            ) {
                binding.apply {
                    val scholarFragment = ScholarFragment(data.scholarName)
                    activity3.replace(R.id.framelayout, scholarFragment)
                    activity3.commit()
                }
            }
        }

        recyclerView = activity2.binding?.RecyclerView!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(activity2.context, LinearLayoutManager.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }

    /* 데이터 표시 */
    fun getAllRecord() {
        val strsql = "select * from $TABLE_NAME order by $PID;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        showAddRecord(cursor)
        cursor.close()
        db.close()
    }

    // DB가 처음 생성될 때
    override fun onCreate(db: SQLiteDatabase?) {
        // 테이블 생성
        val createTable = "create table if not exists $TABLE_NAME(" +
                "$PID integer primary key autoincrement, " + // autoincrement : 자동으로 증가되는 값
                "$PSCHOLARNAME text, " +
                "$PSCHOLARTYPE text, " +
                "$PSCHOLARLIST text, " +
                "$PLIKE boolean, " +
                "$PGRADE float, " +
                "$PSCHOLARMONTH text);"
        db!!.execSQL(createTable) // null이 아니면 테이블 생성
    }

    // DB 버전이 바뀌었을 때
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table if exists $TABLE_NAME;" // 테이블 삭제
        db!!.execSQL(dropTable) // null이 아니면 테이블 삭제
        onCreate(db) // 다시 생성
    }

    // 장학 분류 Spinner 선택 함수
    fun selectScholarType(scholarItem: Any, gradeItem: Any): Boolean {
        var strsql = ""
        strsql = if (scholarItem == "전체") {
            if(gradeItem == "전체")
                "select * from $TABLE_NAME;"
            else
                "select * from $TABLE_NAME where $PGRADE >= '$gradeItem';"
        } else {
            if(gradeItem == "전체")
                "select * from $TABLE_NAME where $PSCHOLARTYPE = '$scholarItem';"
            else
                "select * from $TABLE_NAME where $PSCHOLARTYPE = '$scholarItem' and $PGRADE >= '$gradeItem';"
        }

        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0

        showFindRecord(cursor)
        cursor.close()
        db.close()

        return flag
    }

    // 장학 이름으로 찾음
    fun findScholar(name: String): Boolean {
        val strsql = "select * from $TABLE_NAME where $PSCHOLARNAME like '%$name%';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count != 0

        showFindRecord(cursor)
        cursor.close()
        db.close()

        return flag
    }

    /* MainFragment의 월별 리스트 표시 */
    fun getMainMonthRecord(month: String) {
        val strsql = "select * from $TABLE_NAME order by $PSCHOLARMONTH;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        showMainMonthList(cursor, month)
        cursor.close()
        db.close()
    }

    /* MainFragment 월별 리스트 RecyclerView */
    private fun showMainMonthList(cursor: Cursor, month: String) {
        val scData = mutableListOf<ScholarData>()
        cursor.moveToFirst()
        val attrCount = cursor.columnCount // 개수
        val activity = context as MainActivity
        val activity2 =
            activity.supportFragmentManager.findFragmentById(R.id.framelayout) as MainFragment
        val activity3 = activity.supportFragmentManager.beginTransaction().addToBackStack(null)

        if (cursor.count != 0) {
            do {
                for (i in 0 until attrCount) {
                    if (i % 7 == 6) {
                        val tmp = cursor.getString(i).split(".")
                        if (tmp[0] == month) {
                            val tf: Boolean = cursor.getString(i - 2) == "1"
                            scData.apply {
                                add(
                                    ScholarData(
                                        cursor.getString(i - 6).toInt(),
                                        cursor.getString(i - 5),
                                        cursor.getString(i - 4),
                                        cursor.getString(i - 3),
                                        tf,
                                        cursor.getString(i - 1).toFloat(),
                                        cursor.getString(i)
                                    )
                                )
                            }
                        }
                    }
                }
            } while (cursor.moveToNext())
        }

        mainMonthAdapter = MainMonthAdapter(scData)

        mainMonthAdapter.itemClickListener = object : MainMonthAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: MainMonthAdapter.ViewHolder,
                view: View,
                data: ScholarData,
                position: Int
            ) {
                binding.apply {
                    val scholarFragment = ScholarFragment(data.scholarName)
                    activity3.replace(R.id.framelayout, scholarFragment)
                    activity3.commit()
                }
            }
        }

        recyclerView = activity2.binding?.RecyclerView1!!
        recyclerView.adapter = mainMonthAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(activity2.context, LinearLayoutManager.VERTICAL, false)
        mainMonthAdapter.notifyDataSetChanged()
    }

    /* MainFragment의 장학 리스트 표시 */
    fun getMainListRecord(month: String, day: String) {
        val strsql = "select * from $TABLE_NAME order by $PID;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        showMainList(cursor, month, day)
        cursor.close()
        db.close()
    }

    /* MainFragment 장학 리스트 RecyclerView */
    private fun showMainList(cursor: Cursor, month: String, day: String) {
        val scData = mutableListOf<ScholarData>()
        cursor.moveToFirst()
        val attrcount = cursor.columnCount // 개수
        val activity = context as MainActivity
        val activity2 =
            activity.supportFragmentManager.findFragmentById(R.id.framelayout) as MainFragment

        if (cursor.count != 0) {
            do {
                for (i in 0 until attrcount) {
                    if (i % 7 == 6) {
                        val tmp = cursor.getString(i).split(".")
                        val tf: Boolean = cursor.getString(i - 2) == "1"
                        if (tmp[0] == month && tmp[1] == day && tf) {
                            scData.apply {
                                add(
                                    ScholarData(
                                        cursor.getString(i - 6).toInt(),
                                        cursor.getString(i - 5),
                                        cursor.getString(i - 4),
                                        cursor.getString(i - 3),
                                        tf,
                                        cursor.getString(i - 1).toFloat(),
                                        cursor.getString(i)
                                    )
                                )
                            }
                        }
                    }
                }
            } while (cursor.moveToNext())
        }

        mainListAdapter = MainListAdapter(scData)

        recyclerView = activity2.binding?.RecyclerView2!!
        recyclerView.adapter = mainListAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(activity2.context, LinearLayoutManager.VERTICAL, false)
        mainListAdapter.notifyDataSetChanged()
    }

    /* 데이터 표시 */
    fun getScholarRecord(scholarName: String) {
        val strsql = "select * from $TABLE_NAME where $PSCHOLARNAME = '$scholarName';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        showScholarRecord(cursor)
        cursor.close()
        db.close()
    }

    private fun showScholarRecord(cursor: Cursor) {
        cursor.moveToFirst()
        val attrcount = cursor.columnCount // 개수
        val activity = context as MainActivity
        val activity2 =
            activity.supportFragmentManager.findFragmentById(R.id.framelayout) as ScholarFragment

        if (cursor.count != 0) {
            do {
                for (i in 0 until attrcount) {
                    var tf: Boolean
                    val textView = TextView(activity) // textview 생성
                    textView.text = cursor.getString(i)
                    when (i) {
                        1 -> activity2.binding?.pScholarName?.setText(cursor.getString(i))
                        2 -> activity2.binding?.pScholarType?.setText(cursor.getString(i))
                        3 -> activity2.binding?.pScholarList?.setText(cursor.getString(i))
                        4 -> {
                            tf = cursor.getString(i) == "1"
                            activity2.binding?.pLike?.isChecked = tf
                        }
                        5 -> activity2.binding?.pGrade?.setText(cursor.getString(i))
                        6 -> activity2.binding?.pMonth?.setText(cursor.getString(i))
                    }
                }
            } while (cursor.moveToNext())
        }
    }
}