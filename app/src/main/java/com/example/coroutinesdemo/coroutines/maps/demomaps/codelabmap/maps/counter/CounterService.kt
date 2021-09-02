package com.example.countdown.activities

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.*
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.countdown.activities.CountActivity.Companion.counter
import com.example.countdown.utils.CounterReceiver
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
        startCounter {
            myCount.value = it
            startForeground(10001, showNotification("Counter", "This is counter $it"))
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return alarmBinder
    }

    private fun startCounter(callback: (Float) -> Unit) {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                Log.e("CounterTag", counter.toString())
                callback(counter.toFloat())
                counter++
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {

            }
        }.start()

    }

    fun getText(): String {
        return "This is from service+"

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
        val pendingIntent = Intent(this, CounterReceiver::class.java)
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