package com.example.fcmdemo.maps.demomaps.codelabmap.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.fcmdemo.maps.demomaps.codelabmap.model.MapPlace
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.BitmapHelper
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.mapCodelab
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.PlacesReader
import com.example.myfirstapp.GoogleMaps.MarkerInfoWindowAdapter
import com.google.android.gms.maps.model.*

class CodelabMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private val bicycleIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.black)
        BitmapHelper.vectorToBitmap(
            this,
            R.drawable.common_google_signin_btn_icon_dark_normal,
            color)
    }
    //This code invokes the read() method on a PlacesReader, which returns a List<Place>
    private val places: List<MapPlace> by lazy {
        PlacesReader(this).read()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codelab_map)
        val mapFragment2 =
            supportFragmentManager.findFragmentById(R.id.codelabMap) as? SupportMapFragment
        mapFragment2?.getMapAsync(this)
    }

    override fun onMapReady(mainmap: GoogleMap) {
        mapCodelab = mainmap
        mapCodelab.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
        MapsHelper.addClusteredMarkers(this, places, mainmap)
        places.forEach {
            val markeroptions = MarkerOptions().title(it.name).position(it.latLng).icon(bicycleIcon)
                .snippet(it.address).position(it.latLng)
            mainmap.addMarker(markeroptions)
        }
        MapsHelper.addCircle(mapCodelab, this)
    }


}

