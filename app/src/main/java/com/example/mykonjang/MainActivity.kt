package com.example.mykonjang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mykonjang.databinding.ActivityMainBinding
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDB()
        init()
    }

    private fun initDB() {
        val dbfile = getDatabasePath("scholar.db")
        // dbfile이 존재하는지 확인
        if(!dbfile.parentFile.exists()) {
            dbfile.parentFile.mkdir() // directory 생성
        }

        if(!dbfile.exists()) {
            val file = resources.openRawResource(R.raw.scholar) // file 가져옴
            val fileSize = file.available() // file 크기 정보
            val buffer = ByteArray(fileSize)
            file.read(buffer) // 읽어서 버퍼에 저장
            file.close()
            dbfile.createNewFile() // file 생성
            val output = FileOutputStream(dbfile)
            output.write(buffer)
            output.close()
        }
    }

    private fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        val imgFragment = ShowFragment()
        fragment.replace(R.id.framelayout, imgFragment)
        fragment.commit()
    }
}