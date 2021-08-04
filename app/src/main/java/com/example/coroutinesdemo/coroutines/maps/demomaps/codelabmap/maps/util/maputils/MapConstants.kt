package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

object MapConstants {
    var greyPolyLine: Polyline? = null
    var blackPolyLine: Polyline? = null
    var originMarker: Marker? = null
    var destinationMarker: Marker? = null
    val DOT: PatternItem = Dot()
    lateinit var map: GoogleMap

    //    const val BASE_URL = "https://api.openrouteservice.org/v2/directions/"
    private val PATTERN_GAP_LENGTH_PX = 20
    val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)
    var mapFragment: SupportMapFragment? = null
    var latLongOrigin: LatLng? = null
    var latLongDestination: LatLng? = null
    lateinit var fusedLocationClient: FusedLocationProviderClient
    var TAG = "//"
    lateinit var locationCallback: LocationCallback
    lateinit var locationRequest: LocationRequest
    var locationUpdateState = false
    lateinit var lastLocation: Location
    var isSource = true

    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val REQUEST_CHECK_SETTINGS = 2
    const val PLACE_PICKER_REQUEST = 3
    const val BASE_URL = "https://api.openrouteservice.org/v2/directions/"
    lateinit var mapCodelab: GoogleMap
    var circle: Circle? = null


}