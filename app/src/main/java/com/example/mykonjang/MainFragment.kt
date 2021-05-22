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
        binding!!.apply {
            mainText.setOnClickListener {

            }

            findBtn.setOnClickListener {

            }

            userBtn.setOnClickListener {

            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}