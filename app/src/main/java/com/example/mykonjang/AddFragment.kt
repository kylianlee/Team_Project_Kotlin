package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentAddBinding
import java.util.*

class AddFragment : Fragment() {
    var binding: FragmentAddBinding? = null
    lateinit var myDBHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        init()
        getALLRecord()
        return binding!!.root
    }

    // 전체 데이더 보여줌
    fun getALLRecord() {
        myDBHelper.getAllRecord()
    }

    // 입력 내용 삭제
    fun clearEditText() {
        binding?.apply {
            pScholarName.text.clear()
            pScholarType.text.clear()
            pScholarList.text.clear()
            !pLike.isChecked
            pGrade.text.clear()
            pMonth.text.clear()
        }
    }

    private fun init() {
        myDBHelper = MyDBHelper(this.context)
        binding?.apply {
            insertbtn.setOnClickListener {
                val scholarName = pScholarName.text.toString()
                val scholarType = pScholarType.text.toString()
                val scholarList = pScholarList.text.toString()
                val like = pLike.isChecked
                val grade = pGrade.text.toString().toFloat()
                val month = pMonth.text.toString()
                val scholardata =
                    ScholarData(0, scholarName, scholarType, scholarList, like, grade, month)

                val result = myDBHelper.insertProduct(scholardata)
                if (result) {
                    getALLRecord() // 전체 데이터 다시 보여줌
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data INSERT SUCCESS",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data INSERT FAILED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                clearEditText()
            }

            deletebtn.setOnClickListener {
                val pid = pId.text.toString()
                val result = myDBHelper.deleteProduct(pid)
                if (result) {
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data DELETE SUCCESS",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data DELETE FAILED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                getALLRecord()
                clearEditText()
            }

            updatebtn.setOnClickListener {
                val pid = pId.text.toString().toInt()
                val scholarName = pScholarName.text.toString()
                val scholarType = pScholarType.text.toString()
                val scholarList = pScholarList.text.toString()
                val like = pLike.isChecked
                val grade = pGrade.text.toString().toFloat()
                val month = pMonth.text.toString()
                val scholardata =
                    ScholarData(pid, scholarName, scholarType, scholarList, like, grade, month)

                val result = myDBHelper.updateProduct(scholardata)
                if (result) {
                    getALLRecord() // 전체 데이터 다시 보여줌
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data UPDATE SUCCESS",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@AddFragment.context,
                        "Data UPDATE FAILED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                clearEditText()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}