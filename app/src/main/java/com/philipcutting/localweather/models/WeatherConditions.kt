package com.philipcutting.localweather.models

import androidx.annotation.DrawableRes

/**
https://openweathermap.org/weather-conditions#Weather-Condition-Codes-2

 */

enum class WeatherConditions(
    val conditionDescription: String = "",
    @DrawableRes val imageResource : Int = -1,
    val codes: List<Int> = emptyList(),
){

    CLOUDY("Cloudy", -1, (801..804).toList()),
    DRIZZLE("Drizzle", -1, (300..321).toList()),
    RAIN("Rain", -1, (500..531).toList()),
    THUNDER("Thunder storm", -1, (200..232).toList()),
    SNOW("Snow", -1, (600..622).toList()),
    ATMOSPHERE("Atmospheric conditions", -1, (700..781).toList()),
    CLEAR("Clear", -1, listOf(800));

    companion object {

        //Do stuff.


    }
}