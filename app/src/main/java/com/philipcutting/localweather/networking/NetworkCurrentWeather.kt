package com.philipcutting.localweather.networking

import android.util.Log
import com.philipcutting.localweather.models.Coordinate
import com.philipcutting.localweather.models.CurrentWeather

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

Parameters
lat, lon	required	Geographical coordinates (latitude, longitude)
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
mode	optional	Response format. Possible values are xml and html. If you don't use the mode parameter format is JSON by default. Learn more
units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	You can use this parameter to get the output in your language. Learn more
Examples of API calls

api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid={API key}

 */

object NetworkCurrentWeather {
    private val TAG = "NetworkWeather"

    private val APIKey = "56786491fcb4331ffe593f9ff0b28cd1"


    //sofia
    private val testXAxis = 42.694492
    private val testYAxis = 23.321964

    private var lon = testXAxis  // {x} Number of x tile coordinate
    private var lat = testYAxis  // {y} number of y tile coordinate

    private val currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=$APIKey"

    private var currentWeather: CurrentWeather? = null

    private val client = OkHttpClient()

    private val currentWeatherApi : CurrentWeatherApi
        get() {
            Log.d(TAG, "testing currentWeatherApi in NetworkCurrentWeather.")
            return Retrofit.Builder()
                .baseUrl(currentWeatherUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(CurrentWeatherApi::class.java)
        }


    fun getCurrentWeatherItem(onSuccess: (CurrentWeather?) -> Unit) {
        if(currentWeather != null) {
            onSuccess(currentWeather)
        }
        currentWeatherApi.getCurrentWeather().enqueue(CurrentWeatherCallback(onSuccess))
    }

    private class CurrentWeatherCallback(
        private val onSuccess: (CurrentWeather?) -> Unit
    ): Callback<CurrentWeatherItem> {

        override fun onResponse(
            call: Call<CurrentWeatherItem>,
            response: Response<CurrentWeatherItem>
        ) {
            //val currentWeatherItem = response.body()?.currentWeatherItem?.map {it.toCurrentWeather}
            val currentWeatherItemNetwork = response.body()
                ?.toCurrentWeather()
                ?: null

            currentWeather = currentWeatherItemNetwork
            onSuccess(currentWeather)
            Log.d(TAG, "onResponse Network, $currentWeather")

        }

        override fun onFailure(call: Call<CurrentWeatherItem?>, t: Throwable) {
            Log.d(TAG, "Error on response from server.")
        }


    }

    private fun CurrentWeatherItem.toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            coordinate = Coordinate(
                coordinate.lon,
                coordinate.lat
            ),
            weather = com.philipcutting.localweather.models.Weather(
                id=id,
                main = weather.main,
                description = weather.description,
                icon = weather.icon
                ),
            base = base,
            mainStats = com.philipcutting.localweather.models.MainStats(
               temp = mainStats.temp,
               feelsLike = mainStats.feelsLike,
               tempMin = mainStats.tempMin,
               tempMax = mainStats.tempMax,
               pressure = mainStats.pressure,
               humidity = mainStats.humidity
            ),
            visibilityFactor = visibilityFactor,
            wind = com.philipcutting.localweather.models.Wind(
                speed = wind.speed,
                deg = wind.deg
            ),
            clouds = com.philipcutting.localweather.models.Clouds(
                all = clouds.all
            ),
            dt = dt,
            sys = com.philipcutting.localweather.models.Sys(
                type = sys.type,
                id = sys.id,
                message = sys.message,
                country = sys.country,
                sunrise = sys.sunrise,
                sunset = sys.sunset
            ),
            timezone = timezone,
            id = id,
            name = name,
            cod = cod


        )
    }

//    private fun CurrentWeather.toCoord(): Coordinate {
//        return Coord(
//            lon = lon,
//            lat = lat
//        )
//    }

//    private fun  CurrentWeather.toWeather(): Weather {
//        return Weather(
//            id = id,
//            main = C ,
//            description = description,
//            icon = icon
//        )
//    }
}











