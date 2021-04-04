package com.philipcutting.localweather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.models.WeatherConditions
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"

    private lateinit var viewModel : CurrentWeatherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)

        viewModel = ViewModelProvider(this)[CurrentWeatherViewModel::class.java]

        Log.d(TAG, "onViewCreated in WeatherFragment viewModel $viewModel")
        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            binding.temperatureTextView.text = it?.temp?.toString() ?: "Loading"
            binding.dateTextView.text = "Updated: " +  it?.dt?.toLocalTime()

            val currentCondition = WeatherConditions.getConditionFromCode(it?.weather?.id)
            binding.weatherCurrentIcon.setImageResource(currentCondition.getImageResource(it?.dt, it?.temp, it))
        }
        viewModel.getCurrentWeather()

        binding.refreshButton.setOnClickListener {


        }

        binding.hourlyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_hourly_and_daily, HourlyFragment())
                    .commit()
        }

        binding.dailyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_hourly_and_daily, DailyFragment())

        }


    }

}