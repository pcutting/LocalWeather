package com.philipcutting.localweather.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CurrentWeatherApi {

    @GET("weather")
    fun getCurrentWeatherItem(@QueryMap currentWeatherQueryMap: HashMap<String, String>): Call<CurrentWeatherItem>

}