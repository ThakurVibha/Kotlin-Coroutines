package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.fusedLocationClient
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.lastLocation
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.latLongDestination
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.latLongOrigin
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.maputils.MapConstants.map
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.util.serviceutils.ServiceUtils
import com.example.fcmdemo.maps.demomaps.codelabmap.model.MapPlace
import com.example.myfirstapp.GoogleMaps.MarkerInfoWindowAdapter
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_google_maps.*
import java.io.IOException
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object MapsHelper {
    fun setPolyine() {
        val polyline1 = map.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    latLongOrigin,
                    latLongDestination
                )
        )
        polyline1.tag = "A"

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(28.644800, 77.216721), 4f))
    }
    fun drawPolygonApi(coordinates: List<List<Double>>, mMap: GoogleMap): Polyline {
        val options = PolylineOptions()
        for (i in coordinates.indices) {
            options.add(LatLng(coordinates[i][1], coordinates[i][0]))
        }
        options.clickable(true)
            .width(15f)
            .color(Color.BLUE)

        val polyline1 = mMap.addPolyline(options)
        polyline1.tag = "Api"
        return polyline1
    }

    //To get address of current location onMarkerClick
    fun getAddress(context: Context, location: LatLng): String {
        // 1
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                val i = 0
//                    addressText += if (i == 1) address.getAddressLine(i) else "\n" + address.getAddressLine(
//                        i
//                    )
                addressText += address.getAddressLine(i)
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }
        return addressText
    }


    fun addClusteredMarkers(context: Context, places: List<MapPlace>, mainmap: GoogleMap) {

        val clusterManager = ClusterManager<MapPlace>(context, mainmap)
        clusterManager.renderer = PlaceRenderer(context, mainmap, clusterManager)
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(context))
        clusterManager.addItems(places)
        clusterManager.cluster()
        // Set ClusterManager as the OnCameraIdleListener so that it
        // can re-cluster when zooming in and out.
        mainmap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }
//    fun addMarkerWithAdapter(
//        mMap: GoogleMap,
//        latlong: LatLng,
//        animate: Boolean,
//        context: Context
//    ): Marker {
//
//        val addMarker = mMap.addMarker(MarkerOptions().position(latlong))
//        if (animate) {
//            mMap.animateCamera(
//                CameraUpdateFactory.newLatLngZoom(
//                    LatLng(
//                        latlong.latitude,
//                        latlong.longitude
//                    ),
//                    mMap.cameraPosition.zoom
//                )
//            )
//        }
//        return addMarker
//    }

    //placing marker
    fun placeMarkerOnMap(context: Context, mMap: GoogleMap, location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAddress(context, location)  // add these two lines
        markerOptions.title(titleStr)
        mMap.addMarker(markerOptions)
    }


    //Adding a circle
    fun addCircle(mainmap: GoogleMap, context: Context) {
        MapConstants.circle = mainmap.addCircle(
            CircleOptions().center(LatLng(30.387672910020356, 76.77570959079574))
                .fillColor(ContextCompat.getColor(context, R.color.black)).radius(
                    10000.0
                ).strokeColor(ContextCompat.getColor(context, R.color.teal_700))
        )
    }

    //Receiving Location Updates
    fun startLocationUpdates(context: Context) {
        //1
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MapConstants.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //2
        MapConstants.fusedLocationClient.requestLocationUpdates(
            MapConstants.locationRequest,
            MapConstants.locationCallback,
            null /* Looper */
        )
    }

    //Notification channel
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                ServiceUtils.CHANNEL_ID, "Alarm service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun changeMapType(mMap: GoogleMap) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    //Update lastLocation with new location and update map with new location coordinates.
    fun makeLocationCall(context: Context) {
        MapConstants.locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                MapConstants.lastLocation = p0.lastLocation
                MapsHelper.placeMarkerOnMap(
                    context,
                    MapConstants.map,
                    LatLng(MapConstants.lastLocation.latitude, MapConstants.lastLocation.longitude)
                )
            }
        }


    }

}