package com.philipcutting.localweather.models

import android.location.Location
import java.math.RoundingMode
import java.time.LocalDateTime

data class LocationModel(
    var longitude: Double,
    var latitude: Double,
    var timeRecorded: LocalDateTime = LocalDateTime.now(),
    var location: Location? = null,

    ) {

    fun reduceDoubleSize(precision: Int, number: Double?) : Double {
        return number?.toBigDecimal()?.setScale(precision, RoundingMode.HALF_UP)?.toDouble() ?: 0.0
    }

}


