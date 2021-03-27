package com.philipcutting.localweather.models

import java.time.LocalDateTime
import java.time.ZoneOffset

data class WeatherUnit(
    val timeMade: LocalDateTime = LocalDateTime.now()
){
    companion object {
        fun convertTimeFromEpochInSecondsToLocalDataTimeType(
            fromEpoch: Long,
            offset: Int = 0
        ): LocalDateTime {
            return when {
                fromEpoch <= 0 -> {
                    LocalDateTime.now()
                }

                else -> {
                    LocalDateTime.ofEpochSecond(fromEpoch, 0, ZoneOffset.ofTotalSeconds(offset))
                }
            }
        }
    }


}
