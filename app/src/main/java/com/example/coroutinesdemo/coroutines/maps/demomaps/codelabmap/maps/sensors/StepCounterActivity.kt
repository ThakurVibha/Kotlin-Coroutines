package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils.MagnitudePrevious
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils.running
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils.sensorManager
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors.StepCounterUtils.stepCount
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import kotlinx.android.synthetic.main.activity_step_counter.*

class StepCounterActivity : AppCompatActivity(), SensorEventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_counter)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MapsHelper.createNotificationChannel(this)
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        running = true
        registerCounter()
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)

    }


    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            onStepDetection(p0)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        Log.e("onAccuracyChanged", "onAccuracyChanged: ")
    }

    private fun registerCounter() {
        var stepSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (stepSensor == null) {
            Toast.makeText(this, "Device do not have any sensors", Toast.LENGTH_SHORT).show()
            Log.d("senorTag", "No sensors")
        } else {
            sensorManager!!.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
            Log.d("sensorTag", "onResume: ")
        }
    }

    fun onStepDetection(p0: SensorEvent) {
        val x_acceleration: Float = p0.values.get(0)
        val y_acceleration: Float = p0.values.get(1)
        val z_acceleration: Float = p0.values.get(2)
        val Magnitude =
            Math.sqrt((x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration).toDouble())
        val MagnitudeDelta: Double = Magnitude - MagnitudePrevious
        MagnitudePrevious = Magnitude
        if (MagnitudeDelta > 6) {
            stepCount++
        }
        stepValue.setText(stepCount.toString())
    }

//    fun startMyService(view: View) {
//        try {
//            var startService = Intent(this, StepCounterService::class.java)
//            startService(startService)
//            Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show()
//            Log.e("onStartingService", "onStart: ")
//        } catch (e: Exception) {
//            Log.e("TAG", e.localizedMessage)
//        }
//    }
//
//    fun stopMyService(view: View) {
//        var stopService = Intent(this, StepCounterService::class.java)
//        startService(stopService)
//        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show()
//        Log.e("TAG", "onStart: ")
//    }

}
