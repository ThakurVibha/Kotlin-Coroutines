package com.example.countdown.activities

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import com.example.countdown.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_counter_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CountActivity : AppCompatActivity() {
    var appRunningBackground: Boolean = false
    lateinit var notificationManager: NotificationManager

    companion object {
        var counter = 0
    }

    lateinit var countService: CounterService
    var mBound = false

    private var mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            var myServiceBinder: CounterService.CounterStartService =
                p1 as CounterService.CounterStartService
            countService = myServiceBinder.myService
            countService.myCount.observe(this@CountActivity) {
                Log.e("countTAG", "onServiceConnected: $it")
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e("CountTAG", "Disconnected: ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_counter_activity)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onclick()
        }
        checkAppInBackground()
        startDemoService()
    }


    private fun onclick() {
        btnStart.setOnClickListener {
            startCounter()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Utils.notificationChannel(this)
                Utils.newNotificationChannel(this)
            }
        }
    }

    private fun checkAppInBackground() {
        val runningAppProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(runningAppProcessInfo)
        appRunningBackground =
            runningAppProcessInfo.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        if (appRunningBackground) {
            Toast.makeText(
                applicationContext,
                "Your Android Application is Running in Background",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                "Your Android Application is not Running in Background",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(
            applicationContext,
            "Your Android Application is Running in Background",
            Toast.LENGTH_SHORT
        ).show()
        notificationManager.notify(
            1001,
            showNewNotification("App stop", "Your app will be stop after 10 seconds")
        )
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            notificationManager.notify(
                100,
                showNewNotification("App stop", "App stopped")

            )

        }
    }

    private fun startDemoService() {
        try {
            var startService = Intent(this, CounterService::class.java)
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("alarmTAG", e.localizedMessage)
        }
    }

    private fun startCounter() {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                tvCount.text = counter.toString()
                Log.e("CounterTag", counter.toString())
                counter++
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {

            }
        }.start()
    }

    override fun onStart() {
        super.onStart()
        var intent = Intent(applicationContext, CounterService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(mServiceConnection)
        mBound = false
    }

    private fun showNewNotification(title: String, msg: String): Notification {
        val notificationIntent = Intent(this, CountActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = Intent(this, CountActivity::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        return NotificationCompat.Builder(this, Utils.NEW_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(msg)
            .setSmallIcon(android.R.drawable.checkbox_on_background)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // or NotificationCompat.PRIORITY_MAX
            .addAction(
                android.R.drawable.sym_def_app_icon,
                "ForegroundService",
                buttonPendingIntent
            ).build()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        notificationManager.notify(
            1001, showNewNotification("Oops", "Counter will stop in 10 seconds")
        )

    }


}