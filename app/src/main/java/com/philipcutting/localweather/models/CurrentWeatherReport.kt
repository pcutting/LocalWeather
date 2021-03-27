package com.philipcutting.localweather.models

import java.time.LocalDateTime

data class CurrentWeatherReport(
        var latitude:Double?,
        var longitude:Double?,
        var timezone: String?,
        var timezoneOffset: Int?,
        val dt: LocalDateTime?,
        val sunRise: LocalDateTime?,
        val sunSet: LocalDateTime?,
        val temp: Double?,  //Temperature?,
        val feelsLike: Double?, // FeelsLike?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,
        val uVIndex: Double?,  //UV Index
        val cloudinessPercent: Int?,
        val visibilityMeters: Int?,
        val windSpeed: Double?,
        val windGust: Double?,
        val degreeWindDirection: Int?,
        val weather: WeatherSegment?,
        val rainVolume: Int?, //In mm metric
        val snowVolume: Int?, //In mm metric
)


data class WeatherSegment(
    val id: Int?,
    val mainTitle: String?,
    val description: String?,
    val icon: String?
)

//data class Temperature(
//    val day: Double?,
//    val min: Double?,
//    val max: Double?,
//    val night: Double?,
//    val evening: Double?,
//    val morning: Double?
//)
//
//data class FeelsLike(
//    val day: Double?,
//    val night: Double?,
//    val evening: Double?,
//    val morning: Double?
//)