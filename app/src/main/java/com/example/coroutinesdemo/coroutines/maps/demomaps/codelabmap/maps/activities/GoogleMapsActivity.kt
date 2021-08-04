package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.hardware.SensorEventListener
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.DOT
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.LOCATION_PERMISSION_REQUEST_CODE
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.PATTERN_POLYLINE_DOTTED
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.REQUEST_CHECK_SETTINGS
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.fusedLocationClient
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.isSource
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.lastLocation
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.latLongDestination
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.latLongOrigin
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.locationCallback
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.locationRequest
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.locationUpdateState
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper.startLocationUpdates
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import java.io.IOException

@Suppress("DEPRECATION")
class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        MapConstants.mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        MapConstants.mapFragment!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        makeLocationCall()
        createLocationRequest()
        onClick()
    }

    private fun onClick() {
        isSource = false
        floatingActionButton.setOnClickListener {
            if (isSource) {
                searchLocation()
            } else {
                searchLocation()
                searchDestination()
            }
        }
    }

    fun searchLocation() {
        isSource = false
        val locationSource: AutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.tvSource)
        lateinit var location: String
        location = locationSource.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext, "provide location", Toast.LENGTH_SHORT).show()
        } else {
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            latLongOrigin = (LatLng(address.latitude.toDouble(), address.longitude.toDouble()))
            Log.e("hi", latLongOrigin.toString())
            MapConstants.map!!.addMarker(MarkerOptions().position(latLng).title(location))
            MapConstants.map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }
    }

    fun searchDestination() {
        isSource = true
        val destinationSource: AutoCompleteTextView =
            findViewById<AutoCompleteTextView>(R.id.tvDestination)
        lateinit var location: String
        location = destinationSource.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(applicationContext, "provide location", Toast.LENGTH_SHORT).show()
        } else {
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            latLongDestination = (LatLng(address.latitude.toDouble(), address.longitude.toDouble()))
            Log.e("hello", latLongDestination.toString())
            MapConstants.map!!.addMarker(MarkerOptions().position(latLng).title(location))
            MapConstants.map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }

//        drawRoutes()
    }

    private fun refreshLocation() {
        MapConstants.map.setOnMapClickListener {
            getLocation.text = MapsHelper.getAddress(this, it)
        }
    }

    //Update lastLocation with new location and update map with new location coordinates.
    private fun makeLocationCall() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                MapsHelper.placeMarkerOnMap(
                    this@GoogleMapsActivity,
                    MapConstants.map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
            }
        }
    }

    //     To Receive Location Updates
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates(this)
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(
                        this,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapConstants.map = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
        MapConstants.map.isMyLocationEnabled = true
        MapConstants.map.getUiSettings().setZoomControlsEnabled(true)

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                MapConstants.map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLatLng,
                        12f
                    )
                )
            }
            setUpMap()
        }
        //Adding a Polyline
        MapsHelper.setPolyine(MapConstants.map)
        MapConstants.map.setOnPolylineClickListener(this)
        refreshLocation()
    }

    //granting permissions
    @SuppressLint("MissingPermission")
    private fun setUpMap() {
        MapConstants.map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        // To add marker at current location
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                MapsHelper.placeMarkerOnMap(
                    this,
                    MapConstants.map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
                MapConstants.map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        currentLatLng,
                        12f
                    )
                )
            }
        }
    }

    //to handle location updates
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates(this)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates(this)
        }
    }

    //onClick drop the marker
    override fun onPolylineClick(p0: Polyline) {
        if (p0.pattern == null || !p0.pattern!!.contains(DOT)) {
            p0.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            p0.pattern = null
        }
        Toast.makeText(
            this, "Route type " + p0.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onMapClick(p0: LatLng) {
        getLocation.text = MapsHelper.getAddress(this, p0)

    }

}


