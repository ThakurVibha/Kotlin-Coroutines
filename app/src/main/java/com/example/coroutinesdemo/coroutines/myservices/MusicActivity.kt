package com.example.coroutinesdemo.coroutines.myservices

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.coroutinesdemo.R
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {
    var CHANNEL_ID = "exampleServiceChannel"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        createNotification()

    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var servicechannnel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Example service channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            var notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(servicechannnel)
        }
    }

    fun Onstop(view: View) {
        var stopService = Intent(this, MusicService::class.java)
        stopService(stopService)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onStart(view: View) {
        var input = edtInput.text.toString()
        var startService = Intent(this, MusicService::class.java)
        startService.putExtra("inputExtra", input)

        startForegroundService(startService)
    }
}