package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentScholarBinding

class ScholarFragment : Fragment() {
    var binding: FragmentScholarBinding? = null
    lateinit var myDBHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScholarBinding.inflate(layoutInflater, container, false)
        init()
        return binding!!.root
    }

    fun init() {
        myDBHelper = MyDBHelper(this.context)
        binding!!.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}