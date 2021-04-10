package com.philipcutting.localweather.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OneCallWeatherItem(
    @Json(name = "lat") val latitude:Double?,
    @Json(name= "lon") val longitude:Double?,
    @Json(name= "timezone") val timezone: String?,
    @Json(name= "timezone_offset") val timezoneOffset: Int?,
    @Json(name= "current") val currentWeather: CurrentWeather?,
    @Json(name= "hourly") val hourlyWeathers: List<HourlyWeatherItem>?,
    @Json(name= "daily") val dailyWeather: List<DailyWeatherOneCallItem>?
)

@JsonClass(generateAdapter = true)
data class CurrentWeather(
    @Json(name= "dt") val dt: Long?,
    @Json(name= "sunrise") val sunRise: Long?,
    @Json(name= "sunset") val sunSet: Long?,
    @Json(name= "temp") val temp: Double?,  // TemperatureItem?,
    @Json(name= "feels_like") val feelsLike: Double?, // FeelsLikeItem?,
    @Json(name= "pressure") val pressure: Double?,
    @Json(name= "humidity") val humidity: Int?,
    @Json(name= "dew_point") val dewPoint: Double?,
    @Json(name= "uvi") val uVIndex: Double?,  //UV Index
    @Json(name= "clouds") val cloudinessPercent: Int?,
    @Json(name= "visibility") val visibilityMeters:Int?,
    @Json(name= "wind_speed") val windSpeed: Double?,
    @Json(name= "wind_gust") val windGust: Double?,
    @Json(name= "wind_deg") val degreeWindDirection: Int?,
    @Json(name= "weather") val weather: List<WeatherSegment>?,
    @Transient @Json(name= "rain") val rainVolume: Int? = null, //In mm metric
    @Transient @Json(name= "snow") val snowVolume: Int? = null //In mm metric
)

@JsonClass(generateAdapter = true)
data class WeatherSegment(
    @Json(name= "id") val id: Int?,
    @Json(name= "main") val mainTitle: String?,
    @Json(name= "description") val description: String?,
    @Json(name= "icon") val icon: String?
)

@JsonClass(generateAdapter = true)
data class HourlyWeatherItem(
    @Json(name= "dt") val dt: Long?,
    @Json(name= "temp") val temp: Double?,
    @Json(name= "feels_like") val feelsLike: Double?,
    @Json(name= "pressure") val pressure: Double?,
    @Json(name= "humidity") val humidity: Int?,
    @Json(name= "dew_point") val dewPoint: Double?,
    @Json(name= "uvi") val uVIndex: Double?,  //UV Index
    @Json(name= "clouds") val cloudinessPercent: Int?,
    @Json(name= "visibility") val visibilityMeters:Int?,
    @Json(name= "wind_speed") val windSpeed: Double?,
    @Json(name= "wind_gust") val windGust: Double?,
    @Json(name= "wind_deg") val degreeWindDirection: Int?,
    @Json(name= "weather") val weather: List<WeatherSegment>?,
    @Transient @Json(name= "rain") val rainVolume: Int? = null, //In mm metric
    @Transient @Json(name= "snow") val snowVolume: Int? = null, //In mm metric
)

@JsonClass(generateAdapter = true)
data class DailyWeatherOneCallItem(
    @Json(name= "dt") val dt: Long?,
    @Json(name= "sunrise") val sunRise: Long?,
    @Json(name= "sunset") val sunSet: Long?,
    @Json(name= "temp") val temp: TemperatureItem?,
    @Json(name= "feels_like") val feelsLike: FeelsLikeItem?,
    @Json(name= "pressure") val pressure: Double?,
    @Json(name= "humidity") val humidity: Int?,
    @Json(name= "dew_point") val dewPoint: Double?,
    @Json(name= "wind_speed") val windSpeed: Double?,
    @Json(name= "wind_deg") val degreeWindDirection: Int?,
    @Json(name= "weather") val weather: List<WeatherSegment>?,
    @Json(name= "wind_gust") val windGust:Double?,
    @Json(name= "clouds") val cloudinessPercent: Int?,
    @Json(name= "pop") val probabilityOfRain: Double?,
    @Json(name= "uvi") val uVIndex: Double?,  //UV Index



)

@JsonClass(generateAdapter = true)
data class TemperatureItem(
    @Json(name= "day") val day: Double?,
    @Json(name= "min") val min: Double?,
    @Json(name= "max") val max: Double?,
    @Json(name= "night") val night: Double?,
    @Json(name= "eve") val evening: Double?,
    @Json(name= "morn") val morning: Double?
)



@JsonClass(generateAdapter = true)
data class  FeelsLikeItem(
    @Json(name= "day") val day: Double?,
    @Json(name= "night") val night: Double?,
    @Json(name= "eve") val evening: Double?,
    @Json(name= "morn") val morning: Double?
)
