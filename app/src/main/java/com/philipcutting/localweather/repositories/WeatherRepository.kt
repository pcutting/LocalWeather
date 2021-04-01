package com.philipcutting.localweather.repositories

import androidx.lifecycle.MutableLiveData
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.networking.NetworkOneCallAll

object WeatherRepository {
    // Should this be lateinit instead of nulled?
    private val weatherReport: CombinedWeatherReport? = null

    val currentWeather = MutableLiveData<CombinedWeatherReport?>()


    @JvmName("getCurrentWeather1")
    fun getCurrentWeather(){

        NetworkOneCallAll.getOneCallWeather { weatherReport ->
            currentWeather.value = weatherReport
        }
    }
}