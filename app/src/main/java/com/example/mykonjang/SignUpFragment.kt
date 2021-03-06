package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var authDBHelper: AuthDBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        authDBHelper = AuthDBHelper(this.context)
        var flag1 = false
        var id: String?
        var pw: String?
        var pwCheck: String?
        var score: Double?

        binding.apply {
            btnId.setOnClickListener {
                id = binding.id!!.text.toString()
                if (id!!.length < 8 || id!!.length > 14) {
                    Toast.makeText(
                        this@SignUpFragment.context,
                        "8자 이상 14자 이하의 아이디를 입력해 주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.id.text.clear()
                }
                if (!authDBHelper.isValidId(id!!)) {
                    Toast.makeText(
                        this@SignUpFragment.context,
                        "사용 가능한 아이디입니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    flag1 = true
                } else
                    Toast.makeText(
                        this@SignUpFragment.context,
                        "이미 사용 중인 아이디입니다.",
                        Toast.LENGTH_SHORT
                    ).show()
            }

            btn.setOnClickListener {
                id = binding.id!!.text.toString()
                pw = binding.pw!!.text.toString()
                pwCheck = binding.pw!!.text.toString()
                score = binding.score!!.text.toString().toDouble()
                if (!flag1)
                    Toast.makeText(
                        this@SignUpFragment.context,
                        "아이디 중복확인을 하십시오.",
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    if (pw!!.length < 8 || pw!!.length > 14) {
                        Toast.makeText(
                            this@SignUpFragment.context,
                            "비밀번호의 길이를 확인하십시오.",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.pw!!.text.clear()
                    } else if (pw!! != pwCheck!!) {
                        Toast.makeText(
                            this@SignUpFragment.context,
                            "비밀번호가 일치하지 않습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.pw!!.text.clear()
                        binding.pwcheck!!.text.clear()
                    } else {
                        val user = AuthData(0, id!!, pw!!, score!!)
                        val flag = authDBHelper.insertUser(user)
                        if (flag) {
                            Toast.makeText(
                                this@SignUpFragment.context,
                                "회원 가입 되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val loginFragment = LogInFragment()
                            fragment.replace(R.id.framelayout, loginFragment)
                            fragment.commit()
                        } else {
                            Toast.makeText(
                                this@SignUpFragment.context,
                                "회원 가입에 실패했습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.id!!.text.clear()
                            binding.pw!!.text.clear()
                        }
                    }
                }
            }
        }
    }
}