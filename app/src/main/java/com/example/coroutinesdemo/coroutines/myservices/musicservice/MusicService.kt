package com.example.coroutinesdemo.coroutines.myservices.musicservice

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(ServiceUtils.TAG, "onCreate: ")
    }

    override fun onDestroy() {
//        ServiceUtils.player!!.stop()
//        ServiceUtils.player!!.release()
        Log.e(ServiceUtils.TAG, "onDestroy: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(ServiceUtils.TAG, "onStartCommand: ")
        CoroutineScope(Dispatchers.IO).launch {
            startPlayer()
            Log.d("crashTag", "  hello from thread ${Thread.currentThread().name}")
        }
        showNotification()
        return START_STICKY
    }

    private suspend fun startPlayer() {
        try {
            delay(5000)
            ServiceUtils.player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
            ServiceUtils.player!!.start()
            Log.e("myTag", "music is playing")
            ServiceUtils.player!!.isLooping = true
        } catch (e: Exception) {
        }
        Log.e("myTag", "music is playing")
    }
    private fun showNotification() {
        val notificationIntent = Intent(this, MusicActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent = Intent(this, MusicBroadcastReceiver::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        var notification: Notification =
            NotificationCompat.Builder(this,ServiceUtils.CHANNEL_ID)
                .setContentTitle("Alarm service")
                .setContentText("it's working")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.ic_lock_idle_alarm,
                    "It's time to wake up",
                    buttonPendingIntent
                ).build()
        startForeground(123, notification)
    }
}