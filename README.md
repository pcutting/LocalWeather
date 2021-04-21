#Local Weather


Some problems: 
    Updating is funky.  
    The refresh button doesn't take into account the 60 second delay needed by the api.
    Change in location services triggers an update in api.
    Some of the day vs night icon logic might be incorrect.
    No consideration of long time to pull data and location, can cause errors on some emulators/phones.
    Need to move location logic out of mainActivity to repository.
    Repository doesn't hold latest weather data, it's only in the viewModel.
    Didn't implement coloring for UI, such as day/night/sunset/sunrise...
    Just plain ugly colors and fonts and buttons.
    Actual location name not shown.
    
    














Weather Icons from : https://www.flaticon.com/authors/prosymbols


Requirements


You are going to build your very own Weather app using the OpenWeatherMap API.

The Free API: https://openweathermap.org/api


*Your app will grab your current location and automatically pull up today's weather
*You will show the appropriate icon to match the weather
*Show the title of the current weather, such as "Currently: Cloudy"
*Show today's date
*Have a button/tab that says "Today". If selected it shows 5 hours worth of weather (see screenshot)
*Have a button/tab that says "Weekly". If selected it shows 5 days worth of weather (see screenshot)