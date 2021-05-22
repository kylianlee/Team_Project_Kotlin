package com.example.mykonjang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mykonjang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        val imgFragment = ShowFragment()
        fragment.replace(R.id.framelayout, imgFragment)
        fragment.commit()
    }
}