package com.example.coroutinesdemo.coroutines.mycoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            delay(6000)
            doNetworkcall()
            doNetworkcall2()
            Log.d(ServiceUtils.TAG, doNetworkcall())
            Log.d(ServiceUtils.TAG, doNetworkcall2())
            Log.d(ServiceUtils.TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
        }
        Log.d(ServiceUtils.TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")

        GlobalScope.launch(Dispatchers.IO) {
            val answer = doNetworkcall()
            withContext(Dispatchers.Main) {
                text.text = answer
                Log.d(ServiceUtils.TAG, "setting text on  ${Thread.currentThread().name}")
            }
            Log.d(ServiceUtils.TAG, "I am on thread ${Thread.currentThread().name}")
        }
    }

    suspend fun doNetworkcall(): String {
        delay(3000)
        return "this is answer 1"
    }

    suspend fun doNetworkcall2(): String {
        delay(5000)
        return "this is answer 2"
    }

}