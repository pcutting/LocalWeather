package com.philipcutting.localweather.repositories

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.models.LocationModel
import com.philipcutting.localweather.networking.NetworkOneCallAll

object WeatherRepository {
    // Should this be lateinit instead of nulled?
    private val TAG = "WeatherRepository"
    private val weatherReport: CombinedWeatherReport? = null

    private lateinit var locationCurrent : LocationModel

    val currentWeather = MutableLiveData<CombinedWeatherReport?>()


    fun setLocation(givenLocation: Location) {
        locationCurrent.apply {
             location = givenLocation
            latitude = givenLocation.latitude
            longitude = givenLocation.longitude
        }

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

    @JvmName("getCurrentWeather1")
    fun getCurrentWeather(){

        NetworkOneCallAll.getOneCallWeather { weatherReport ->
            currentWeather.value = weatherReport
        }
    }





}