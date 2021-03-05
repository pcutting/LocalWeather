package com.philipcutting.localweather.networking

import com.philipcutting.localweather.models.CurrentWeather
import retrofit2.Call
import retrofit2.http.GET

interface CurrentWeatherApi {

    @GET("/")
    fun getCurrentWeather(): Call<CurrentWeather?>

}