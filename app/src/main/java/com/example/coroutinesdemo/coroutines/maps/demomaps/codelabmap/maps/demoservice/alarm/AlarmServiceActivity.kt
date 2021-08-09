package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import kotlinx.android.synthetic.main.activity_demo_service.*
import java.text.DateFormat
import java.util.*

class AlarmServiceActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var alarmService: AlarmService
    var mBound = false

    private var mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            var myServiceBinder: AlarmService.StepCounterService =
                p1 as AlarmService.StepCounterService
            alarmService = myServiceBinder.myAlarm
            alarmService.mySteps.observe(this@AlarmServiceActivity) {
                tvSteps.text = "$it"
                Log.e("alarmTAG", "onServiceConnected: ")
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e("alarmTAG", "Disconnected: ")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_service)
        MapsHelper.createNotificationChannel(this)
        onClick()
        startDemoService()
    }

    private fun onClick() {
        counterServiceBtn.setOnClickListener {
            titleTv.setText(alarmService.getText())
            Log.e("alarmTAG", "onStepDetection: ")
        }
        stopDemoBtn.setOnClickListener {
            ServiceUtils.stopDemoService(this)
            Log.e("alarmTAG", "onStoppedButtonClicked: ")
        }
        setAlarmBtn.setOnClickListener {
            var timePickerDialog: DialogFragment = AlarmPickerFragment()
            timePickerDialog.show(supportFragmentManager, "time picker")
            Log.e("alarmTAG", "onClick: ")
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
        Log.e("alarmTAG", "onTimeSet: ")

    }

    private fun updateTimeText(c: Calendar) {
        var alarmTextView = "Alarm set for  "
        alarmTextView += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        titleTv.setText(alarmTextView)
        Log.e("alarmTAG", "updateTimeText: ")

    }

    private fun setAlarm(c: Calendar) {
        Log.e("alarmTAG", "setAlarm: ")
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
        Log.e("alarmTAG", "cancelAlarm: ")
    }


    private fun startDemoService() {
        try {
            var startService = Intent(this, AlarmService::class.java)
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
            Log.e("TAG", "onStartService: ")
        } catch (e: Exception) {
            Log.e("alarmTAG", e.localizedMessage)
        }
    }

    override fun onStart() {
        super.onStart()
        var intent = Intent(applicationContext, AlarmService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        Log.e("alarmTAG", "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        unbindService(mServiceConnection)
        mBound = false
        Log.e("alarmTAG", "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("alarmTAG", "onDestroy: ")

    }

}
