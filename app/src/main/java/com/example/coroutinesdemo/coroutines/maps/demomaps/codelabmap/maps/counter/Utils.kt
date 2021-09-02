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

//    fun showNotificationBackground(title: String, msg: String, context: Context): Notification {
//        val notificationIntent = Intent(context, MainActivity::class.java)
//        notificationIntent.apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent = Intent(context, MainActivity::class.java)
//        val buttonPendingIntent = PendingIntent.getBroadcast(context, 0, pendingIntent, 0)
//        val notification: Notification =
//            NotificationCompat.Builder(context, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(msg)
//                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
//                .addAction(
//                    R.drawable.sym_def_app_icon,
//                    "Tracking location in Background!!",
//                    buttonPendingIntent
//                ).build()
//        return notification
//    }

    //Notification channel
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
    //Notification channel
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