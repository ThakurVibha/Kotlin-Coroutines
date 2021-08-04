package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import android.widget.Toast
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils

class StepCounterBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "You have stopped stepCounter on Clicking", Toast.LENGTH_LONG).show()
        ServiceUtils().stopStepCounter(p0!!, p1!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            p0!!.startForegroundService(Intent(p0, StepCounterService::class.java))
        } else {
            p0!!.startService(Intent(p0, StepCounterService::class.java))
        }
    }


}
