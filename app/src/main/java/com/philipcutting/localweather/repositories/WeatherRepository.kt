package com.philipcutting.localweather.repositories

import android.content.Context
import android.location.Location
import android.util.Log
import com.philipcutting.localweather.models.LocationModel
import com.philipcutting.localweather.networking.NetworkOneCallAll

object WeatherRepository {
    private val TAG = "WeatherRepository"
    private var locationHasBeenAquired = false
    private lateinit var locationCurrent: LocationModel
    fun getUnits(context: Context) = NetworkOneCallAll.getUnits(context)

    fun setLocation(givenLocation: Location) {
        locationCurrent = LocationModel(
             location = givenLocation,
            latitude = givenLocation.latitude,
            longitude = givenLocation.longitude)
        locationHasBeenAquired = this::locationCurrent.isInitialized // should now be true
        Log.d(TAG, "setLocation: ${locationCurrent.latitude}, ${locationCurrent.longitude}")
    }

    fun getLatitude() = locationCurrent.latitude
    fun getLongitude() = locationCurrent.longitude
    fun getLocation() : Location? {
        return if (this::locationCurrent.isInitialized) {
            Location(locationCurrent.location)
        } else {
            null
        }
    }
}