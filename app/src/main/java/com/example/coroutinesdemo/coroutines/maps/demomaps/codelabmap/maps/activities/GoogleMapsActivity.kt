package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.IOException
import kotlin.math.cos
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.math.sqrt

@Suppress("DEPRECATION")


class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener,
    LocationSource.OnLocationChangedListener {

    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)
    private lateinit var map: GoogleMap
    var mapFragment: SupportMapFragment? = null
    var latLongOrigin: LatLng? = null
    var latLongDestination: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var TAG = "//"
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private lateinit var lastLocation: Location
    var isSource = true
    var lat1: Double? = null
    var lon1: Double? = null
    var lat2: Double? = null
    var lon2: Double? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val AVERAGE_RADIUS_EARTH = 6371
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        makeLocationCall()
        createLocationRequest()
        onClick()
    }

    private fun calculateDistanceInKm() {
        var latDistance=Math.toRadians((lat2!! -lat1!!))
        var lonDistance=Math.toRadians((lon2!! - lon1!!))
        val a = (sin(latDistance / 2) * sin(latDistance / 2)
                + (cos(Math.toRadians(lat2!!.toDouble())) * cos(Math.toRadians(lon2!!.toDouble()))
                * sin(lonDistance / 2) * sin(lonDistance / 2)))
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
        var distance=Math.round(AVERAGE_RADIUS_EARTH * c).toDouble()+450
        Log.e("distance", distance.toString())
        tvDistance.setText(distance.toString()+ "KM")
    }

    private fun onClick() {
        isSource = false
        floatingActionButton.setOnClickListener {
            if (isSource) {
                searchLocation()
                MapsHelper.changeMapType(map)
                calculateDistanceInKm()
            } else {
                searchLocation()
                searchDestination()
            }
        }
    }

    private fun searchLocation() {
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
            latLongOrigin = (LatLng(address.latitude, address.longitude))
            lat1 = address.latitude
            lon1 = address.longitude
            map!!.addMarker(MarkerOptions().position(latLng).title(location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }
    }

    private fun searchDestination() {
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
            Log.e("getLatLng", latLongDestination.toString())
            lat2 = address.latitude
            lon2 = address.longitude
            map!!.addMarker(MarkerOptions().position(latLng).title(location))
            map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }
    }

    private fun refreshLocation() {
        map.setOnMapClickListener {
            getLocation.text = MapsHelper.getAddress(this, it)
        }
    }

    private fun makeLocationCall() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                MapsHelper.placeMarkerOnMap(
                    this@GoogleMapsActivity,
                    map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
            }
        }
    }

    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //2
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

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
            startLocationUpdates()
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
        map = googleMap

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
        map.isMyLocationEnabled = true
        map.getUiSettings().setZoomControlsEnabled(true)

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
            setUpMap()
        }
        //Adding a Polyline
        MapsHelper.setPolyine(map)
        map.setOnPolylineClickListener(this)
        refreshLocation()
    }

    @SuppressLint("MissingPermission")
    private fun setUpMap() {
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                MapsHelper.placeMarkerOnMap(
                    this,
                    map,
                    LatLng(lastLocation.latitude, lastLocation.longitude)
                )
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
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
            startLocationUpdates()
        }
    }

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

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

}

