package com.philipcutting.localweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipcutting.localweather.models.CurrentWeatherReport
import com.philipcutting.localweather.networking.NetworkOneCallAll

class CurrentWeatherViewModel : ViewModel() {
    val testText = "Test text inside of currentWeatherViewModel class"
    val currentWeatherReportLiveData = MutableLiveData<CurrentWeatherReport?>()

    fun getCurrentWeather() {
        //val currentWeather = NetworkOneCallAll.getOneCallWeather { currentWeatherReport.value }
        currentWeatherReportLiveData.value = NetworkOneCallAll.getOneCallWeather { currentWeatherReportLiveData.value }
        //currentWeatherReport.value = currentWeather
    }


}
