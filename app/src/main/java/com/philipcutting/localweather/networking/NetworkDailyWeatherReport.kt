package com.philipcutting.localweather.networking

/*
api:
ref: https://openweathermap.org/forecast16

api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}

lat, lon	required	coordinates of the location of your interest
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
cnt	optional	A number of days, which will be returned in the API response (from 1 to 16). Learn more
mode	optional	Data format. Possible values are: json, xml. If the mode parameter is empty the format is JSON by default. Learn more
units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	Language code. Learn more

 */


object NetworkDailyWeatherReport {

    private val TAG = "NetworkDailyWeather"

    private val APIKey = "56786491fcb4331ffe593f9ff0b28cd1"

//    //sofia
//    private val testXAxis = 42.694492
//    private val testYAxis = 23.321964
//
//    private var lon = testXAxis
//    private var lat = testYAxis
//
//    private val dailyWeatherQueryMap = hashMapOf<String,String>(
//            "lon" to testXAxis.toString(),
//            "lat" to testYAxis.toString(),
//            "units" to "imperial",
//            "cnt" to "6",   //Days to request, set to 6 to have more offline data.
//            "appid" to APIKey
//    )
//
//
//    var dailyWeather: DailyWeatherItems? = null
//
//
//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    private val client = OkHttpClient.Builder().addInterceptor(logger)
//
//    //api.openweathermap.org/data/2.5/forecast/daily?lat=42.694492&lon=23.321964&cnt=6&appid=56786491fcb4331ffe593f9ff0b28cd1
//
//
//    private val dailyWeatherApi : DailyWeatherApi
//        get() {
//            Log.d(TAG, "testing DailyWeatherApi in NetworkDailyWeather.")
//            var retroBuilder =  Retrofit.Builder()
//                    .baseUrl("http://api.openweathermap.org/data/2.5/")
//                    .client(client.build())
//                    .addConverterFactory(MoshiConverterFactory.create())
//                    .build()
//                    .create(DailyWeatherApi::class.java)
//
//            Log.d(TAG, "retroBuilder:  ${retroBuilder.toString()}  ; client ${client}")
//            return retroBuilder
//        }
//
//
//    fun getDailyWeather(onSuccess: (DailyWeather?) -> Unit) {
//        if(dailyWeather != null) {
//            onSuccess(dailyWeather)
//        }
//        dailyWeatherApi
//                .getDailyWeatherItem(dailyWeatherQueryMap)
//                .enqueue(NetworkDailyWeather.DailyWeatherCallback(onSuccess))
//
//        Log.d(TAG, "getDailyWeather end: ${dailWeatherApi.toString()}")
//    }
//
//    class DailyWeatherCallback(
//            private val onSuccess: (DailyWeather?) -> Unit
//    ): Callback<DailyWeatherItem> {
//
//        override fun onResponse(
//                call: Call<DailyWeatherItem>,
//                response: Response<DailyWeatherItem>
//        ) {
//            Log.d(TAG, "onResponse Network Url : ${response.raw().toString()}")
//            val dailyWeatherItemNetwork = response.body()
//                    ?.toDailyWeather()
//                    ?: null
//            dailyWeather = dailyWeatherItemNetwork
//            onSuccess(dailyWeather)
//            Log.d(TAG, "2.onResponse Network, $dailyWeather; Url : ${response.raw().toString()}")
//        }
//
//        override fun onFailure(call: Call<DailyWeatherItem?>, t: Throwable) {
//
//            Log.d(TAG, "Error on response from server. $t")
//        }
//    }
//
//    private fun DailyWeatherItem.toDailyWeather(): DailyWeather {
//        val toDailyWeather = DailyWeather(
////                coordinate = Coordinate(
////                        lon = coordinate?.lon,
////                        lat = coordinate?.lat
////                ),
////                weather =
////                Weather(
////                        id=weather?.first()?.id,
////                        mainCondition = weather?.first()?.mainCondition,
////                        description = weather?.first()?.description,
////                        icon = weather?.first()?.icon
////                ),
////                base = base,
////                mainStats = MainStats(
////                        temp = mainStats?.temp,
////                        feelsLike = mainStats?.feelsLike,
////                        tempMin = mainStats?.tempMin,
////                        tempMax = mainStats?.tempMax,
////                        pressure = mainStats?.pressure,
////                        humidity = mainStats?.humidity
////                ),
////                visibilityFactor = visibilityFactor,
////                wind = Wind(
////                        speed = wind?.speed,
////                        deg = wind?.deg
////                ),
////                clouds = Clouds(
////                        all = clouds?.all
////                ),
////                dt = dt,
////                sys = Sys(
////                        typeOfWeather = sys?.typeOfWeather,
////                        id = sys?.id,
////                        message = sys?.message,
////                        country = sys?.country,
////                        sunrise = sys?.sunrise,
////                        sunset = sys?.sunset
////                ),
////                timezone = timezone,
////                id = id,
////                name = name,
////                cod = cod
//        )
//
//        //taken out of inline to make debugging easier.
//        Log.d(TAG, "Made a dailyWeather from json : ${toDailyWeather.toString()}")
//        return toDailyWeather
//    }
//

}