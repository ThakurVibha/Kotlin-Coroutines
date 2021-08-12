package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm.ServiceUtils.showNotificationOnGeoFencing
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent


class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

//        Toast.makeText(p0, Toast.LENGTH_SHORT).show()
        Log.e("geofence", "Your Geo fence has been triggered")
        var geofencingEvent = GeofencingEvent.fromIntent(p1)
        if (geofencingEvent.hasError()) {
            Log.d("geofence", "onReceive: Error receiving geofence event...")
            return
        }
        var location = geofencingEvent.triggeringLocation
        Log.e("geofence", location.toString())
        val geofenceList = geofencingEvent.triggeringGeofences
        for (geofence in geofenceList) {
            Log.d("geofence", "onReceive: " + geofence.requestId)
        }
        var transitionType = geofencingEvent.geofenceTransition

        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(p0, "TRANSITION_ENTER", Toast.LENGTH_SHORT).show()
                Log.e("geofence", "OnEnter: ")
                if (p0 != null) {
                    showNotificationOnGeoFencing("Geofencing", "this is Geofencing event", p0)
                }
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Log.e("geofence", "onDwell: ")
                Toast.makeText(p0, "TRANSITION_DWELL", Toast.LENGTH_SHORT).show()
                if (p0 != null) {
                    showNotificationOnGeoFencing("Geofencing", "this is Geofencing event", p0)
                }
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Log.e("geofence", "OnExit: ")
                Toast.makeText(p0, "TRANSITION_EXIT", Toast.LENGTH_SHORT).show()
                if (p0 != null) {
                    showNotificationOnGeoFencing("Geofencing", "this is Geofencing event", p0)
                }
            }
        }
    }

}
