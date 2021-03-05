package com.philipcutting.localweather.models

/**
api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}

Parameters
lat, lon	required	coordinates of the location of your interest
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
cnt	optional	A number of days up to 16, which will be returned in the API response (from 1 to 16). Learn more
mode	optional	Data format. Possible values are: json, xml. If the mode parameter is empty the format is JSON by default. Learn more
units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	Language code. Learn more

city
    city.id City ID
    city.name City name
    city.coord
        city.coord.lat City geo location, latitude
        city.coord.lon City geo location, longitude
country Country code (GB, JP etc.)
population Internal parameter
timezone Shift in seconds from UTC
cod Internal parameter
message Internal parameter
cnt A number of days returned in the API response
list
    list.dt Time of data forecasted
    list.temp
    list.temp.day Day temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.temp.min Min daily temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.temp.max Max daily temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.temp.night Night temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.temp.eve Evening temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.temp.morn Morning temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
list.feels_like
    list.feels_like.day Day temperature.This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.feels_like.night Night temperature.This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.feels_like.eve Evening temperature.This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
    list.feels_like.morn Morning temperature. This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
list.pressure Atmospheric pressure on the sea level, hPa
list.humidity Humidity, %
list.weather (more info Weather condition codes)
    list.weather.id Weather condition id
    list.weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
    list.weather.description Weather condition within the group. You can get the output in your language. Learn more.
    list.weather.icon Weather icon id
list.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
list.deg Wind direction, degrees (meteorological)
list.clouds Cloudiness, %
list.rain Precipitation volume, mm
list.snow Snow volume, mm
list.pop Probability of precipitation

 */

data class DailyWeather(
        val id : String
)
{
//
}

