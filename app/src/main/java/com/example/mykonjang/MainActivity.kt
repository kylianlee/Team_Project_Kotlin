package com.example.mykonjang

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.mykonjang.databinding.ActivityMainBinding
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        val imgFragment = ShowFragment()
        fragment.replace(R.id.framelayout, imgFragment)
        fragment.commit()

        // 알림 기능
        var builder = NotificationCompat.Builder(this, "MY_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("알림 제목")
            .setContentText("알림 내용")
        val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
        val channel_name = "채널이름" // 채널 이름 설정
        val descriptionText = "설명글" // 채널 설명글 설정
        val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
        val channel = NotificationChannel(channel_id, channel_name, importance).apply {
            description = descriptionText
        }

        // 만든 채널 정보를 시스템에 등록
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
        notificationManager.notify(1002, builder.build())
    }
}