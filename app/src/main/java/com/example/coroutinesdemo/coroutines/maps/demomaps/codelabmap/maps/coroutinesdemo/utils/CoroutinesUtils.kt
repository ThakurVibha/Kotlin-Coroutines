package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.coroutinesdemo.utils

import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.coroutinesdemo.activities.CoroutinesAwaitActivity
import kotlinx.coroutines.delay

object CoroutinesUtils {
    suspend fun getIntegerValue(): Int {
        delay(2000)
        return 44
    }

    suspend fun getFloatValue(): Float {
        delay(5000)
        return 44.5F
    }

    suspend fun getText(): String {
        delay(1000)
        return "This is text"
    }

    suspend fun getAlphabet(): String {
        delay(2000)
        return CoroutinesAwaitActivity.ALPHABET
    }

    suspend fun divideByZero(): Int {
        delay(1000)
        var a=40
        var b=0
        return a/b
    }
}