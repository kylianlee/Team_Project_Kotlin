package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentScholarBinding

class ScholarFragment(scholarName: String) : Fragment() {
    var binding: FragmentScholarBinding? = null
    lateinit var myDBHelper: MyDBHelper
    var scholar = scholarName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScholarBinding.inflate(layoutInflater, container, false)
        init()
        getRecord(scholar)
        return binding!!.root
    }

    fun getRecord(scholarName: String) {
        myDBHelper.getScholarRecord(scholarName)
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