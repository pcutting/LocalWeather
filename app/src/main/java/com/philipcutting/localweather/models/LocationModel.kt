package com.philipcutting.localweather.models

import android.location.Location
import java.time.LocalDateTime

data class LocationModel(
    var longitude: Double,
    var latitude: Double,
    var timeRecorded: LocalDateTime = LocalDateTime.now(),
    var location: Location? = null
)


