package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mykonjang.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    lateinit var binding: FragmentLogInBinding
    lateinit var authDBHelper: AuthDBHelper
    val myViewModel: MyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        init()
        return binding!!.root
    }

    private fun init() {
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        authDBHelper = AuthDBHelper(this.context)
        binding.apply {
            login.setOnClickListener {
                val idStr = binding.id.text.toString()
                val pwStr = binding.pw.text.toString()
                val user = arrayOf(idStr, pwStr)
                if (authDBHelper.isSignedUp(user)) {
                    myViewModel.setLiveData(true)
                    Toast.makeText(this@LogInFragment.context, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    val mainFragment = MainFragment()
                    fragment.replace(R.id.framelayout, mainFragment)
                    fragment.commit()
                } else {
                    Toast.makeText(this@LogInFragment.context, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    binding.id.text.clear()
                    binding.pw.text.clear()
                }
            }
            signup.setOnClickListener {
                val signUpFragment = SignUpFragment()
                fragment.replace(R.id.framelayout, signUpFragment)
                fragment.commit()
            }
        }
    }
}