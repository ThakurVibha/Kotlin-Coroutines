package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmAlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Alarm service has been started", Toast.LENGTH_LONG).show()
        var intent = Intent(p0, AlarmService::class.java)
        p0!!.startService(intent)
        Log.e("alarmTAG", "Alarm service has been started")
    }
}