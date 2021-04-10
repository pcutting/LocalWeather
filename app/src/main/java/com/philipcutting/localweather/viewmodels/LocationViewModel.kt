package com.philipcutting.localweather.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LocationViewModel(application: Application): AndroidViewModel(application) {
    private val locationData = LocationLiveData(application)
    fun getLocationData() = locationData
}