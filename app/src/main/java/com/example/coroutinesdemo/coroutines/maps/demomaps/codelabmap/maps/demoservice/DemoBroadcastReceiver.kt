package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast

class DemoBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val bm = p0!!.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batLevel: Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        Toast.makeText(p0, "Battery is $batLevel%", Toast.LENGTH_LONG).show()

    }
}