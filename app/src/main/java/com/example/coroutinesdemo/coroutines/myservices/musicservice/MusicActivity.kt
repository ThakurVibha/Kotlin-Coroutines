package com.example.coroutinesdemo.coroutines.myservices.musicservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper

class MusicActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        MapsHelper.createNotificationChannel(this)
    }
    fun onStart(view: View) {
        startMusicService()
    }

    fun Onstop(view: View) {
        stopMusicService()
    }

    private fun startMusicService() {
        try{
            var startService = Intent(this, MusicService::class.java)
            startService(startService)
            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
            Log.e("TAG", "onStart: ")
        }catch (e:Exception){
            Log.e("TAG",e.localizedMessage )
        }
    }

    private fun stopMusicService() {
        var stopService = Intent(this, MusicService::class.java)
        stopService(stopService)
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show()
        Log.e("stopped", "onStopped: ")

    }
}