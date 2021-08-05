package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.AlarmUtils.backgroundSensor
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.AlarmUtils.onStepDetection
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils.Companion.CHANNEL_ID

class AlarmService : Service(),SensorEventListener {
    override fun onBind(p0: Intent?): IBinder? {
        Log.e("onBind", p0.toString())
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("myOnStartCommandTAG", "This is onStartCommand method")
        Toast.makeText(this, "Alarm has been started", Toast.LENGTH_SHORT).show()
        showNotification()
        startStepCounterInBackground()
        triggerEvent()
        return START_STICKY
    }

    private fun triggerEvent() {
        var listener = object : TriggerEventListener() {
            override fun onTrigger(p0: TriggerEvent?) {
                Toast.makeText(this@AlarmService, "this is a tpast", Toast.LENGTH_SHORT).show()
                Log.e("onTriggerEvent", "onTrigger: ", )
            }
        }
    }


    private fun showNotification() {
        val notificationIntent = Intent(this, AlarmServiceActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent = Intent(this, AlarmAlertReceiver::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        var notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Step counter")
                .setContentText("Detecting your steps")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.ic_lock_idle_alarm,
                    "This is step counter service!!",
                    buttonPendingIntent
                ).build()
        startForeground(123, notification)
        Log.e("alarmTAG", "showNotification: ")
    }

    private fun startStepCounterInBackground() {
        var backgroundSensor: SensorManager =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor: Sensor? = backgroundSensor!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        backgroundSensor?.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST)
        Log.e("registeringSensor", "startStepCounterInBackground: ")
    }

    override fun onSensorChanged(p0: SensorEvent?) {
//        backgroundSensor!!.unregisterListener(this)
        p0?.let { onStepDetection(it) }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.e("TAG", "onAccuracyChanged: ")
    }


}