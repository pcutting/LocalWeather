package com.philipcutting.localweather.networking

import retrofit2.Call
import retrofit2.http.GET

interface CurrentWeatherApi {

    @GET("/")
    fun getCurrentWeatherItem(): Call<CurrentWeatherItem>

}