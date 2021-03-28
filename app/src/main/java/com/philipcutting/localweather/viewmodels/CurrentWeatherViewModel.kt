package com.philipcutting.localweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipcutting.localweather.models.CurrentWeatherReport
import com.philipcutting.localweather.networking.NetworkOneCallAll

class CurrentWeatherViewModel : ViewModel() {
    val testText = "test text inside of vm"
    private val mVCurrentWeatherReport : CurrentWeatherReport? = null
    val currentWeatherReportLiveData = MutableLiveData<CurrentWeatherReport?>()
    val testStringLiveData =  MutableLiveData<String>()

    fun getCurrentWeather() {
        NetworkOneCallAll
                .getOneCallWeather {
                    mVCurrentWeatherReport -> mVCurrentWeatherReport
                }
        currentWeatherReportLiveData.value = mVCurrentWeatherReport
    }

    fun incrementTestString(count: Int = 1){
        if (testStringLiveData.value.isNullOrBlank()) {
            testStringLiveData.value = "Dots:"
        }
        testStringLiveData.value += "*".repeat(count)
    }
}
