package com.philipcutting.localweather.networking

import android.util.Log
import com.philipcutting.localweather.models.*
import com.philipcutting.localweather.models.WeatherSegment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/*
https://openweathermap.org/api/one-call-api

Current weather
Minute forecast for 1 hour
Hourly forecast for 48 hours
Daily forecast for 7 days
National weather alerts
Historical weather data for the previous 5 days

https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}


Parameters
lat, lon	required	Geographical coordinates (latitude, longitude)
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
exclude	optional	By using this parameter you can exclude some parts of the weather data from the API response. It should be a comma-delimited list (without spaces).
Available values:
        current  minutely hourly daily alerts
units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	You can use the lang parameter to get the output in your language. Learn more

https://api.openweathermap.org/data/2.5/onecall?lat=42.694492&lon=23.321964&exclude=minutely,alerts&units=imperial&appid=56786491fcb4331ffe593f9ff0b28cd1

 */

object NetworkOneCallAll {
    private const val TAG = "NetworkOneCallAll"
    private const val APIKey = "56786491fcb4331ffe593f9ff0b28cd1"

    //sofia
    private const val testXAxis = 42.694492
    private const val testYAxis = 23.321964

    private var lon = testXAxis
    private var lat = testYAxis

    private val weatherQueryMap = hashMapOf(
            "lon" to lon.toString(),
            "lat" to lat.toString(),
            "units" to "imperial",
            "exclude" to "minutely,alerts,hourly,daily",
            "appid" to APIKey
    )

    var currentWeather: CurrentWeatherReport? = null
    var dailyWeather: DailyWeatherReports? = null
    var hourlyWeather: HourlyWeatherReports? = null
    val combinedWeatherReport = CombinedWeatherReport(
            currentWeather,
            dailyWeather,
            hourlyWeather)

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY )
    private val client = OkHttpClient.Builder().addInterceptor(logger)

    private val oneCallApi : OneCallApi
        get(){
            Log.d(TAG, "onecallapi to weather")
            val retroBuilder = Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .client(client.build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(OneCallApi::class.java)
            Log.d(TAG, "end of oneCallApi")
            return retroBuilder
        }

    fun getOneCallWeather(
        onSuccess: (CurrentWeatherReport?) -> CurrentWeatherReport?
    ) : CurrentWeatherReport? {
        oneCallApi.getOneCallWeatherItems(weatherQueryMap).enqueue(OneCallCallback(onSuccess))
        return currentWeather
    }

    private class OneCallCallback(
            private val onSuccess: (CurrentWeatherReport?) -> CurrentWeatherReport?
    ) : Callback<OneCallCurrentWeatherHourlyAndSevenDayForecastItems> {
        override fun onResponse(
                call: Call<OneCallCurrentWeatherHourlyAndSevenDayForecastItems>,
                response: Response<OneCallCurrentWeatherHourlyAndSevenDayForecastItems>
        ) {
            val reports = response.body()?.toCurrent()
            currentWeather = reports
            onSuccess(currentWeather)

            Log.d(TAG, "End of onResponse: $currentWeather")
            //onSuccess(reports)
        }

        override fun onFailure(call: Call<OneCallCurrentWeatherHourlyAndSevenDayForecastItems>, t: Throwable) {
            Log.d(TAG, "oOneCallCallback.onFailure : $t")
        }
    }

    private fun OneCallCurrentWeatherHourlyAndSevenDayForecastItems.toCurrent()
    : CurrentWeatherReport
    {
        return CurrentWeatherReport(
                latitude = this.latitude,
                longitude = this.longitude,
                timezone = this.timezone,
                timezoneOffset = this.timezoneOffset,

                dt = WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.dt ?: 0,
                    this.timezoneOffset ?: 0
                ),

                sunRise =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.sunRise ?: 0,
                    this.timezoneOffset ?: 0),
                sunSet =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.sunSet ?: 0,
                    this.timezoneOffset ?: 0),
                temp = this.currentWeather?.temp,
                feelsLike = this.currentWeather?.feelsLike,
                pressure = this.currentWeather?.pressure,
                humidity = this.currentWeather?.humidity,
                dewPoint = this.currentWeather?.dewPoint,
                uVIndex =  this.currentWeather?.uVIndex,
                cloudinessPercent = this.currentWeather?.cloudinessPercent,
                visibilityMeters = this.currentWeather?.visibilityMeters,
                windSpeed = this.currentWeather?.windSpeed,
                windGust = this.currentWeather?.windGust,
                degreeWindDirection = this.currentWeather?.degreeWindDirection,
                weather = WeatherSegment(
                    id = this.currentWeather?.weather?.firstOrNull()?.id,
                    mainTitle =  this.currentWeather?.weather?.firstOrNull()?.mainTitle,
                    description =  this.currentWeather?.weather?.firstOrNull()?.description,
                    icon =  this.currentWeather?.weather?.firstOrNull()?.icon
                ),
                rainVolume = this.currentWeather?.rainVolume,
                snowVolume = this.currentWeather?.snowVolume
        )
    }



}