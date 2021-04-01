package com.philipcutting.localweather.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OneCallApi {
    @GET("onecall")
    //prefix with suspend ???
    fun getOneCallWeatherItems(@QueryMap weatherQueryMap: HashMap<String, String>): Call<OneCallWeatherItem>

}