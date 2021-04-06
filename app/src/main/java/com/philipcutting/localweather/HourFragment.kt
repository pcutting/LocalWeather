package com.philipcutting.localweather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.FragmentHourBinding
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.recyclerview.HourlyListAdapter
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class HourFragment: Fragment(R.layout.fragment_hour) {

    private val TAG = "HourFragment.kt"
    //private var currentWeather :CombinedWeatherReport? = null
    //private lateinit var viewModel: CurrentWeatherViewModel
    private val viewModel: CurrentWeatherViewModel by activityViewModels()
    private val hourlyListAdapter = HourlyListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHourBinding.bind(view)

        binding.testTextview.text = "onViewCreated: Hour fragment"
        //viewModel = ViewModelProvider(this)[CurrentWeatherViewModel::class.java]
        hourlyListAdapter.submitList(viewModel.getHourly())

        Log.d(TAG, "onViewCreated: ${viewModel.getHourly()}")

        binding.hourListRecyclerview.adapter = HourlyListAdapter()
        binding.hourListRecyclerview.layoutManager =
            LinearLayoutManager(
                view.context,
                RecyclerView.HORIZONTAL,
                false)

        hourlyListAdapter.submitList(viewModel.currentWeatherReportLiveData.value?.hourly)

        Log.d(TAG, "onViewCreated: before viewmodel livedata.")


        viewModel.currentWeatherReportLiveData.observe(
            viewLifecycleOwner,
            Observer<CombinedWeatherReport?> { it ->

                hourlyListAdapter.submitList(it?.hourly)
                Log.d(TAG, "inside livedata observer: Hours(${it?.hourly?.size}): ${it?.hourly?.toString()}")

            })

    }



}