package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"

    //private val viewModel: CurrentWeatherViewModel by viewModels()

    private lateinit var viewModel : CurrentWeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)


//        var currentWeather: CurrentWeatherReport? = null
//        NetworkOneCallAll.getOneCallWeather { currentWeather ->
//            Log.d("WeatherFragment", "getting current weather: $currentWeather" )
//
//        }
//
//
//        val weatherCode = currentWeather?.weather?.id ?: -1
//        val currentWeatherCondition = WeatherConditions.getConditionFromCode(weatherCode)
//        val weatherIconInt = when {
//            (currentWeatherCondition == null) -> R.drawable.ic_error
//            else -> {
//                currentWeatherCondition.getImageResource(
//                    LocalDateTime.now(),
//                    currentWeather?.temp,
//                    currentWeather
//                )
//            }
//        }

//        binding.currentWeatherConditionTextView.text = currentWeather?.weather?.description ?: "Error"
//        binding.weatherCurrentIcon.setImageResource(weatherIconInt )

//        val currentWeatherObserver = Observer<CurrentWeatherReport> { current ->
//            binding.currentWeatherConditionTextView.text = current.weather?.description ?: "Loading Weather"
//        }
//
//        viewModel.currentWeatherReport.observe(viewLifecycleOwner,currentWeatherObserver)

        viewModel = ViewModelProvider(this)[CurrentWeatherViewModel::class.java]

        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
        }
        viewModel.getCurrentWeather()


    }

}