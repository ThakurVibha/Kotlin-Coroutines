package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import kotlinx.android.synthetic.main.activity_demo_service.*

class DemoServiceActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_service)
        MapsHelper.createNotificationChannel(this)
        onClick()
    }

    private fun onClick() {
        demoBtn.setOnClickListener {
            startDemoService()
            Log.e("TAG", "onStartButtonClicked: ", )
        }
        stopDemoBtn.setOnClickListener {
            stopDemoService()
            Log.e("TAG", "onStoppedButtonClicked: ", )
        }

    }

    private fun stopDemoService() {
        var stopService = Intent(this, DemoService::class.java)
        stopService(stopService)
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show()
        Log.e("TAG", "onStoppedService: ")
    }

    private fun startDemoService() {
        try {
            var startService = Intent(this, DemoService::class.java)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
            Log.e("TAG", "onStartService: ")
        } catch (e: Exception) {
            Log.e("TAG", e.localizedMessage)
        }
    }
}
