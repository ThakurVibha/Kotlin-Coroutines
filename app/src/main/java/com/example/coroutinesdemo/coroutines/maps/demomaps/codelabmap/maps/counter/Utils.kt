package com.example.countdown.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object  Utils {
    var CHANNEL_ID = "ForegroundService Kotlin"
    var NEW_CHANNEL_ID="id"
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Counter channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun newNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NEW_CHANNEL_ID, "Counter channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}