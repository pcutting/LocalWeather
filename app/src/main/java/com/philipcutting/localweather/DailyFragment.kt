package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.FragmentDailyBinding
import com.philipcutting.localweather.models.CombinedWeatherReport
import com.philipcutting.localweather.recyclerview.DailyListAdapter
import com.philipcutting.localweather.viewmodels.CurrentWeatherViewModel

class DailyFragment: Fragment (R.layout.fragment_daily){
    private val TAG = "DailyFragment"
    private val viewModel: CurrentWeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyBinding.bind(view)
        val adapter = DailyListAdapter()
        binding.dailyRecyclerView.adapter = adapter
        binding.dailyRecyclerView.layoutManager =
            LinearLayoutManager(
                view.context,
                RecyclerView.HORIZONTAL,
                false
            )

        viewModel.currentWeatherReportLiveData.observe(
            viewLifecycleOwner,
            Observer<CombinedWeatherReport?> { it ->
//                it?.daily?.let { binding.dailyRecyclerView.adapter::submitList }
                adapter.submitList(it?.daily)
//                (binding.dailyRecyclerView.adapter as DailyListAdapter).
            }
        )


    }

}