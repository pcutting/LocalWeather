package com.philipcutting.localweather.networking

data class DailyWeatherItems(
    val city: City,
    val cod: String,
    val message: Double,
    val cnt: Int,
    private val dailyWeatherItemsList : MutableList<DailyWeatherItem> = mutableListOf()


) {
    fun fiveDayForcast() =  dailyWeatherItemsList.subList(0,4)
}

data class City(
    val id: Int,
    val name: String,
    val coordinate: Coordinate,
    val country: String,
    val population: Int,
    val timezone: Int
)

data class DailyWeatherItem(
    val dt: String,
    val sunRise: Int,
    val sunSet: Int,
    val temp: Temperature,
    val feelsLike: FeelsLike,
    val pressure: Double,
    val humidity: Int,
    val windSpeed: Double,
    val degreeWindDirection: Int,
    val cloudinessPercent: Int,
    val rainVolume: Int, //In mm metric
    val snowVolume: Int, //In mm metric
    val probability: Double,
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val evening: Double,
    val morning: Double
)

data class  FeelsLike(
        val day: Double,
        val night: Double,
        val evening: Double,
        val morning: Double
)

