package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.philipcutting.localweather.databinding.FragmentDailyBinding

class DailyFragment: Fragment (R.layout.fragment_daily){
    private val TAG = "HourlyFragment"
    private lateinit var fragmentDailyBinding: FragmentDailyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyBinding.bind(view)
        fragmentDailyBinding = binding
//        binding.DailyRecyclerView.adapter = HourlyListAdapter()
//        binding.hourlyRecyclerView.layoutManager =
//                LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = "Hourly Report"
    }

}