package com.example.coroutinesdemo.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.R
import kotlinx.coroutines.*

class RunBlocking : AppCompatActivity() {
    var TAG = "RunBlocking"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_blocking)
//      runBlocking is used to actually blokc the main thread
//        for selected amount of time
//        GlobalScope.launch {
//
//        }
//this will block ui updates
        Log.d(TAG, "Before runBlocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(4000)
                Log.d(TAG, "finish IO coroutine one  ")

            }
            Log.d(TAG, "I am on thread ${Thread.currentThread().name}")
            Log.d(TAG, "start of  runBlocking")
            delay(2000)
            Log.d(TAG, "End of  runBlocking")

        }


    }
}