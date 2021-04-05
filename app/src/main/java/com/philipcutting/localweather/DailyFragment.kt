package com.philipcutting.localweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.philipcutting.localweather.databinding.FragmentDailyBinding

class DailyFragment: Fragment (R.layout.fragment_daily){
    private val TAG = "DailyFragment"
    private var fragmentDailyBinding: FragmentDailyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDailyBinding.bind(view)
        fragmentDailyBinding = binding
//        binding.DailyRecyclerView.adapter = HourlyListAdapter()
//        binding.hourlyRecyclerView.layoutManager =
//                LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

//        val bar = (activity as AppCompatActivity).supportActionBar
//        bar?.title = "Daily"
    }

}