package com.example.countdown.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.countdown.activities.CountActivity

class CounterReceiver:BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "HIII", Toast.LENGTH_SHORT).show()
        var intent=Intent(p0, CountActivity::class.java)
        p0!!.startForegroundService(intent)

    }
}