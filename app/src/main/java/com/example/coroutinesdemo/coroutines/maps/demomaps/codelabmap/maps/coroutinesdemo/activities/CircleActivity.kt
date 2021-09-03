package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.coroutinesdemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinesdemo.R

class CircleActivity(var radius: Double)  {

    fun area(): Double {
        return Math.PI * radius * radius;
    }

    fun main() {
        fun CircleActivity.perimeter(): Double {
            return 2 * Math.PI * radius;
        }

        val newCircle = CircleActivity(2.5);
        println("Area of the circle is ${newCircle.area()}")
        println("Perimeter of the circle is ${newCircle.perimeter()}")
    }


}