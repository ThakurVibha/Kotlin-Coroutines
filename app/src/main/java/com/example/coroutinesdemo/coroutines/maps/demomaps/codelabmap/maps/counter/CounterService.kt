package com.example.countdown.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.*
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.coroutinesdemo.R
import com.example.countdown.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*

class CounterService : Service() {
    private var alarmBinder: IBinder = CounterStartService()
    var myCount = MutableLiveData<Float>()

    inner class CounterStartService : Binder() {
        val myService: CounterService
            get() = this@CounterService
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Counter Service ", Toast.LENGTH_SHORT).show()
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification)

    }

    override fun onBind(p0: Intent?): IBinder? {
        return alarmBinder
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
        Log.e("alarmTAG", "onDestroy: ")
    }

    private fun showNotification(title: String, msg: String): Notification {
        val notificationIntent = Intent(this, CountActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = Intent(this, CountActivity::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        val notification: Notification =
            NotificationCompat.Builder(this, Utils.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)

                .setSmallIcon(android.R.drawable.checkbox_on_background)
                .addAction(
                    android.R.drawable.sym_def_app_icon,
                    "ForegroundService Kotlin",
                    buttonPendingIntent
                ).build()
        return notification
    }


    private fun stopService() {
        stopSelf()
    }

}