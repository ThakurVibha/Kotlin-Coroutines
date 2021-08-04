package com.example.coroutinesdemo.coroutines.myservices.foregroundservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils
import kotlinx.android.synthetic.main.foreground_layout.*

class ForeGroundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.foreground_layout)
        onClick()
    }
    private fun onClick() {
        buttonStart.setOnClickListener(View.OnClickListener {
            ServiceUtils.startService(this, "Foreground Service is running...")
        })
//        buttonStop.setOnClickListener(View.OnClickListener {
//            ServiceUtils.stopService(this)
//        })
    }
}
