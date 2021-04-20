package com.philipcutting.localweather

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.philipcutting.localweather.databinding.FragmentWeatherBinding
import com.philipcutting.localweather.models.WeatherConditions
import com.philipcutting.localweather.repositories.WeatherRepository
import com.philipcutting.localweather.utilities.toScaleWithFormat
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

/** TODO  add swipe to refresh. Reference bellow.
 *  https://www.geeksforgeeks.org/how-to-implement-swipe-down-to-
 *  refresh-in-android-using-android-studio/
*/

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private val TAG = "WeatherFragment"
    private val viewModel: CurrentWeatherViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
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
            refreshWeather("onDragEvent ;(v, event)$v , $event")
            true
        }

//        Log.d(TAG, "onViewCreated in WeatherFragment viewModel $viewModel")
        viewModel.currentWeatherReportLiveData.observe(viewLifecycleOwner){

            binding.updatedTextView.text = "Updated: " +
                    it?.dt?.toLocalDate()?.toString() + " @ " +
                    it?.dt?.toLocalTime()?.toString() +
                    "\nLocation :${it?.latitude?.toScaleWithFormat(4)}, " +
                    "${it?.longitude?.toScaleWithFormat(4)}"

            binding.currentWeatherConditionTextView.text = it?.weather?.description ?: "Loading Weather"

            val currentCondition = WeatherConditions.getConditionFromCode(it?.weather?.id)
            binding.weatherCurrentIcon.setImageResource(currentCondition.getImageResource(it?.dt, it?.temp, it))

            binding.temperatureTextView.text = "${it?.temp?.toString()} ${WeatherRepository.getUnits(activity as Activity)}"
        }
        viewModel.getCurrentWeather()

        binding.fab.setOnClickListener {
            refreshWeather("Fab update")
        }
    }

    private fun refreshWeather(event: String = "No event given"){
        //TODO Refresh
//        Log.d(TAG, "Refresh requested: $event.")
        viewModel.getCurrentWeather()
    }



}