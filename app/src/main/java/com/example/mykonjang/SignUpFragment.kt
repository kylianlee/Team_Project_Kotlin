package com.example.mykonjang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mykonjang.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
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

    private fun init(){
        authDBHelper = AuthDBHelper(this.context)
        var flag = false
        var id:String?
        var pw:String?
        var score:Double?

        binding.btnId.setOnClickListener {
            id = binding.id!!.text.toString()
            if(authDBHelper.isValidId(id!!))
                Toast.makeText(this.context, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this.context, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show()
        }
        binding.btn.setOnClickListener {
            id = binding.id!!.text.toString()
            pw = binding.pw!!.text.toString()
            score = binding.score!!.text.toString().toDouble()
            val user = AuthData(0, id!!, pw!!, score!!)
            val flag = authDBHelper.insertUser(user)
            if(flag)
                Toast.makeText(this.context, "회원 가입 되었습니다.", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this.context, "회원 가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}