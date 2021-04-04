package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.FragmentHourlyBinding
import com.philipcutting.localweather.recyclerview.HourlyListAdapter

class HourlyFragment: Fragment (R.layout.fragment_daily){
    private val TAG = "HourlyFragment"
    private var fragmentHourlyBinding:FragmentHourlyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHourlyBinding.bind(view)
        fragmentHourlyBinding = binding
        binding.hourlyListHorizontalRecyclerview.adapter = HourlyListAdapter()
        binding.hourlyListHorizontalRecyclerview.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = "Hourly Report"
    }
}