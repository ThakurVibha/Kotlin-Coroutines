package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.`interface`

import android.util.Log
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.toast
import java.lang.Error
import java.net.CacheResponse
import java.net.ResponseCache

interface ResponseListener {
    fun onSuccess(response: ResponseCache){
        Log.e("onResponse", "onSuccess: ", )
    }
    fun onError(error: Error){
        Log.e("OnError", "onError: ", )
    }

    fun addDigits(){
        var a=88
        var b=44
        val add=a+b
        var sum=add
        Log.e("onAddition", "addDigits:{$sum} ", )
    }
    fun substract(){
        var a=646
        var b=344
        var sub=a-b
        Log.e("onSubs", "substract:{$sub} ", )
    }

}