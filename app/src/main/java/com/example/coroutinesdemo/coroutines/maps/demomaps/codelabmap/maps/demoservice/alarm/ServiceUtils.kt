package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities.GeofenceBroadcastReceiver
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities.GoogleCodelabActivity
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities.GoogleMapsActivity

object ServiceUtils {
    var stepCount = 1
    var COUNTER_CHANNEL_ID = "Counter channel id"
    var running = false
    var sensorManager: SensorManager? = null
    var MagnitudePrevious = 0.0
    var totalSteps: Float = 0F
    var inStep: Boolean = false
    private var currentvectorSum: Float = 0F


    fun stopAlarmService(context: Context, intent: Intent) {
        var stopServices = Intent(context, AlarmService::class.java)
        context.stopService(stopServices)
        Toast.makeText(context, "Alarm service has been stopped", Toast.LENGTH_LONG).show()
    }

    fun onStepDetection(p0: SensorEvent) {
        val x_acceleration: Float = p0.values.get(0)
        val y_acceleration: Float = p0.values.get(1)
        val z_acceleration: Float = p0.values.get(2)
        val Magnitude =
            Math.sqrt((x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration).toDouble())
        val MagnitudeDelta: Double = Magnitude - MagnitudePrevious
        MagnitudePrevious = Magnitude
        if (MagnitudeDelta > 6) {
            stepCount++
            Log.e("DETECT", stepCount.toString())
        }
        Log.e("DETECT", "onStepDetection: ")
    }

    fun stopDemoService(context: Context) {
        AlarmServiceActivity().alarmService.stopAlarmService()
        Toast.makeText(context, "Service stopped", Toast.LENGTH_LONG).show()
        Log.e("alarmTAG", "onStoppedService: ")
    }

    fun showNotificationOnGeoFencing(title: String, msg: String, context: Context): Notification {
        val notificationIntent = Intent(context, GeofenceBroadcastReceiver::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = Intent(context, GoogleMapsActivity::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(context, 0, pendingIntent, 0)
        val notification: Notification =
            NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(R.drawable.sym_def_app_icon,
                    "Tracking location in Background!!",
                    buttonPendingIntent
                ).build()
        return notification
    }

    fun showNotification(title: String, msg: String, context: Context): Notification {
        val notificationIntent = Intent(context, GeofenceBroadcastReceiver::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = Intent(context, GoogleMapsActivity::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(context, 0, pendingIntent, 0)
        val notification: Notification =
            NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.sym_def_app_icon,
                    "Tracking location in Background!!",
                    buttonPendingIntent
                ).build()
        return notification
    }

    //Notification channel
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                COUNTER_CHANNEL_ID, "Counter channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun countStepsMethod_2(event: SensorEvent?, callback: (Float) -> Unit) {
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]
        currentvectorSum = x * x + y * y + z * z
        Log.e("onStepsCount", x.toString())
        if (currentvectorSum < 50 && !inStep) {
            inStep = true
        }
        if (currentvectorSum > 125 && inStep) {
            inStep = false
            totalSteps++
            callback(totalSteps)
        }
    }

}