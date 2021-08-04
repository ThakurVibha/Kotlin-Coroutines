package com.example.coroutinesdemo.coroutines.myservices.musicservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils

class MusicBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "You have stopped services on clicking", Toast.LENGTH_LONG).show()
        ServiceUtils().stopMusicService(p0!!, p1!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            p0!!.startForegroundService(Intent(p0, MusicService::class.java))
        } else {
            p0!!.startService(Intent(p0, MusicService::class.java))
        }
    }
}