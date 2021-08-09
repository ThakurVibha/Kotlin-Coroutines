package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils.notificationChannel
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import kotlinx.android.synthetic.main.activity_counter_activityy.*

class CounterActivityy : AppCompatActivity() {
    var counterService = CounterService()
    var isBound = false
    var generatedNumber=MutableLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_activityy)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel(this)
        }
        startService()
        onClick()
    }

    @SuppressLint("SetTextI18n")
    private fun onClick() {
        btnRefresh.setOnClickListener {
            tvNum.setText("Generated Number is  ${counterService.generateRandomNumber()}")
        }
    }

    private fun startService() {
        var startServiceIntent = Intent(this, CounterService::class.java)
        startService(startServiceIntent)
        bindService()
    }

    private fun bindService() {
        var serviceBindIntent = Intent(this, CounterService::class.java)
        bindService(serviceBindIntent, counterServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        var stopServiceIntent = Intent(this, CounterService::class.java)
        stopService(stopServiceIntent)
        Log.e("TAG", "stopService: ")
    }

    override fun onResume() {
        super.onResume()
        startService()
        Log.e("TAG", "onResume: ")
    }

    override fun onStop() {
        super.onStop()
//        stopService()
        Log.e("TAG", "onStop: ")
    }

    //to detect when the client (activity) has successfully bound to the service, and when it has disconnected from the service.
    var counterServiceConnection = object : ServiceConnection {
        @SuppressLint("SetTextI18n")
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.e("TAG", "onServiceConnected: ")
            val binder: CounterService.MyRandomNumberGenerator =
                p1 as CounterService.MyRandomNumberGenerator
            //getting reference to CounterService class
            counterService = binder.myCounter
            isBound = true
            CounterService().generateRandomNumber()
            Log.e("TAG", "New random number is..this: " + counterService.generateRandomNumber())
            generateNumber()
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e("TAG", "onServiceDisconnected: ")
            isBound = false
        }

    }

    @SuppressLint("SetTextI18n")
    private fun generateNumber() {
        tvNum.setText("Generated Number is+${counterService.generateRandomNumber().toString()}")
    }


}