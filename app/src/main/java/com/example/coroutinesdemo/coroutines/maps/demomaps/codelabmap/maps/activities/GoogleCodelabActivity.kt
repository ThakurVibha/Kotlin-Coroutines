package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils.notificationChannel
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleCodelabActivity() : AppCompatActivity(),
    OnMapReadyCallback {
    lateinit var geofencingClient: GeofencingClient
    lateinit var googleMap: GoogleMap
    private val runningQOrLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q

    companion object {
        private const val REQUEST_PERMISSION_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_codelab)
        val mapFragment2 =
            supportFragmentManager.findFragmentById(R.id.frgmtCodelab) as? SupportMapFragment
        mapFragment2?.getMapAsync(this)

    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val latitude = 28.7041
        val longitude = 77.1025
        val zoomLevel = 15F
        var homeLatLng = LatLng(latitude, longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        googleMap.addMarker(MarkerOptions().position(homeLatLng))
        onMapClick(googleMap)
        onPoiClick(googleMap)
        enableMyLocation(googleMap)
    }


    private fun enableMyLocation(googleMap: GoogleMap) {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            if (googleMap != null) {
                googleMap.isMyLocationEnabled = true
                googleMap.addMarker(MarkerOptions().position(LatLng(28.7041, 77.1025)))
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_CODE
                )
                Log.e("permissionNotGranted", "enableMyLocation: ")
            }

    }
    private fun onPoiClick(googleMap: GoogleMap) {
        googleMap.setOnPoiClickListener {
            Log.e("poi", "onPoiClick: ")
            googleMap.addMarker(MarkerOptions().position(it.latLng).title(it.name))
        }
    }
    private fun onMapClick(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener {
            Log.e("TAG", "onMapClick: ")
            googleMap.addMarker(MarkerOptions().position(it))
//            addCircle(googleMap)
        }
    }
//    fun addCircle(map: GoogleMap) {
//        map.addCircle(
//            CircleOptions()
//                .center(LatLng(29.9680, 77.5552))
//                .radius(GoogleMapsActivity.GEOFENECE_RADIUS.toDouble())
//                .strokeColor(ContextCompat.getColor(this, R.color.teal_700))
//                .fillColor(ContextCompat.getColor(this, R.color.teal_700))
//        )
//    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.mapHybrid -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            Toast.makeText(this, "Hybrid map", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.mapSatellite -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            Toast.makeText(this, "Satellite map", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.mapTerrain -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            Toast.makeText(this, "Terrain map", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.mapNormal -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            Toast.makeText(this, "Normal map", Toast.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation(googleMap)
                Log.e("CodelabMapActivity", "onRequestPermissionsResult: ")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("CodelabMapActivity", "onDestroy: ")

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("CodelabMapActivity", "onRestart: ")

    }

    override fun onResume() {
        super.onResume()
        Log.e("CodelabMapActivity", "onResume: ")

    }

    override fun onStart() {
        super.onStart()
        Log.e("CodelabMapActivity", "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.e("CodelabMapActivity", "onStop: ")


    }
}