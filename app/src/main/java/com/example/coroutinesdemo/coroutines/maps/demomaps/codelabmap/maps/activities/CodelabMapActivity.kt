package com.example.fcmdemo.maps.demomaps.codelabmap.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinesdemo.R
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.StreetViewPanoramaView
import com.google.android.gms.maps.model.*

class CodelabMapActivity : AppCompatActivity(),
    OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {
    var streetViewPanoramaView: StreetViewPanoramaView? = null
    var streetViewPanorama: StreetViewPanorama? = null
    var sendLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codelab_map)
        streetViewPanoramaView = findViewById<StreetViewPanoramaView>(R.id.codelabMap)
    }


    override fun onStreetViewPanoramaChange(p0: StreetViewPanoramaLocation) {
        Log.e("street", (p0.position.latitude + p0.position.longitude).toString())
    }

    override fun onStreetViewPanoramaReady(p0: StreetViewPanorama) {
        p0!!.isStreetNamesEnabled = true
        p0!!.isUserNavigationEnabled = true
        p0!!.isPanningGesturesEnabled = true
        p0!!.isZoomGesturesEnabled = true
        Log.e("street", "onStreetViewPanoramaReady: ")
        streetViewPanorama!!.setPosition(LatLng(55.758818, 37.620587))
        streetViewPanorama=p0
    }

    override fun onDestroy() {
        super.onDestroy()
        streetViewPanoramaView!!.onDestroy()
        Log.e("street", "onDestroy: ")
    }

    override fun onResume() {
        super.onResume()
        streetViewPanoramaView!!.onResume()
        Log.e("street", "onResume: ")
    }

    override fun onStart() {
        super.onStart()
        Log.e("street", "onStart: ")
    }

}

