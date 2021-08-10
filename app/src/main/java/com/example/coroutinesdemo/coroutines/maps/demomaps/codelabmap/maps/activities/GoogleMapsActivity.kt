package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapsHelper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_google_maps.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.IOException
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


@Suppress("DEPRECATION")


class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnPolylineClickListener, GoogleMap.OnMapClickListener,
    GoogleMap.OnCameraMoveStartedListener {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val REQUEST_CHECK_SETTINGS = 2
        const val AVERAGE_RADIUS_EARTH = 6371
        const val GEOFENECE_LAT = 30.6327
        const val GEOFENECE_LONG = 76.8243
        const val GEOFENECE_RADIUS = 10000.00
    }

    var latLongOrigin: LatLng? = null
    var latLongDestination: LatLng? = null
    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)
    private lateinit var map: GoogleMap
    var mapFragment: SupportMapFragment? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var TAG = "//"
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private lateinit var lastLocation: Location
    var isSource = true
    private var lat1: Double? = null
    private var lon1: Double? = null
    private var lat2: Double? = null
    private var lon2: Double? = null
    var mapService = MapService()
    var isBound = false


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ServiceUtils.notificationChannel(this)
        }
    }

    private fun requestMyGpsLocation(callback: (location: Location) -> Unit) {
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
        var client = LocationServices.getFusedLocationProviderClient(this)
        client.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    val location = locationResult?.lastLocation
                    if (location != null) {
                        callback.invoke(location)
                        location.checkForGeoFenceEntry(
                            location,
                            GEOFENECE_LAT,
                            GEOFENECE_LONG,
                            GEOFENECE_RADIUS,

                            )
                    }
                }

            },
            null,
        )
    }


    fun setMap() {
        map.addCircle(
            CircleOptions()
                .center(LatLng(GEOFENECE_LAT, GEOFENECE_LONG))
                .radius(GEOFENECE_RADIUS)
                .strokeColor(ContextCompat.getColor(this, R.color.black))
                .fillColor(ContextCompat.getColor(this, R.color.black))
        )
    }

    private fun onClick() {
        isSource = false
        floatingActionButton.setOnClickListener {
            if (isSource) {
                searchLocation()
                calculateDistance()
                setMap()

            } else {
                searchLocation()
                searchDestination()
            }
        }
    }

    private fun startService() {
        var startServiceIntent = Intent(this, MapService::class.java)
        startService(startServiceIntent)
        bindService()
    }

    private fun bindService() {
        var serviceBindIntent = Intent(this, MapService::class.java)
        bindService(serviceBindIntent, locationTrackerService, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        var stopServiceIntent = Intent(this, MapService::class.java)
        stopService(stopServiceIntent)
        Log.e("TAG", "stopService: ")
    }

    //location service connection
    var locationTrackerService = object : ServiceConnection {
        @SuppressLint("SetTextI18n")
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.e("TAG", "onServiceConnected: ")
            val binder: MapService.MyLocationTracker =
                p1 as MapService.MyLocationTracker
            mapService = binder.myLocationTracker
            makeLocationCall()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.e("TAG", "onServiceDisconnected: ")
            isBound = false
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calculateDistance() {
        var latDistance = Math.toRadians((lat2!! - lat1!!))
        var lonDistance = Math.toRadians((lon2!! - lon1!!))
        val a = (sin(latDistance / 2) * sin(latDistance / 2)
                + (cos(Math.toRadians(lat2!!.toDouble())) * cos(Math.toRadians(lon2!!.toDouble()))
                * sin(lonDistance / 2) * sin(lonDistance / 2)))
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))
        var distance = Math.round(AVERAGE_RADIUS_EARTH * c).toDouble() + 350
        Log.e("distance", distance.toString())
        tvDistance.setText(distance.toString() + "KM")
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
            map.addMarker(MarkerOptions().position(latLng).title(location))
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
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
            map.addMarker(MarkerOptions().position(latLng).title(location))
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(applicationContext, "Here you go(: (:", Toast.LENGTH_LONG).show()
        }
    }

    private fun refreshLocation() {
        map.setOnMapClickListener {
            getLocation.text = MapsHelper.getAddress(this, it)
            Log.e("map", it.toString())
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
                Log.e(
                    "mapTestingTag",
                    lastLocation.latitude.toString() + "and " + lastLocation.longitude.toString()
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
                    Log.e("map", "createLocationRequest: ")
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
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isRotateGesturesEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
            setUpMap()
        }
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

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
        startService()
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
//        latLongDestination?.let { addMarker() }

    }

//    override fun onLocationChanged(p0: Location) {
//        var lat = p0.latitude
//        var lon = p0.longitude
//        lastLocation = p0
//        var latLng=LatLng(lat, lon)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(latLng)
//        markerOptions.title("I am here")
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//        Log.e("onLocationChanged", "onLocationChanged: ", )
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onCameraMoveStarted(p0: Int) {
        Toast.makeText(this, "Camera moved", Toast.LENGTH_SHORT).show()
    }


}

