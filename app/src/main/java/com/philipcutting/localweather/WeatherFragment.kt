package com.philipcutting.localweather

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.models.WeatherConditions
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.showToast
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

/** TODO  add swipe to refresh. Reference bellow.
 *  https://www.geeksforgeeks.org/how-to-implement-swipe-down-to-
 *  refresh-in-android-using-android-studio/
*/

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"
    private val viewModel: CurrentWeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherBinding.bind(view)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_hourly_and_daily, HourFragment())
            .commit()
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
            refreshWeather(requireContext(),"onDragEvent ;(v, event)$v , $event")
            true
        }
        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){
            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"
            val currentCondition = WeatherConditions.getConditionFromCode(it?.weather?.id)
            binding.weatherCurrentIcon.setImageResource(currentCondition.getImageResource(it?.dt, it?.temp, it))
            binding.temperatureTextView.text = "${it?.temp?.toString()} ${WeatherRepository.getUnits(activity as Activity)}"
        }
        viewModel.getCurrentWeather()
        binding.fab.setOnClickListener {
            //TODO figure out this.
            if (viewModel.timeSinceLastUpdateInSeconds() <= 60) {
                activity?.showToast("Have to wait at least 60 seconds to update.  You have waited ${60-viewModel.timeSinceLastUpdateInSeconds()}")
            }
            refreshWeather(requireContext(),"Fab update")
        }
    }

    private fun refreshWeather(context: Context, event: String = "No event given"){
        Log.d(TAG, "Refresh requested: $event.")
        viewModel.getCurrentWeather()
    }



}