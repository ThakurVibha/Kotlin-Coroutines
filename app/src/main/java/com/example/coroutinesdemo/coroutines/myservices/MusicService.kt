package com.example.coroutinesdemo.coroutines.myservices

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.coroutines.MainActivity


class MusicService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var input = intent!!.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, MusicActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        var CHANNEL_ID = "exampleServiceChannel"
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        //do heavy work on a background thread
        //stopSelf();


        //do heavy work on a background thread
        //stopSelf();
        return START_NOT_STICKY
    }
}