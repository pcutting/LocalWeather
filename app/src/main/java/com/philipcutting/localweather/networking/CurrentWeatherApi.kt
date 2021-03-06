package com.philipcutting.localweather.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CurrentWeatherApi {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherItem(@QueryMap currentWeatherQueryMap: HashMap<String, String>): Call<CurrentWeatherItem>

}