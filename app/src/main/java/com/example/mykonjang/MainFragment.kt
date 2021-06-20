package com.example.mykonjang

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mykonjang.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    var binding: FragmentMainBinding? = null
    lateinit var myDBHelper: MyDBHelper
    var flag = false
    val myViewModel:MyViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        init()
        getALLRecord()
        return binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getALLRecord() {
        val cal = Calendar.getInstance()
        val month = (cal.get(Calendar.MONTH)+1).toString()
        myDBHelper.getMainMonthRecord(month)
    }

    private fun init() {
        myDBHelper = MyDBHelper(this.context)
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        binding!!.apply {
            // 왼쪽 상단에 텍스트 클릭하면 메인으로 이동
            mainText.setOnClickListener {
                val showfragment = ShowFragment()
                fragment.replace(R.id.framelayout, showfragment)
                fragment.commit()
            }

            // 왼쪽 상단에 아이콘 클릭하면 메인으로 이동
            imageView4.setOnClickListener{
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
                myViewModel.selectedNum.observe(viewLifecycleOwner, Observer{
                    flag = it
                })
                if(flag){
                    Toast.makeText(context, "로그아웃 됨", Toast.LENGTH_SHORT).show()
                }
                else {
                    val logInFragment = LogInFragment()
                    fragment.replace(R.id.framelayout, logInFragment)
                    fragment.commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}