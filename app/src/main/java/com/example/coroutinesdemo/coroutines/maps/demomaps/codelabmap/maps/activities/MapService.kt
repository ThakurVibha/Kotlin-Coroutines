package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils.showNotification
import java.util.*

class MapService : Service() {
    var locationBinder: IBinder = MyLocationTracker()
    override fun onBind(p0: Intent?): IBinder {
        return locationBinder
    }

    inner class MyLocationTracker : Binder() {
        val myLocationTracker: MapService get() = this@MapService
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(1001, showNotification("Bound service", "Tracking location in background", this))
        Log.e("TAG", "onCreate:", )
    }

}