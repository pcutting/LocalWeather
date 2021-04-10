package com.philipcutting.localweather.models

import androidx.annotation.StringRes
import com.philipcutting.localweather.R
import java.time.*

/**
https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2

 */

enum class WeatherConditions(
        @StringRes val conditionDescriptionStringId: Int = -1,
        val codes: List<Int> = emptyList(),
){
    CLOUDY(R.string.cloudy, (801..804).toList()),
    DRIZZLE(R.string.drizzle,  (300..321).toList()),
    RAIN(R.string.rain, (500..531).toList()),
    THUNDER(R.string.thunder, (200..232).toList()),
    SNOW(R.string.snow,(600..622).toList()),
    ATMOSPHERE(R.string.atmosphere, (700..781).toList()),
    CLEAR(R.string.clear, listOf(800)),
    ERROR(R.string.error, listOf(0));

    fun getImageResource(
        time:LocalDateTime?,
        temperature: Double?,
        currentWeather: CombinedWeatherReport? = null
    ) : Int {
        return when (this) {
            CLOUDY -> getCloudyImage(time, temperature, currentWeather)
            RAIN -> getRainyImage(currentWeather)
            CLEAR -> getClearImage(time,currentWeather)
            DRIZZLE -> getDrizzlyImage(time, currentWeather)
            SNOW  -> getSnowyImage()
            THUNDER -> getThunderyImage()
            ATMOSPHERE -> getAtmosphericImage()
            ERROR -> R.drawable.ic_error
        }
    }

    companion object {
        fun getConditionFromCode(code: Int?): WeatherConditions{
            return  values().firstOrNull {
                it.codes.contains(code)
            } ?: ERROR
        }
    }
}

private fun getAtmosphericImage(): Int {
    return  R.drawable.ic_snow
}

private fun getThunderyImage(): Int {
    return  R.drawable.ic_snow
}

private fun getSnowyImage(): Int {
    return  R.drawable.ic_snow
}

private fun getDrizzlyImage(time: LocalDateTime? , combinedWeatherReport: CombinedWeatherReport?): Int {
    return when{
        isDay(time, combinedWeatherReport)  -> R.drawable.ic_drizzle_day
        else -> R.drawable.ic_drizzle_night
    }
}

private fun getRainyImage(combinedWeatherReport: CombinedWeatherReport?): Int {
    return when{
        combinedWeatherReport?.windSpeed != null && combinedWeatherReport.windSpeed > 20.0  -> R.drawable.ic_rain_windy
        else -> R.drawable.ic_rain
    }
}

private fun getClearImage(conditionTime:LocalDateTime?, combinedWeatherReport: CombinedWeatherReport?): Int {
    //TODO may have a bug with the date component here.
    val time = conditionTime ?: LocalDateTime.now()
    val sunRiseRangeEnd = sunRise(combinedWeatherReport)
    val sunRiseRangeStart = sunRiseRangeEnd.minusMinutes(30)

    //Tried making these a range, but ranges for LocalDateTime didn't work.
    val sunSetRangeStart = sunSet(combinedWeatherReport)
    val sunSetRangeEnd = sunSetRangeStart.plusMinutes(30)

    return when{
        time.isAfter(sunRiseRangeStart) && time.isBefore(sunRiseRangeEnd)-> R.drawable.ic_sunrise
        time.isAfter(sunSetRangeStart) && time.isBefore(sunSetRangeEnd) -> R.drawable.ic_sunset
        isDay(time,combinedWeatherReport) && isTwilight(time,combinedWeatherReport)-> R.drawable.ic_sun_big
        else -> R.drawable.ic_moon_clear
    }
}

private fun getCloudyImage(time:LocalDateTime?, temperature: Double?, combinedWeatherReport: CombinedWeatherReport?): Int {
    val freezing = 32.0

    val workingTemp = when {
        temperature != null -> temperature
        combinedWeatherReport?.temp != null -> combinedWeatherReport.temp
        else -> 33.0
    }

    return when{
        isDay(time,combinedWeatherReport) && workingTemp <= freezing -> R.drawable.ic_cloudy_day_little_sun
        isDay(time,combinedWeatherReport) && workingTemp > freezing -> R.drawable.ic_cloudy_day_strong_sun
        workingTemp <= freezing -> R.drawable.ic_cloudy_day_little_sun
        workingTemp > freezing -> R.drawable.ic_cloudy_day_strong_sun
        else -> R.drawable.ic_cloud
    }
}

//TODO consider modifying isDay, isTwilight to an enum of DAY,NIGHT, TWILIGHT_MORNING, TWILIGHT_EVENING
//Twilight is 20-30 min, set it for 30 min here.
//TWILIGHT : Morning (sunRise - 30 min)..SunRise.  Evening:sunSet..(sunSet+30 min)
fun isTwilight(time: LocalDateTime?, combinedWeatherReport: CombinedWeatherReport?): Boolean{
    val sunRise = sunRise(combinedWeatherReport)  // default to 6 am
    val sunSet = sunSet(combinedWeatherReport)   // default to 7 pm
    val currentTime = time ?: LocalDateTime.now()
    val howLongIsTwilight = Duration.ofMinutes(30)
    return when {
        currentTime.isAfter(sunRise.minus(howLongIsTwilight)) && currentTime.isBefore(sunRise.plusSeconds(1)) -> true
        currentTime.isAfter(sunSet.minusSeconds(1)) && currentTime.isBefore(sunSet.plus(howLongIsTwilight))  -> true
//        in (sunRise - howLongIsTwilight) until (sunRise) -> true
//        in (sunSet) until (sunSet + howLongIsTwilight) -> true
        else -> false
    }
}

//private fun workingTime(time:LocalDateTime?) = time ?: 1200  //TODO make right.

private fun sunRise(combinedWeatherReport: CombinedWeatherReport?) =
    combinedWeatherReport?.sunRise ?: LocalDateTime.of(LocalDate.now(), LocalTime.of(6,30))  // TODO default to 6 am

private fun sunSet(combinedWeatherReport: CombinedWeatherReport?) =
    combinedWeatherReport?.sunSet ?: LocalDateTime.of(LocalDate.now(), LocalTime.of(18,30))  // TODO default to 10 pm

fun isDay(time: LocalDateTime?, combinedWeatherReport: CombinedWeatherReport?): Boolean{
    val workingTime = time ?: LocalDateTime.now()
    return when {

        workingTime.isAfter(sunRise(combinedWeatherReport)) && workingTime.isAfter(sunSet(combinedWeatherReport).minusMinutes(1)) -> {
            true
        }
        else -> false
    }
}

private fun convertTimeFromEpocInSecondsToLocalDataTimeType(fromEpoch: Long, offset: Int): LocalDateTime {
    return LocalDateTime.ofEpochSecond(fromEpoch, 0, ZoneOffset.ofTotalSeconds(offset))
}

//private fun getTimeNow(): LocalTime {
//    //TODO FIX THIS
//    return LocalDateTime.now()  // get time somehow.
//}
