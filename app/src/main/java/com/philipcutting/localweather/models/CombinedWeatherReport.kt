package com.philipcutting.localweather.models

import java.time.LocalDateTime

data class CombinedWeatherReport(
        val timeLastPulled: LocalDateTime = LocalDateTime.now(),
        var latitude:Double?,
        var longitude:Double?,
        var timezone: String?,
        var timezoneOffset: Int?,
        val dt: LocalDateTime?,
        val sunRise: LocalDateTime?,
        val sunSet: LocalDateTime?,
        val temp: Double?,
        val feelsLike: Double?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,
        val uVIndex: Double?,
        val cloudinessPercent: Int?,
        val visibilityMeters: Int?,
        val windSpeed: Double?,
        val windGust: Double?,
        val degreeWindDirection: Int?,
        val weather: AllWeatherSegment?,
        val hourly: List<Hourly?>,
        val daily: List<Daily?>,
)

data class AllWeatherSegment(
        val id: Int?,
        val mainTitle: String?,
        val description: String?,
        val icon: String?,
        val condition: WeatherConditions?,
)

data class Hourly(
        val dt: LocalDateTime?,
        val temp: Double?,
        val feelsLike: Double?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,
        val uVIndex: Double?,  //UV Index
        val cloudinessPercent: Int?,
        val visibilityMeters: Int?,
        val windSpeed: Double?,
        val windGust: Double?,
        val degreeWindDirection: Int?,
        val weather: AllWeatherSegment?,
)

data class Daily(
        val dt: LocalDateTime?,
        val sunRise: LocalDateTime?,
        val sunSet: LocalDateTime?,
        val tempDay: Double?,
        val tempMin: Double?,
        val tempMax: Double?,
        val tempNight: Double?,
        val tempEvening: Double?,
        val tempMorning: Double?,
        val feelsLikeDay: Double?,
        val feelsLikeNight: Double?,
        val feelsLikeEvening: Double?,
        val feelsLIkeMorning: Double?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,  //dew_point
        val uVIndex: Double?,  //UV Index
        val cloudinessPercent: Int?,
        val windSpeed: Double?,     //wind_speed
        val windGust: Double?,      //wind_gust
        val degreeWindDirection: Int?,  //wind_deg
        val weather: AllWeatherSegment?,
        val probabilityOfRain: Double?, //pop
)

