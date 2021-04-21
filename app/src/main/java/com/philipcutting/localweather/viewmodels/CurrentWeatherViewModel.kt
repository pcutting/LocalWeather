package com.philipcutting.localweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.models.Hourly
import com.philipcutting.localweather.networking.NetworkOneCallAll
import java.time.LocalTime
import java.time.LocalTime.now

class CurrentWeatherViewModel : ViewModel() {
    val currentWeatherReportLiveData = MutableLiveData<CombinedWeatherReport?>()

    fun getCurrentWeather() {
        NetworkOneCallAll
                .getOneCallWeather { mVCurrentWeatherReport ->
                    currentWeatherReportLiveData.postValue(mVCurrentWeatherReport)
                }
    }

    fun lastUpdate(): LocalTime? = currentWeatherReportLiveData.value?.dt?.toLocalTime()

    fun timeSinceLastUpdateInSeconds() = lastUpdate()?.toSecondOfDay()?.minus(now().toSecondOfDay()) ?: 0

    fun getHourly(): List<Hourly?> {
        return currentWeatherReportLiveData.value?.hourly ?: emptyList()
    }

}
