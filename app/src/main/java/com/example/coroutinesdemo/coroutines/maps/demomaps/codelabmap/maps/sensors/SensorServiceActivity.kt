package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper

class SensorServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_service)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MapsHelper.createNotificationChannel(this)
        }

    }

    fun onStart(view: View) {
        try {
            var startService = Intent(this, StepCounterService::class.java)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
            Log.e("onStartingService", "onStart: ")
        } catch (e: Exception) {
            Log.e("TAG", e.localizedMessage)
        }
    }

    fun Onstop(view: View) {
        var stopService = Intent(this, StepCounterService::class.java)
        startService(stopService)
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show()
        Log.e("TAG", "onStart: ")
    }
}