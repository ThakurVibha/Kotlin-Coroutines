package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.permissions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.coroutinesdemo.R

class ActivityResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    fun sendResult(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
        Log.e("TAG", "sendResult: ", )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
        Log.e("TAG", "onBackPressed: ", )
    }
}