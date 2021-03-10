package com.philipcutting.localweather.networking

/*
https://openweathermap.org/api/one-call-api

Current weather
Minute forecast for 1 hour
Hourly forecast for 48 hours
Daily forecast for 7 days
National weather alerts
Historical weather data for the previous 5 days

https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}


Parameters
lat, lon	required	Geographical coordinates (latitude, longitude)
appid	required	Your unique API key (you can always find it on your account page under the "API key" tab)
exclude	optional	By using this parameter you can exclude some parts of the weather data from the API response. It should be a comma-delimited list (without spaces).
Available values:
        current
        minutely
        hourly
        daily
        alerts

units	optional	Units of measurement. standard, metric and imperial units are available. If you do not use the units parameter, standard units will be applied by default. Learn more
lang	optional	You can use the lang parameter to get the output in your language. Learn more

https://api.openweathermap.org/data/2.5/onecall?lat=42.694492&lon=23.321964&exclude=minutely,alerts&units=imperial&appid=56786491fcb4331ffe593f9ff0b28cd1
{
  "lat": 42.6945,
  "lon": 23.322,
  "timezone": "Europe/Sofia",
  "timezone_offset": 7200,
  "current": {
    "dt": 1615354002,
    "sunrise": 1615351652,
    "sunset": 1615393599,
    "temp": 37.24,
    "feels_like": 24.21,
    "pressure": 1011,
    "humidity": 87,
    "dew_point": 33.73,
    "uvi": 0,
    "clouds": 90,
    "visibility": 10000,
    "wind_speed": 17.27,
    "wind_deg": 110,
    "weather": [
      {
        "id": 804,
        "main": "Clouds",
        "description": "overcast clouds",
        "icon": "04d"
      }
    ]
  },
  "daily": [
    {
      "dt": 1615370400,
      "sunrise": 1615351652,
      "sunset": 1615393599,
      "temp": {
        "day": 37.27,
        "min": 29.93,
        "max": 38.01,
        "night": 29.93,
        "eve": 33.66,
        "morn": 38.01
      },
      "feels_like": {
        "day": 31.08,
        "night": 23.92,
        "eve": 27.14,
        "morn": 31.24
      },
      "pressure": 1014,
      "humidity": 90,
      "dew_point": 34.83,
      "wind_speed": 5.37,
      "wind_deg": 70,
      "weather": [
        {
          "id": 616,
          "main": "Snow",
          "description": "rain and snow",
          "icon": "13d"
        }
      ],
      "clouds": 100,
      "pop": 1,
      "rain": 14.27,
      "snow": 12.71,
      "uvi": 1.3
    },
    {
      "dt": 1615456800,
      "sunrise": 1615437949,
      "sunset": 1615480070,
      "temp": {
        "day": 33.4,
        "min": 25.66,
        "max": 34.97,
        "night": 27.91,
        "eve": 32.31,
        "morn": 26.53
      },
      "feels_like": {
        "day": 27.75,
        "night": 22.96,
        "eve": 26.94,
        "morn": 20.1
      },
      "pressure": 1024,
      "humidity": 92,
      "dew_point": 29.43,
      "wind_speed": 3.53,
      "wind_deg": 313,
      "weather": [
        {
          "id": 601,
          "main": "Snow",
          "description": "snow",
          "icon": "13d"
        }
      ],
      "clouds": 76,
      "pop": 1,
      "snow": 1.59,
      "uvi": 4.55
    },
    {
      "dt": 1615543200,
      "sunrise": 1615524245,
      "sunset": 1615566541,
      "temp": {
        "day": 39.45,
        "min": 28.04,
        "max": 43.43,
        "night": 35.78,
        "eve": 38.98,
        "morn": 29.28
      },
      "feels_like": {
        "day": 34.14,
        "night": 31.62,
        "eve": 34.38,
        "morn": 24.17
      },
      "pressure": 1022,
      "humidity": 73,
      "dew_point": 31.03,
      "wind_speed": 2.95,
      "wind_deg": 269,
      "weather": [
        {
          "id": 804,
          "main": "Clouds",
          "description": "overcast clouds",
          "icon": "04d"
        }
      ],
      "clouds": 92,
      "pop": 0,
      "uvi": 5.51
    },
    {
      "dt": 1615629600,
      "sunrise": 1615610540,
      "sunset": 1615653011,
      "temp": {
        "day": 43.3,
        "min": 35.15,
        "max": 49.86,
        "night": 39.25,
        "eve": 47.37,
        "morn": 35.15
      },
      "feels_like": {
        "day": 39.09,
        "night": 35.83,
        "eve": 41.9,
        "morn": 31.06
      },
      "pressure": 1017,
      "humidity": 68,
      "dew_point": 33.6,
      "wind_speed": 1.52,
      "wind_deg": 235,
      "weather": [
        {
          "id": 804,
          "main": "Clouds",
          "description": "overcast clouds",
          "icon": "04d"
        }
      ],
      "clouds": 100,
      "pop": 0.06,
      "uvi": 4.02
    },
    {
      "dt": 1615716000,
      "sunrise": 1615696836,
      "sunset": 1615739481,
      "temp": {
        "day": 48.54,
        "min": 38.07,
        "max": 54.32,
        "night": 42.96,
        "eve": 51.37,
        "morn": 38.07
      },
      "feels_like": {
        "day": 44.33,
        "night": 38.3,
        "eve": 45.88,
        "morn": 34.3
      },
      "pressure": 1015,
      "humidity": 65,
      "dew_point": 37.47,
      "wind_speed": 2.64,
      "wind_deg": 124,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "clouds": 100,
      "pop": 0.34,
      "rain": 0.42,
      "uvi": 3.25
    },
    {
      "dt": 1615802400,
      "sunrise": 1615783130,
      "sunset": 1615825951,
      "temp": {
        "day": 47.14,
        "min": 40.8,
        "max": 47.14,
        "night": 40.8,
        "eve": 45.84,
        "morn": 41.02
      },
      "feels_like": {
        "day": 43.47,
        "night": 35.04,
        "eve": 41.63,
        "morn": 36.27
      },
      "pressure": 1003,
      "humidity": 71,
      "dew_point": 38.35,
      "wind_speed": 1.97,
      "wind_deg": 152,
      "weather": [
        {
          "id": 501,
          "main": "Rain",
          "description": "moderate rain",
          "icon": "10d"
        }
      ],
      "clouds": 100,
      "pop": 1,
      "rain": 12.04,
      "uvi": 4
    },
    {
      "dt": 1615888800,
      "sunrise": 1615869425,
      "sunset": 1615912421,
      "temp": {
        "day": 40.41,
        "min": 37.33,
        "max": 40.41,
        "night": 37.33,
        "eve": 38.37,
        "morn": 40.01
      },
      "feels_like": {
        "day": 35.38,
        "night": 32.13,
        "eve": 32.45,
        "morn": 33.78
      },
      "pressure": 1007,
      "humidity": 88,
      "dew_point": 37.22,
      "wind_speed": 4.05,
      "wind_deg": 328,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "clouds": 100,
      "pop": 1,
      "rain": 6.83,
      "uvi": 4
    },
    {
      "dt": 1615975200,
      "sunrise": 1615955720,
      "sunset": 1615998890,
      "temp": {
        "day": 36.21,
        "min": 35.46,
        "max": 36.77,
        "night": 35.46,
        "eve": 35.64,
        "morn": 36.66
      },
      "feels_like": {
        "day": 32.04,
        "night": 30.51,
        "eve": 30.31,
        "morn": 31.5
      },
      "pressure": 1014,
      "humidity": 90,
      "dew_point": 33.8,
      "wind_speed": 1.48,
      "wind_deg": 344,
      "weather": [
        {
          "id": 616,
          "main": "Snow",
          "description": "rain and snow",
          "icon": "13d"
        }
      ],
      "clouds": 100,
      "pop": 1,
      "rain": 2.72,
      "snow": 14.38,
      "uvi": 4
    }
  ]
}


 */


object NetworkOneCallCurrentWeatherAndForcast {



}