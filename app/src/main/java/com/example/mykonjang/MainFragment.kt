package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        init()
        return binding!!.root
    }

    private fun init() {
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

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}