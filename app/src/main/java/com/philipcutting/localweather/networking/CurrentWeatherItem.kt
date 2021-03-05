package com.philipcutting.localweather.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CurrentWeatherItem(
    @Json(name = "coord") val coordinate: Coordinate,
    @Json(name = "weather") val weather: Weather,
    @Json(name = "base") val base: String,
    @Json(name = "main") val mainStats: MainStats,
    @Json(name = "visibility") val visibilityFactor: Int, // as "visibility"
    @Json(name = "wind") val wind: Wind,
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "dt") val dt: Int,   // time of data collection.
    @Json(name = "sys") val sys: Sys,
    @Json(name = "timezone") val timezone: Int,  // Shift in seconds from UTC
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "cod") val cod: Int   // internal parameter
)

@JsonClass(generateAdapter = true)
data class Coordinate(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

// ** As "Main" for name.
@JsonClass(generateAdapter = true)
data class MainStats(
    @Json(name = "temp") val temp : Double,
    @Json(name = "feels_like") val feelsLike : Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val deg: Int
)

@JsonClass(generateAdapter = true)
data class Clouds (
    @Json(name = "all") val all: Int
)

@JsonClass(generateAdapter = true)
data class Sys (
    @Json(name = "type") val type: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "message") val message: String,
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: Int,
    @Json(name = "sunset") val sunset: Int
)
