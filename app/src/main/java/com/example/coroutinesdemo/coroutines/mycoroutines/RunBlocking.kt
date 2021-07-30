package com.example.coroutinesdemo.coroutines.mycoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.util.serviceutils.ServiceUtils
import kotlinx.coroutines.*

class RunBlocking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_blocking)
        Log.d(ServiceUtils.TAG, "Before runBlocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(4000)
                Log.d(ServiceUtils.TAG, "finish IO coroutine one  ")
            }
            Log.d(ServiceUtils.TAG, "I am on thread ${Thread.currentThread().name}")
            Log.d(ServiceUtils.TAG, "start of  runBlocking")
            delay(2000)
            Log.d(ServiceUtils.TAG, "End of  runBlocking")

        }


    }
}