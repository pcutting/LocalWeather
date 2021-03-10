package com.philipcutting.localweather.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DailyWeatherApi {
    @GET("forecast")
    fun getDailyWeatherItems(@QueryMap dailyWeatherQueryMap: HashMap<String, String>): Call<DailyWeatherItems>

}