package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils.countStepsMethod_2
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils.Companion.CHANNEL_ID

class AlarmService : Service(), SensorEventListener {
    lateinit var notificationManager: NotificationManager
    private var backgroundSensor: SensorManager? = null
    var alarmBinder: IBinder = StepCounterService()
    var mySteps = MutableLiveData<Float>()

    //used to  get instance of service inside whatever you bind to it
    inner class StepCounterService : Binder() {
        val myAlarm: AlarmService
            get() = this@AlarmService
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        backgroundSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor: Sensor? = backgroundSensor!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        backgroundSensor?.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_FASTEST)
        Log.e("alarmTAG", "startStepCounterInBackground: ")
        startForeground(1001, showNotification("Step Counter", "Counting..."))
    }

    override fun onBind(p0: Intent?): IBinder? {
        //to the reference to service inside activity
        return alarmBinder
    }

    private fun showNotification(title: String, msg: String): Notification {
        val notificationIntent = Intent(this, AlarmServiceActivity::class.java)
        notificationIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = Intent(this, AlarmAlertReceiver::class.java)
        val buttonPendingIntent = PendingIntent.getBroadcast(this, 0, pendingIntent, 0)
        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .addAction(
                    R.drawable.sym_def_app_icon,
                    "This is step counter service!!",
                    buttonPendingIntent
                ).build()
        return notification
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        countStepsMethod_2(p0)
        {
            mySteps.value = it
            startForeground(1001, showNotification("Step Counter", "Counting: $it"))
            notificationManager.notify(1001, showNotification("Step Counter", "Counting: $it"))
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.e("alarmTAG", "onAccuracyChanged: ")
    }


    fun getText(): String {
        return "This is from service++"
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAlarmService()
        Log.e("alarmTAG", "onDestroy: ")
    }

    fun stopAlarmService() {
        backgroundSensor?.unregisterListener(this)
        stopSelf()
    }
}