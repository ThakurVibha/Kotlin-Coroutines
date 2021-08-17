package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.`interface`

import java.lang.Error
import java.net.ResponseCache

class ResponseActivity():ResponseListener {


    override fun onError(error: Error) {
        super.onError(error)
    }

    override fun onSuccess(response: ResponseCache) {
        super.onSuccess(response)
    }

    override fun addDigits() {
        super.addDigits()
    }

    override fun substract() {
        super.substract()
    }
    var addDigits=ResponseActivity().addDigits()
    var subsDigits=ResponseActivity().substract()




}