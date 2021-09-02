package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.await.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.await.utils.CoroutinesUtils
import kotlinx.android.synthetic.main.activity_coroutines_await.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime

class CoroutinesAwaitActivity : AppCompatActivity() {
    companion object {
        const val ALPHABET = "Alphabet string"
    }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_await)
        onclick()
        CoroutineScope(Dispatchers.IO).launch {
            val calculateTime = measureTimeMillis {
                val text = async {
                    CoroutinesUtils.getText()
                }
                val alphabet = async {
                    CoroutinesUtils.getAlphabet()
                }

                val divisionResult=async {
                    try{
                        CoroutinesUtils.divideByZero()

                    }catch (e:Exception){
                        Log.e("TAG", "Exception handled", )
                    }
                }

                Log.e("awaitTag", " Division is as follow: ${divisionResult.await()}" )
                Log.e("awaitTag", "text is: ${text.await()}")
                Log.e("awaitTag", "alphabet is :${alphabet.await()}")
            }
            Log.e("awaitTag", "Total time: $calculateTime")
            startTaskInParallel()
        }
    }

    private fun onclick() {
        btnLaunch.setOnClickListener {
            lifecycleScope.launch {
               while (true){
                   Log.e("awaitTag", "Coroutine is still running................")
               }
            }
            lifecycleScope.launch {
                Intent(this@CoroutinesAwaitActivity, SecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun startTaskInParallel() {
        CoroutineScope(Dispatchers.IO).launch {

            val calculateTime2 = measureTimeMillis {
                val intValue = async {
                    CoroutinesUtils.getIntegerValue()
                }
                val floatValue = async {
                    CoroutinesUtils.getFloatValue()
                }
                val combineResults = intValue.await() + floatValue.await()
                Log.e("awaitTag", "startTaskInParallel: $combineResults")
            }
            Log.e("awaitTag", "startTaskInParallel: $calculateTime2")
        }

    }


}