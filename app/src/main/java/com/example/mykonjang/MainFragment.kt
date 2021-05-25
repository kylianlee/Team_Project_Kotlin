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
            mainText.setOnClickListener {
                val showfragment = ShowFragment()
                fragment.replace(R.id.framelayout, showfragment)
                fragment.commit()
            }

            findBtn.setOnClickListener {
                val findfragment = FindFragment()
                fragment.replace(R.id.framelayout, findfragment)
                fragment.commit()
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