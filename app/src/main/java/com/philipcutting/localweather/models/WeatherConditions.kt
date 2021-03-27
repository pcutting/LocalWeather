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
    CLEAR(R.string.clear, listOf(800));

    fun getImageResource(
        time:LocalDateTime?,
        temperature: Double?,
        currentWeather: CurrentWeatherReport?
    ) : Int {
        return when (this) {
            CLOUDY -> getCloudyImage(time, temperature, currentWeather)
            RAIN -> getRainyImage(currentWeather)
            CLEAR -> getClearImage(time,currentWeather)
            DRIZZLE -> getDrizzlyImage(time, currentWeather)
            SNOW  -> getSnowyImage()
            THUNDER -> getThunderyImage()
            ATMOSPHERE -> getAtmosphericImage()
        }
    }

    companion object {
        fun getConditionFromCode(code: Int): WeatherConditions?{
            return values().firstOrNull {
                it.codes.contains(code)
            }
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

private fun getDrizzlyImage(time: LocalDateTime? , currentWeatherReport: CurrentWeatherReport?): Int {
    return when{
        isDay(time, currentWeatherReport)  -> R.drawable.ic_drizzle_day
        else -> R.drawable.ic_drizzle_night
    }
}

private fun getRainyImage(currentWeatherReport: CurrentWeatherReport?): Int {
    return when{
        currentWeatherReport?.windSpeed != null && currentWeatherReport.windSpeed > 20.0  -> R.drawable.ic_rain_windy
        else -> R.drawable.ic_rain
    }
}

private fun getClearImage(conditionTime:LocalDateTime?, currentWeatherReport: CurrentWeatherReport?): Int {
    //TODO may have a bug with the date component here.
    val time = conditionTime ?: LocalDateTime.now()

    val sunRiseRangeEnd = sunRise(currentWeatherReport)
    val sunRiseRangeStart = sunRiseRangeEnd.minusMinutes(30)

    //Tried making these a range, but ranges for LocalDateTime didn't work.
    val sunSetRangeStart = sunSet(currentWeatherReport)
    val sunSetRangeEnd = sunSetRangeStart.plusMinutes(30)

    return when{
        time.isAfter(sunRiseRangeStart) && time.isBefore(sunRiseRangeEnd)-> R.drawable.ic_sunrise
        time.isAfter(sunSetRangeStart) && time.isBefore(sunSetRangeEnd) -> R.drawable.ic_sunset
        isDay(time,currentWeatherReport) && isTwilight(time,currentWeatherReport)-> R.drawable.ic_sun_big
        else -> R.drawable.ic_moon_clear
    }
}

private fun getCloudyImage(time:LocalDateTime?, temperature: Double?, currentWeatherReport: CurrentWeatherReport?): Int {
    val freezing = 32.0
    val workingTemp:Double = temperature
        ?: if(currentWeatherReport?.temp != null) {
            currentWeatherReport.temp
        }else {
            freezing+1
        }
    return when{
        isDay(time,currentWeatherReport) && workingTemp <= freezing -> R.drawable.ic_cloudy_day_little_sun
        isDay(time,currentWeatherReport) && workingTemp > freezing -> R.drawable.ic_cloudy_day_strong_sun
        workingTemp <= freezing -> R.drawable.ic_cloudy_day_little_sun
        workingTemp > freezing -> R.drawable.ic_cloudy_day_strong_sun
        else -> R.drawable.ic_cloud
    }
}

//TODO consider modifying isDay, isTwilight to an enum of DAY,NIGHT, TWILIGHT_MORNING, TWILIGHT_EVENING
//Twilight is 20-30 min, set it for 30 min here.
//TWILIGHT : Morning (sunRise - 30 min)..SunRise.  Evening:sunSet..(sunSet+30 min)
fun isTwilight(time: LocalDateTime?, currentWeatherReport: CurrentWeatherReport?): Boolean{
    val sunRise = sunRise(currentWeatherReport)  // default to 6 am
    val sunSet = sunSet(currentWeatherReport)   // default to 7 pm
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

private fun sunRise(currentWeatherReport: CurrentWeatherReport?) =
    currentWeatherReport?.sunRise ?: LocalDateTime.of(LocalDate.now(), LocalTime.of(6,30))  // TODO default to 6 am

private fun sunSet(currentWeatherReport: CurrentWeatherReport?) =
    currentWeatherReport?.sunSet ?: LocalDateTime.of(LocalDate.now(), LocalTime.of(18,30))  // TODO default to 10 pm

fun isDay(time: LocalDateTime?, currentWeatherReport: CurrentWeatherReport?): Boolean{
    val workingTime = time ?: LocalDateTime.now()
    return when {

        workingTime.isAfter(sunRise(currentWeatherReport)) && workingTime.isAfter(sunSet(currentWeatherReport).minusMinutes(1)) -> {
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
