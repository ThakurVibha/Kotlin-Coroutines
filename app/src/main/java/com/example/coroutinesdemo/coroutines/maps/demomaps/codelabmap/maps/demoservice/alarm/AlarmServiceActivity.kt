package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.AlarmUtils.backgroundSensor
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.AlarmUtils.running
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import kotlinx.android.synthetic.main.activity_demo_service.*
import java.text.DateFormat
import java.util.*

class AlarmServiceActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener{

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_service)
        MapsHelper.createNotificationChannel(this)
        backgroundSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        onClick()
    }

    private fun onClick() {
        counterServiceBtn.setOnClickListener {
            running=true
            startDemoService()
            Log.e("alarmTAG", "onStartButtonClicked: ")
        }
        stopDemoBtn.setOnClickListener {
            stopDemoService()
            Log.e("alarmTAG", "onStoppedButtonClicked: ")
        }
        setAlarmBtn.setOnClickListener {
            running=false
            var timePickerDialog: DialogFragment = AlarmPickerFragment()
            timePickerDialog.show(supportFragmentManager, "time picker")
            Log.e("alarmTAG", "onClick: ", )
        }

        cancelAlarmBtn.setOnClickListener {
            cancelAlarm()
        }
    }

    override fun onTimeSet(p0: TimePicker?, hourday: Int, minute: Int) {
        var c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourday)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)
        updateTimeText(c)
        setAlarm(c)
        Log.e("alarmTAG", "onTimeSet: ", )

    }

    private fun updateTimeText(c: Calendar) {
        Log.e("alarmTAG", "updateTimeText: ", )
        var alarmTextView = "Alarm set for  "
        alarmTextView += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        titleTv.setText(alarmTextView)
    }

    private fun setAlarm(c: Calendar) {
        Log.e("alarmTAG", "setAlarm: ", )
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarmIntent = Intent(this, AlarmAlertReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(this, 2, alarmIntent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, alarmPendingIntent)

    }

    @SuppressLint("SetTextI18n")
    private fun cancelAlarm() {
        var alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarmIntent = Intent(this, AlarmAlertReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(this, 2, alarmIntent, 0)
        alarmManager.cancel(alarmPendingIntent)
        titleTv.setText("Alarm has been cancelled")
        Toast.makeText(this, "Alarm has been cancelled", Toast.LENGTH_LONG).show()
        Log.e("alarmTAG", "cancelAlarm: ", )
    }

    private fun stopDemoService() {
        var stopService = Intent(this, AlarmService::class.java)
        stopService(stopService)
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show()
        Log.e("alarmTAG", "onStoppedService: ")
    }

    private fun startDemoService() {
        try {
            var startService = Intent(this, AlarmService::class.java)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
            Log.e("TAG", "onStartService: ")
        } catch (e: Exception) {
            Log.e("alarmTAG", e.localizedMessage)
        }
    }

}
