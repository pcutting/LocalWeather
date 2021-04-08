package com.philipcutting.localweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.models.Hourly
import com.philipcutting.localweather.networking.NetworkOneCallAll

class CurrentWeatherViewModel : ViewModel() {

    val currentWeatherReportLiveData = MutableLiveData<CombinedWeatherReport?>()

    fun getCurrentWeather() {
        NetworkOneCallAll
                .getOneCallWeather { mVCurrentWeatherReport ->
                    currentWeatherReportLiveData.postValue(mVCurrentWeatherReport)
                }
    }

    fun getHourly(): List<Hourly?> {
        return currentWeatherReportLiveData.value?.hourly ?: emptyList()
    }

}
