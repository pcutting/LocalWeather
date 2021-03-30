package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"

    private lateinit var viewModel : CurrentWeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)

        viewModel = ViewModelProvider(this)[CurrentWeatherViewModel::class.java]

        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            binding.temperatureTextView.text = it?.temp?.toString() ?: "Loading"
            binding.dateTextView.text = "Updated: " +  it?.dt?.toLocalTime() ?: "Loading"
        }
        viewModel.getCurrentWeather()

        //TODO testing code: Remove following section.

        binding.refreshButton.text = "Test Livedata String in frag"
        binding.refreshButton.setOnClickListener {
            viewModel.incrementTestString()
//            viewModel.getCurrentWeather()
        }
        binding.refreshButton2.text = "Test LiveData C.Weather"
        binding.refreshButton2.setOnClickListener {
            viewModel.getCurrentWeather()
        }
        viewModel.testStringLiveData.observe(viewLifecycleOwner){
            binding.tempLiveDataString.text = it
        }

        //TODO End testing code:

    }

}