package com.philipcutting.localweather.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OneCallCurrentWeatherHourlyAndSevenDayForecastItems(
        @Json(name = "lat") var latitude:Double?,
        @Json(name= "lon") var longitude:Double?,
        @Json(name= "timezone") var timezone: String?,
        @Json(name= "timezone_offset") var timezoneOffset: Int?,
        @Json(name= "current") var currentWeather: CurrentWeather?,
        @Json(name= "hourly") var hourlyWeathers: List<HourlyWeatherItem>?,
        @Json(name= "daily") var dailyWeather: List<DailyWeatherOneCallItem>?
)

@JsonClass(generateAdapter = true)
data class CurrentWeather(
    @Json(name= "dt") var dt: Long?,
    @Json(name= "sunrise") var sunRise: Long?,
    @Json(name= "sunset") var sunSet: Long?,
    @Json(name= "temp") var temp: Double?,  // TemperatureItem?,
    @Json(name= "feels_like") var feelsLike: Double?, // FeelsLikeItem?,
    @Json(name= "pressure") var pressure: Double?,
    @Json(name= "humidity") var humidity: Int?,
    @Json(name= "dew_point") var dewPoint: Double?,
    @Json(name= "uvi") var uVIndex: Double?,  //UV Index
    @Json(name= "clouds") var cloudinessPercent: Int?,
    @Json(name= "visibility") var visibilityMeters:Int?,
    @Json(name= "wind_speed") var windSpeed: Double?,
    @Json(name= "wind_gust") var windGust: Double?,
    @Json(name= "wind_deg") var degreeWindDirection: Int?,
    @Json(name= "weather") var weather: List<WeatherSegment>?,
    @Json(name= "rain") var rainVolume: Int?, //In mm metric
    @Json(name= "snow") var snowVolume: Int?, //In mm metric
)

@JsonClass(generateAdapter = true)
data class WeatherSegment(
    @Json(name= "id") var id: Int?,
    @Json(name= "main") var mainTitle: String?,
    @Json(name= "description") var description: String?,
    @Json(name= "icon") var icon: String?
)

@JsonClass(generateAdapter = true)
data class HourlyWeatherItem(
    @Json(name= "dt") var dt: String?,
    @Json(name= "sunrise") var sunRise: Int?,
    @Json(name= "sunset") var sunSet: Int?,
    @Json(name= "temp") var temp: TemperatureItem?,
    @Json(name= "feels_like") var feelsLike: FeelsLikeItem?,
    @Json(name= "pressure") var pressure: Double?,
    @Json(name= "humidity") var humidity: Int?,
    @Json(name= "dew_point") var dewPoint: Double?,
    @Json(name= "uvi") var uVIndex: Double?,  //UV Index
    @Json(name= "clouds") var cloudinessPercent: Int?,
    @Json(name= "visibility") var visibilityMeters:Int?,
    @Json(name= "wind_speed") var windSpeed: Double?,
    @Json(name= "wind_gust") var windGust: Double?,
    @Json(name= "wind_deg") var degreeWindDirection: Int?,
    @Json(name= "weather") var weather: List<WeatherSegment>?,
    @Json(name= "rain") var rainVolume: Int?, //In mm metric
    @Json(name= "snow") var snowVolume: Int? //In mm metric
)

@JsonClass(generateAdapter = true)
data class DailyWeatherOneCallItem(
    @Json(name= "dt") var dt: String?,
    @Json(name= "sunrise") var sunRise: Int?,
    @Json(name= "sunset") var sunSet: Int?,
    @Json(name= "temp") var temp: TemperatureItem?,
    @Json(name= "feels_like") var feelsLike: FeelsLikeItem?,
    @Json(name= "pressure") var pressure: Double?,
    @Json(name= "humidity") var humidity: Int?,
    @Json(name= "wind_speed") var windSpeed: Double?,
    @Json(name= "wind_deg") var degreeWindDirection: Int?,
    @Json(name= "wind_gust") var windGust:Double?,
    @Json(name= "clouds") var cloudinessPercent: Int?,
    @Json(name= "rain") var rainVolume: Int?, //In mm metric
    @Json(name= "snow") var snowVolume: Int?, //In mm metric
    @Json(name= "pop") var probability: Double?,
    @Json(name= "weather") var weather: List<WeatherSegment>? = null
)

@JsonClass(generateAdapter = true)
data class TemperatureItem(
    @Json(name= "day") var day: Double?,
    @Json(name= "min") var min: Double?,
    @Json(name= "max") var max: Double?,
    @Json(name= "night") var night: Double?,
    @Json(name= "eve") var evening: Double?,
    @Json(name= "morn") var morning: Double?
)

@JsonClass(generateAdapter = true)
data class  FeelsLikeItem(
    @Json(name= "day") var day: Double?,
    @Json(name= "night") var night: Double?,
    @Json(name= "eve") var evening: Double?,
    @Json(name= "morn") var morning: Double?
)
