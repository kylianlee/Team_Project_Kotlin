package com.example.mykonjang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mykonjang.databinding.FragmentFindBinding

class FindFragment : Fragment() {
    var binding: FragmentFindBinding? = null
    lateinit var myDBHelper: MyDBHelper
    val scholarTypes = arrayOf("전체", "교외", "교내")
    val gradeTypes = arrayOf("전체", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindBinding.inflate(layoutInflater, container, false)
        init()
        return binding!!.root
    }

    private fun init() {
        var scholarSelect = ""
        var gradeSelect = ""
        myDBHelper = MyDBHelper(this.context)
        binding!!.apply {
            scholarDiv?.adapter = this@FindFragment.context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    scholarTypes
                )
            } as SpinnerAdapter

            scholarDiv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    scholarSelect = scholarDiv.getItemAtPosition(position) as String
                    myDBHelper.selectScholarType(
                        scholarDiv.getItemAtPosition(position),
                        gradeSelect
                    )
                    Toast.makeText(
                        this@FindFragment.context,
                        "선택 : " + scholarDiv.getItemAtPosition(position),
                        Toast.LENGTH_SHORT
                    ).show();
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            gradeDiv?.adapter = this@FindFragment.context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    gradeTypes
                )
            } as SpinnerAdapter

            gradeDiv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    gradeSelect = gradeDiv.getItemAtPosition(position) as String
                    myDBHelper.selectScholarType(
                        scholarSelect,
                        gradeDiv.getItemAtPosition(position)
                    )
                    Toast.makeText(
                        this@FindFragment.context,
                        "선택 : " + gradeDiv.getItemAtPosition(position),
                        Toast.LENGTH_SHORT
                    ).show();
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            findBtn.setOnClickListener {
                val scholarName = findScholar.text.toString()
                val result = myDBHelper.findScholar(scholarName)
                if (result) {
                    Toast.makeText(this@FindFragment.context, "검색 완료", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@FindFragment.context, "검색 결과가 없습니다", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}