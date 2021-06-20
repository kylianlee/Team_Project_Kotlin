package com.example.mykonjang

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val selectedNum = MutableLiveData<Boolean>()
    fun setLiveData(flag:Boolean){
        selectedNum.value = flag
    }
}