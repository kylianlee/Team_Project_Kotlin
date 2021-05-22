package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentShowBinding

class ShowFragment : Fragment() {
    var binding: FragmentShowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            val fragment = requireActivity().supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)

            // showPanel 클릭하면 MainFragment로 이동
            showPanel.setOnClickListener {
                val mainfragment = MainFragment()
                fragment.replace(R.id.framelayout, mainfragment)
                fragment.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}