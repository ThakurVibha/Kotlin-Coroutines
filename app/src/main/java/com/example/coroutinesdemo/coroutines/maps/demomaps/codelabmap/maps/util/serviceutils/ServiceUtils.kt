package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterService
import com.example.coroutinesdemo.coroutines.myservices.foregroundservice.ForeGroundService
import com.example.coroutinesdemo.coroutines.myservices.musicservice.MusicService


class ServiceUtils {
    companion object {
        var CHANNEL_ID = "ForegroundService Kotlin"
        var TAG = "myTag"
        var player: MediaPlayer? = null
        var MUSIC_CHANNEL_ID = "exampleServiceChannel"
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForeGroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
    }

    fun stopMusicService(context: Context, intent: Intent) {
        var stopServices = Intent(context, MusicService::class.java)
        context.stopService(stopServices)
        Toast.makeText(context, "Muisc Service stopped", Toast.LENGTH_LONG).show()
        Log.e("stopped", "onStopped: ")
    }

    fun stopStepCounter(context: Context, intent: Intent){
        var stopStepCounter=Intent(context, StepCounterService::class.java)
        context.stopService(stopStepCounter)
        Toast.makeText(context, "Step counter Service stopped", Toast.LENGTH_LONG).show()
        Log.e("stepCounterStopped", "onStopped: ")
    }

}