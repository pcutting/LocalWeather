package com.philipcutting.localweather.models

import com.philipcutting.localweather.networking.FeelsLike
import com.philipcutting.localweather.networking.Temperature

/**


*/

data class HourlyWeatherReports(
    var latitude:Int?,
    var longitude:Int?,
    var timezone: String?,
    var timezoneOffset: Int?,
    var currentWeather: CurrentWeather?,
    private var hourlyWeatherList: List<HourlyWeatherOneCallReport>?
) {

    fun fiveHoursOfReports() =  hourlyWeatherList.subList(0,4)

    data class CurrentWeather(
        val dt: String?,
        val sunRise: Int?,
        val sunSet: Int?,
        val temp: Temperature?,
        val feelsLike: FeelsLike?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,
        val uVIndex: Double?,  //UV Index
        val cloudinessPercent: Int?,
        val visibilityMeters: Int?,
        val windSpeed: Double?,
        val windGust: Double?,
        val degreeWindDirection: Int?,
        val weather: List<WeatherSegment>?,
        val rainVolume: Int?, //In mm metric
        val snowVolume: Int?, //In mm metric
    )

    data class WeatherSegment(
        val id: Int?,
        val main: String?,
        val description: String?,
        val icon: String?
    )

    data class HourlyWeatherOneCallReport(
        val dt: String?,
        val sunRise: Int?,
        val sunSet: Int?,
        val temp: Temperature?,
        val feelsLike: FeelsLike?,
        val pressure: Double?,
        val humidity: Int?,
        val dewPoint: Double?,
        val uVIndex: Double?,  //UV Index
        val cloudinessPercent: Int?,
        val visibilityMeters: Int?,
        val windSpeed: Double?,
        val windGust: Double?,
        val degreeWindDirection: Int?,
        val weather: List<WeatherSegment>?,
        val rainVolume: Int?, //In mm metric
        val snowVolume: Int? //In mm metric
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

    data class TemperatureItem(
        val day: Double?,
        val min: Double?,
        val max: Double?,
        val night: Double?,
        val evening: Double?,
        val morning: Double?
    )


    data class FeelsLikeItem(
        val day: Double?,
        val night: Double?,
        val evening: Double?,
        val morning: Double?
    )
}