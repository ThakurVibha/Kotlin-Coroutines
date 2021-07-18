package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.println
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = "mainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //first coroutine

        GlobalScope.launch {
            delay(6000)
            doNetworkcall()
            doNetworkcall2()
            Log.d(TAG, doNetworkcall())
            Log.d(TAG, doNetworkcall2())
            Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
        }
        Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")

        //Coroutines context

        GlobalScope.launch(Dispatchers.IO) {
            val answer = doNetworkcall()
            withContext(Dispatchers.Main){
                text.text=answer
                Log.d(TAG, "setting text on  ${Thread.currentThread().name}")
            }
            Log.d(TAG, "I am on thread ${Thread.currentThread().name}")

        }
    }

    //Suspend functions
    suspend fun doNetworkcall(): String {
        delay(3000)
        return "this is answer 1"
    }

    suspend fun doNetworkcall2(): String {
        delay(5000)
        return "this is answer 2"
    }

}