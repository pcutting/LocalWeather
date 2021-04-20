package com.philipcutting.localweather.networking

import android.content.Context
import android.util.Log
import com.philipcutting.localweather.R
import com.philipcutting.localweather.models.*
import com.philipcutting.localweather.repositories.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Instant
import com.philipcutting.localweather.networking.WeatherSegment as NetworkingWeatherSegment

object NetworkOneCallAll {
    private const val TAG = "NetworkOneCallAll"
    private const val APIKey = "56786491fcb4331ffe593f9ff0b28cd1"

    // These will limit how much data is processed,  save time, and use it to
    // make code more efficient while we can't effect how much data the api returns
    // based off the current api.
    val hoursReportedLimit = 5
    val daysReportedLimit = 5

    private var testDebugTimeVariableEnteringGetLocalWeather = Instant.now()
    private var testDebugTimeVariableLeavingOnResponse = Instant.now()

    //sofia, Bulgaria
    private const val testLat = 42.694492
    private const val testLon = 23.321964

    private var lat : Double? = null
    private var lon : Double? = null

    fun setLocation(location: LocationModel) {
        lat = location.latitude
        lon = location.longitude
    }

    private fun updateLocationFromRepository() {
        if (WeatherRepository.getLocation() != null) {
            Log.d(TAG, "updateLocationFromRepository() getLocation != null")
            lat = WeatherRepository.getLatitude()
            lon = WeatherRepository.getLongitude()

            weatherQueryMap["lat"] = lat.toString()
            weatherQueryMap["lon"] = lon.toString()
        }
    }

    private val weatherQueryMap = hashMapOf(
        "lat" to (lat?.toString() ?: "Null"),
        "lon" to (lon?.toString() ?: "Null"),
        "units" to "imperial",
        "exclude" to "minutely,alerts",
        "appid" to APIKey
    )

    fun getUnits(context:Context): String {
        return if (weatherQueryMap["units"]== "imperial") {
            context.getString(R.string.unit_imperial)
        } else {
            context.getString(R.string.unit_metric)
        }
    }

    fun getWindUnits(context: Context): String {
        return if (weatherQueryMap["units"]== "imperial") {
            context.getString( R.string.units_imperial_wind_speed)
        } else {
            context.getString(R.string.units_metric_wind_speed)
        }
    }

    var combinedWeatherReport : CombinedWeatherReport? = null

    //Low detail logging: Level.NONE, Max detail: BODY, ...
    private val logger = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.HEADERS )

    private val client = OkHttpClient.Builder().addInterceptor(logger)

    private val oneCallApi : OneCallApi
        get(){
//            Log.d(TAG, "oneCallAPI to weather")
            val retroBuilder = Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .client(client.build())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(OneCallApi::class.java)
            //Log.d(TAG, "end of oneCallApi")
            return retroBuilder
        }

    fun getOneCallWeather(
        onSuccess: (CombinedWeatherReport?) -> Unit
    ){
//        Log.d(TAG, "getOneCallWeather ${WeatherRepository.getLocation().toString()}")
        testDebugTimeVariableEnteringGetLocalWeather = Instant.now()
        Log.d(TAG, "getOneCallWeather location before updateFromRepo ${WeatherRepository.getLocation()}")
        updateLocationFromRepository()
        Log.d(TAG, "getOneCallWeather location after updateFromRepo ${WeatherRepository.getLocation()}")

        if (WeatherRepository.getLocation()== null) {
            return
        }

        oneCallApi
                .getOneCallWeatherItems(weatherQueryMap)
                .enqueue(OneCallCallback(onSuccess))
    }

    private class OneCallCallback(
            private val onSuccess:
            (CombinedWeatherReport?) -> Unit
    ) : Callback<OneCallWeatherItem> {
        override fun onResponse(
                call: Call<OneCallWeatherItem>,
                response: Response<OneCallWeatherItem>
        ) {
            combinedWeatherReport = response.body()?.toCurrent()
            testDebugTimeVariableLeavingOnResponse = Instant.now()
//            Log.d(TAG,"onResponse {Time: ${testDebugTimeVariableLeavingOnResponse.toEpochMilli() - testDebugTimeVariableEnteringGetLocalWeather.toEpochMilli()  } : $combinedWeatherReport")
            onSuccess(combinedWeatherReport)
        }

        override fun onFailure(
                call: Call<OneCallWeatherItem>,
                t: Throwable
        ) {
            Log.d(TAG, "oOneCallCallback.onFailure : $t")
        }
    }

    private fun OneCallWeatherItem.toCurrent()
    : CombinedWeatherReport
    {
        return CombinedWeatherReport(
                latitude = this.latitude,
                longitude = this.longitude,
                timezone = this.timezone,
                timezoneOffset = this.timezoneOffset,
                dt = WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.dt ?: 0,
                    this.timezoneOffset ?: 0
                ),
                sunRise =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.sunRise ?: 0,
                    this.timezoneOffset ?: 0),
                sunSet =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                    this.currentWeather?.sunSet ?: 0,
                    this.timezoneOffset ?: 0),
                temp = this.currentWeather?.temp,
                feelsLike = this.currentWeather?.feelsLike,
                pressure = this.currentWeather?.pressure,
                humidity = this.currentWeather?.humidity,
                dewPoint = this.currentWeather?.dewPoint,
                uVIndex =  this.currentWeather?.uVIndex,
                cloudinessPercent = this.currentWeather?.cloudinessPercent,
                visibilityMeters = this.currentWeather?.visibilityMeters,
                windSpeed = this.currentWeather?.windSpeed,
                windGust = this.currentWeather?.windGust,
                degreeWindDirection = this.currentWeather?.degreeWindDirection,
                weather = AllWeatherSegment(
                    id = this.currentWeather?.weather?.firstOrNull()?.id,
                    mainTitle =  this.currentWeather?.weather?.firstOrNull()?.mainTitle,
                    description =  this.currentWeather?.weather?.firstOrNull()?.description,
                    icon =  this.currentWeather?.weather?.firstOrNull()?.icon,
                    condition = WeatherConditions.getConditionFromCode(this.currentWeather?.weather?.firstOrNull()?.id)
                ),
//                rainVolume = this.currentWeather?.rainVolume,
//                snowVolume = this.currentWeather?.snowVolume,
                hourly =  listOfHourlyReports(this, this.timezoneOffset ?: 0),
                daily = listOfDailyReports(this, this.timezoneOffset ?: 0)
        )
    }

    private fun listOfDailyReports(
        item : OneCallWeatherItem,
        offset: Int = 0
    ): List<Daily?> {
        val listOfDailyReports = mutableListOf<Daily?>()
        item.dailyWeather?.subList(1, daysReportedLimit+1)?.forEach {
            listOfDailyReports.add(
                Daily(
                    dt = WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                            it.dt ?: 0,
                            offset
                    ),
                    sunRise =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                            it.sunRise ?: 0,
                            offset
                    ),
                    sunSet =  WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                            it.sunSet ?: 0,
                            offset
                    ),

                    tempMax = it.temp?.max,
                    tempMin = it.temp?.min,
                    tempDay = it.temp?.day,
                    tempEvening = it.temp?.evening,
                    tempMorning = it.temp?.morning,
                    tempNight = it.temp?.night,

                    feelsLikeDay = it.feelsLike?.day,
                    feelsLikeEvening = it.feelsLike?.evening,
                    feelsLIkeMorning = it.feelsLike?.morning,
                    feelsLikeNight = it.feelsLike?.night,

                    pressure = it.pressure,
                    humidity = it.humidity,
                    dewPoint = it.dewPoint,
                    uVIndex = it.uVIndex,
                    cloudinessPercent = it.cloudinessPercent,
                    windGust = it.windGust,
                    windSpeed = it.windSpeed,
                    degreeWindDirection = it.degreeWindDirection,
                    weather = theWeatherSegment(it.weather?.first()),
                    probabilityOfRain = it.probabilityOfRain,
                )
            )
        }
        return listOfDailyReports
    }

    private fun listOfHourlyReports(
        item : OneCallWeatherItem?,
        offset: Int = 0
    ): List<Hourly?> {
        val listOfHourlyReports = mutableListOf<Hourly?>()
        item?.hourlyWeathers?.subList(1, hoursReportedLimit+1)?.forEach {
            listOfHourlyReports.add(
                Hourly(
                    dt = WeatherUnit.convertTimeFromEpochInSecondsToLocalDataTimeType(
                            it.dt ?: 0,
                            offset
                    ),
                    temp = it.temp,
                    feelsLike = it.feelsLike,
                    pressure = it.pressure,
                    humidity = it.humidity,
                    dewPoint = it.dewPoint,
                    uVIndex = it.uVIndex,
                    cloudinessPercent = it.cloudinessPercent,
                    visibilityMeters = it.visibilityMeters,
                    windGust = it.windGust,
                    windSpeed = it.windSpeed,
                    degreeWindDirection = it.degreeWindDirection,
//                    rainVolume = it.rainVolume,
//                    snowVolume = it.snowVolume,
                    weather = theWeatherSegment(it.weather?.first()),
                )
            )
        }
        return listOfHourlyReports
    }



    private fun theWeatherSegment(
            weatherSegment :NetworkingWeatherSegment?
    ) =  AllWeatherSegment(
            id = weatherSegment?.id,
            mainTitle = weatherSegment?.mainTitle,
            description = weatherSegment?.description,
            icon = weatherSegment?.icon,
            condition = WeatherConditions.getConditionFromCode(weatherSegment?.id)

    )


}