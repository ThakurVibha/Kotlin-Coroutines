package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils.sensorManager
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepCounterService : Service(), SensorEventListener {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            var sensorManager: SensorManager =
                getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val countSensor: Sensor? = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            if (countSensor != null) {
                Toast.makeText(applicationContext, "Step Detecting Start", Toast.LENGTH_SHORT)
                    .show()
                registerListener()
                showNotification()
            }else{
                Toast.makeText(applicationContext, "Sensor Not Detected", Toast.LENGTH_SHORT).show()
            }
        }
        return START_STICKY
    }

    private fun registerListener() {
        var sensorManager: SensorManager =
            getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor: Sensor? = sensorManager!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        sensorManager?.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST)


    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let { StepCounterActivity().onStepDetection(it) }
        Log.d("onSensorChanged", "onSensorChanged: ")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.e("onAccuracyChanged", "onAccuracyChanged: ")
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, SensorServiceActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent = Intent(this, StepCounterBroadcastReceiver::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        var notification: Notification =
            NotificationCompat.Builder(this, ServiceUtils.CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.ic_lock_idle_alarm,
                    "Dismiss Notification and Service",
                    buttonPendingIntent
                ).build()
        startForeground(123, notification)
    }

}