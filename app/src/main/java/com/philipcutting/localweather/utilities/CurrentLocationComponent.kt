package com.philipcutting.localweather.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

class CurrentLocationComponent (
    context: Context,
    private val locationSuccessCallback: (Location) -> Unit,
    private val locationErrorCallback: (String) -> Unit
): LifecycleObserver {
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private var cancellationTokenSource = CancellationTokenSource()

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_LOW_POWER,
            cancellationTokenSource.token
        ).addOnSuccessListener{ location ->
            if(location != null) {
                locationSuccessCallback(location)
            } else {
                locationErrorCallback("Location not found")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        locationErrorCallback("Location request cancelled")

        cancellationTokenSource.cancel()

        cancellationTokenSource = CancellationTokenSource()
    }

}