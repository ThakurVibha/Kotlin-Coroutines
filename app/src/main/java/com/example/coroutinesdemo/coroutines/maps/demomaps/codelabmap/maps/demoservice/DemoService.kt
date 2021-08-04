package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice
import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils

class DemoService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        Log.e("onBind",p0.toString())
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("TAG", "This is onStartCommand method" )
        showNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy: ", )
    }
    private fun showNotification() {
        val notificationIntent = Intent(this, DemoServiceActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent = Intent(this, DemoBroadcastReceiver::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        var notification: Notification =
            NotificationCompat.Builder(this, ServiceUtils.CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.ic_lock_idle_alarm,
                    "Get your custom information Here!!",
                    buttonPendingIntent
                ).build()
        startForeground(123, notification)
    }
}