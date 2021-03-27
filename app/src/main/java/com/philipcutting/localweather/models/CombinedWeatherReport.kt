package com.philipcutting.localweather.models

data class CombinedWeatherReport(
        val currentWeatherReport: CurrentWeatherReport?,
        val dailyWeatherReports: DailyWeatherReports?,
        val hourlyWeatherReports: HourlyWeatherReports?,
        val timeLastPulled: Long = 0,

)
