package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.sensors

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log

object StepCounterUtils {

    var running = false
    var sensorManager: SensorManager? = null
    var MagnitudePrevious = 0.0
    var stepCount = 0
    var totalSteps: Float = 0F
    var inStep: Boolean = false
    private var currentvectorSum: Float = 0F


    fun countStepsMethod_2(event: SensorEvent?, callback: (Float) -> Unit) {
        val x = event!!.values[0]
        val y = event.values[1]
        val z = event.values[2]
        currentvectorSum = x * x + y * y + z * z
        if (currentvectorSum < 100 && !inStep) {
            inStep = true
        }
        if (currentvectorSum > 125 && inStep) {
            inStep = false
            totalSteps++
            callback(totalSteps)
        }
    }




}