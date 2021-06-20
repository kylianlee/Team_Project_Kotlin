package com.example.mykonjang

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentMainBinding

var flagtf:Boolean = false

class MainFragment : Fragment() {
    var binding: FragmentMainBinding? = null
    lateinit var myDBHelper: MyDBHelper
    var selectMonth = 0
    var selectDay = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        init()
        getMonthRecord()
        getListRecord()
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getMonthRecord() {
        val cal = Calendar.getInstance()
        val month = (cal.get(Calendar.MONTH) + 1).toString()
        myDBHelper.getMainMonthRecord(month)
    }

    fun getMonthRecord(month: Int) {
        myDBHelper.getMainMonthRecord(month.toString())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getListRecord() {
        val cal = Calendar.getInstance()
        val month = (cal.get(Calendar.MONTH) + 1).toString()
        val day = (cal.get(Calendar.DATE)).toString()
        myDBHelper.getMainListRecord(month, day)
    }

    fun getListRecord(month: Int, day: Int) {
        myDBHelper.getMainListRecord(month.toString(), day.toString())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun init() {
        val cal = Calendar.getInstance()
        val fragment = requireActivity().supportFragmentManager.beginTransaction()

        myDBHelper = MyDBHelper(this.context)
        fragment.addToBackStack(null)
        binding!!.apply {
            // 왼쪽 상단에 텍스트 클릭하면 메인으로 이동
            mainText.setOnClickListener {
                val showfragment = ShowFragment()
                fragment.replace(R.id.framelayout, showfragment)
                fragment.commit()
            }

            // 왼쪽 상단에 아이콘 클릭하면 메인으로 이동
            imageView4.setOnClickListener {
                val showfragment = ShowFragment()
                fragment.replace(R.id.framelayout, showfragment)
                fragment.commit()
            }

            // 우측 상단에 검색 버튼 누르면 검색 화면으로 이동
            findBtn.setOnClickListener {
                val findfragment = FindFragment()
                fragment.replace(R.id.framelayout, findfragment)
                fragment.commit()
            }

            // 우측 상단에 사용자 버튼 누르면 사용자 화면으로 이동
            userBtn.setOnClickListener {
                Toast.makeText(context, "로그아웃 됨", Toast.LENGTH_SHORT).show()
                flagtf = false
                val showFragment = ShowFragment()
                fragment.replace(R.id.framelayout, showFragment)
                fragment.commit()
            }

            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                monthText.text = (month + 1).toString() + "월 장학 목록"
                selectMonth = month + 1
                selectDay = dayOfMonth
                getMonthRecord(selectMonth)
                getListRecord(selectMonth, selectDay)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}