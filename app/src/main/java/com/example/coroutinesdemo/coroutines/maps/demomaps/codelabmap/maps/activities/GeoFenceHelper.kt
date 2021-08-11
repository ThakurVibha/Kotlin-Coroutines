package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.activities

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.transition.Transition
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng


class GeoFenceHelper(base: Context?) : ContextWrapper(base) {
    private var pendingIntent:PendingIntent?=null
    companion object {
        private const val TAG = "GeoFenceHelper"

    }

    fun getGeofencingRequest(geofence: Geofence?): GeofencingRequest? {
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER  or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT )
            .build()
    }

    fun getGeofence(ID: String?, latLng: LatLng, radius: Float, transitionTypes: Int): Geofence? {
        return Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, radius.toFloat())
            .setRequestId(ID)
            .setTransitionTypes(transitionTypes)
            .setLoiteringDelay(1000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }


    fun getPendingIntent(): PendingIntent? {
        if (pendingIntent != null) {
            return pendingIntent
        }
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    fun getMYErrorString(e: Exception): String? {
        if (e is ApiException) {
            when (e.statusCode) {
                GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> return "GEOFENCE_NOT_AVAILABLE"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> return "GEOFENCE_TOO_MANY_GEOFENCES"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> return "GEOFENCE_TOO_MANY_PENDING_INTENTS"
            }
        }
        return e.localizedMessage
    }


}