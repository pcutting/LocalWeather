package com.philipcutting.localweather.models

import android.location.Location
import java.time.LocalDateTime

data class LocationModel(
    val longitude: Double,
    val latitude: Double,
    val timeRecorded: LocalDateTime = LocalDateTime.now(),
    val location: Location? = null,

    )


