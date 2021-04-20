package com.philipcutting.localweather.repositories

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.models.LocationModel
import com.philipcutting.localweather.networking.NetworkOneCallAll
import com.philipcutting.localweather.utilities.toScale


object WeatherRepository {
    // Should this be lateinit instead of nulled?
    private val TAG = "WeatherRepository"
    private val weatherReport: CombinedWeatherReport? = null

    private var locationHasBeenAquired = false

    private lateinit var locationCurrent: LocationModel

    val currentWeather = MutableLiveData<CombinedWeatherReport?>()

    fun getUnits(context: Context) = NetworkOneCallAll.getUnits(context)
    fun getWindUnits(context: Context) = NetworkOneCallAll.getWindUnits(context)


    fun getLocationAquiredStatus() = locationHasBeenAquired

    fun setLocation(givenLocation: Location) {
        locationCurrent = LocationModel(
             location = givenLocation,
            latitude = givenLocation.latitude,
            longitude = givenLocation.longitude)

        locationHasBeenAquired = this::locationCurrent.isInitialized // should now be true

        Log.d(TAG, "setLocation: ${locationCurrent.latitude}, ${locationCurrent.longitude}")
    }

    fun getLatitude() = locationCurrent.latitude
    fun getLatitude(setScale: Int): Double  = locationCurrent.latitude.toScale(setScale)
    fun getLongitude() = locationCurrent.longitude
    fun getLongitude(setScale: Int) = locationCurrent.longitude.toScale(setScale)
    fun getLocation() : Location? {
        return if (this::locationCurrent.isInitialized) {
            Location(locationCurrent.location)
        } else {
            null
        }
    }

    @JvmName("getCurrentWeather1")
    fun getCurrentWeather(){

        NetworkOneCallAll.getOneCallWeather { weatherReport ->
            currentWeather.value = weatherReport
        }
    }





}