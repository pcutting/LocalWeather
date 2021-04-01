package com.philipcutting.localweather.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.networking.NetworkOneCallAll

class CurrentWeatherViewModel : ViewModel() {
    val testText = "test text inside of vm"
    private val mVCurrentWeatherReport : CombinedWeatherReport? = null
    val currentWeatherReportLiveData = MutableLiveData<CombinedWeatherReport?>()
    val testStringLiveData =  MutableLiveData<String>()

    // Use the currentWeatherReport held in the Network class.
    // TODO TODO TODO



    fun getCurrentWeather() {
        NetworkOneCallAll
                .getOneCallWeather { mVCurrentWeatherReport ->
                    currentWeatherReportLiveData.postValue(mVCurrentWeatherReport)
                }
    }

    fun incrementTestString(count: Int = 1){
        if (testStringLiveData.value.isNullOrBlank()) {
            testStringLiveData.value = "Dots:"
        }
        testStringLiveData.value += "*".repeat(count)
    }
}
