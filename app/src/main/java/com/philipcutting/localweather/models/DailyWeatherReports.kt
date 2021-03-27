package com.philipcutting.localweather.models

import com.philipcutting.localweather.networking.FeelsLikeItem
import com.philipcutting.localweather.networking.TemperatureItem



data class DailyWeatherReports(
//        var latitude:Double?,
//        var longitude:Double?,
//        var timezone: String?,
//        var timezoneOffset: Int?,
        var currentWeather: CurrentWeatherReport?,
        var dailyWeatherList: List<DailyWeatherOneCallItem>?
) {

    fun fiveDayForcast() =  dailyWeatherList?.subList(0,4) ?: emptyList()

//    data class CurrentWeather(
//        val dt: String?,
//        val sunRise: Int?,
//        val sunSet: Int?,
//        val temp: Temperature?,
//        val feelsLike: FeelsLike?,
//        val pressure: Double?,
//        val humidity: Int?,
//        val dewPoint: Double?,
//        val uVIndex: Double?,  //UV Index
//        val cloudinessPercent: Int?,
//        val visibilityMeters: Int?,
//        val windSpeed: Double?,
//        val windGust: Double?,
//        val degreeWindDirection: Int?,
//        val weather: List<WeatherSegment>?,
//        val rainVolume: Int?, //In mm metric
//        val snowVolume: Int?, //In mm metric
//    )


    data class WeatherSegment(
        val id: Int?,
        val main: String?,
        val description: String?,
        val icon: String?
    )

    data class DailyWeatherOneCallItem(
        val dt: String?,
        val sunRise: Int?,
        val sunSet: Int?,
        val temp: TemperatureItem?,
        val feelsLike: FeelsLikeItem?,
        val pressure: Double?,
        val humidity: Int?,
        val windSpeed: Double?,
        val degreeWindDirection: Int?,
        val windGust: Double?,
        val cloudinessPercent: Int?,
        val rainVolume: Int?, //In mm metric
        val snowVolume: Int?, //In mm metric
        val probability: Double?,
        val weather: List<WeatherSegment>? = null

    )

    data class Temperature(
        val day: Double?,
        val min: Double?,
        val max: Double?,
        val night: Double?,
        val evening: Double?,
        val morning: Double?
    )


    data class FeelsLike(
        val day: Double?,
        val night: Double?,
        val evening: Double?,
        val morning: Double?
    )
}