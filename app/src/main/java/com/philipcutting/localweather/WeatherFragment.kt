package com.philipcutting.localweather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.models.WeatherConditions
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel
import com.philipcutting.localweather.viewmodels.LocationViewModel

//TODO  add swipe to refresh. Reference bellow.
// https://www.geeksforgeeks.org/how-to-implement-swipe-down-to-
// refresh-in-android-using-android-studio/

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"

    //private lateinit var viewModel : CurrentWeatherViewModel
    private val viewModel: CurrentWeatherViewModel by activityViewModels()
    //private lateinit var locationViewModel : LocationViewModel
    private val locationViewModel: LocationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)

        //locationViewModel = ViewModelProviders.of(this)
        // .get(LocationViewModel::class.java)


        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_hourly_and_daily, HourFragment())
            .commit()

        binding.refreshButton.setOnClickListener {
            refreshWeather()

        }

        binding.hourlyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_hourly_and_daily, HourFragment())
                .commit()
        }

        binding.dailyButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_hourly_and_daily, DailyFragment())
                .commit()
        }

        binding.weatherConstrainLayoutParent.setOnDragListener { v, event ->
            refreshWeather("onDragEvent ;(v, event)$v , $event")
            true
        }

        Log.d(TAG, "onViewCreated in WeatherFragment viewModel $viewModel")
        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            binding.temperatureTextView.text = it?.temp?.toString() ?: "Loading"
//            binding.dateTextView.text = "Updated: " +  it?.dt?.toLocalTime()

            val currentCondition = WeatherConditions.getConditionFromCode(it?.weather?.id)
            binding.weatherCurrentIcon.setImageResource(currentCondition.getImageResource(it?.dt, it?.temp, it))
            //Log.d(TAG, "Weather: ${it.toString()}")
            //Log.d(TAG, "Hour: ${it?.hourly.toString()}")
        }
        viewModel.getCurrentWeather()

        locationViewModel.getLocationData().observe(viewLifecycleOwner, Observer {
            binding.locationTextview.text =
                "${it.latitude}, ${it.longitude} at ${it.timeRecorded.toLocalTime()}"
        })
    }

    private fun refreshWeather(event: String = "No event given"){
        //TODO Refresh
        Log.d(TAG, "Refresh requested: $event.")
        viewModel.getCurrentWeather()
    }

}