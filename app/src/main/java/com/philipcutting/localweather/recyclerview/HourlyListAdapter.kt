 package com.philipcutting.localweather.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.R
import com.philipcutting.localweather.models.Hourly
import com.philipcutting.localweather.recyclerview.HourlyListAdapter.HourlyViewHolder


private const val TAG = "HourlyListAdapter"

class HourlyListAdapter : ListAdapter<Hourly, HourlyViewHolder> (diffUtil){



    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Hourly>() {
            override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {

                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem.dt == newItem.dt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = HourlyItemBinding.inflate(inflater, parent, false)
//        return HourlyViewHolder(binding)
        val hourLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_item, parent, false)


        return HourlyViewHolder(hourLayout)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val hourly = getItem(position)
        holder.bind(hourly)
    }

    class HourlyViewHolder(
        private val containerView: View
    ) : RecyclerView.ViewHolder(containerView) {

        fun bind(hour: Hourly){
            Log.d(TAG, "onBind: Hour: $hour")

            containerView.apply {

//                this.tempTextview.text = hour?.temp.toString()
//                this.descriptionTextview.text = hour?.weather?.description

            }
        }
    }
}