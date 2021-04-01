package com.philipcutting.localweather.repositories

import androidx.lifecycle.MutableLiveData
import com.philipcutting.localweather.models.CurrentWeatherReport
import com.philipcutting.localweather.networking.NetworkOneCallAll

object WeatherRepository {
    // Should this be lateinit instead of nulled?
    private val currentWeatherReport: CurrentWeatherReport? = null

    val currentWeather = MutableLiveData<CurrentWeatherReport?>()


    init {

    }

    @JvmName("getCurrentWeather1")
    fun getCurrentWeather(){
        NetworkOneCallAll.getOneCallWeather { _currentWeather -> _currentWeather }
        currentWeather.value =   currentWeatherReport
    }
}