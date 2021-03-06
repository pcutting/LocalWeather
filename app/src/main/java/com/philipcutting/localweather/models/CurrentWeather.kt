package com.philipcutting.localweather.models



/**
 *

https://openweathermap.org/current

api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

Parameters
lat, lon	required	Geographical coordinates (latitude, longitude)
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
mode	optional	Response format. Possible values are xml and html. If you don't use the mode parameter format is JSON by default. Learn more
units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	You can use this parameter to get the output in your language. Learn more
Examples of API calls

api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid={API key}

coord
    coord.lon City geo location, longitude
    coord.lat City geo location, latitude
weather (more info Weather condition codes)
    weather.id Weather condition id
    weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
    weather.description Weather condition within the group. You can get the output in your language. Learn more
    weather.icon Weather icon id
base Internal parameter
main
    main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    main.feels_like Temperature. This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    main.pressure Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
    main.humidity Humidity, %
    main.temp_min Minimum temperature at the moment. This is minimal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    main.temp_max Maximum temperature at the moment. This is maximal currently observed temperature (within large megalopolises and urban areas). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    main.sea_level Atmospheric pressure on the sea level, hPa
    main.grnd_level Atmospheric pressure on the ground level, hPa
wind
    wind.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    wind.deg Wind direction, degrees (meteorological)
    wind.gust Wind gust. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour
clouds
    clouds.all Cloudiness, %
rain
    rain.1h Rain volume for the last 1 hour, mm
    rain.3h Rain volume for the last 3 hours, mm
snow
    snow.1h Snow volume for the last 1 hour, mm
    snow.3h Snow volume for the last 3 hours, mm
dt Time of data calculation, unix, UTC
sys
    sys.type Internal parameter
    sys.id Internal parameter
    sys.message Internal parameter
    sys.country Country code (GB, JP etc.)
    sys.sunrise Sunrise time, unix, UTC
    sys.sunset Sunset time, unix, UTC
timezone Shift in seconds from UTC
id City ID
name City name
cod Internal parameter

 */



data class CurrentWeather(
        val coordinate: Coordinate,
        val weather:  MutableList<Weather>,
        val base: String,
        val mainStats: MainStats,
        val visibilityFactor: Int, // as "visibility"
        val wind: Wind,
        val clouds: Clouds,
        val dt: Int,   // time of data collection.
        val sys: Sys,
        val timezone: Int,  // Shift in seconds from UTC
        val id: Int,
        val name: String,
        val cod: Int   // internal parameter
)

 data class Coordinate(
    val lon: Double,
    val lat: Double
 )

 data class Weather(
    val id: Int,
    val mainCondition: String,
    val description: String,
    val icon: String
 )

// ** As "Main" for name.
data class MainStats(
    val temp : Double,
    val feelsLike : Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Clouds (
    val all: Int
)

data class Sys (
    val typeOfWeather: Int,
    val id: Int,
    val message: String,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
