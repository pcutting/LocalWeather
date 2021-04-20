 package com.philipcutting.localweather.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.HourlyItemBinding
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
        val inflater = LayoutInflater.from(parent.context)
        val binding = HourlyItemBinding.inflate(inflater, parent, false)
//        return HourlyViewHolder(binding)
//        val hourLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.hourly_item, parent, false)

        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val hourly = getItem(position)
        holder.bind(hourly)
    }

    class HourlyViewHolder(
        private val hourlyBinding: HourlyItemBinding
    ) : RecyclerView.ViewHolder(hourlyBinding.root) {

        fun bind(hour: Hourly){
//            Log.d(TAG, "onBind: Hour: $hour")

            hourlyBinding.apply {

                this.descriptionTextview.text = hour.weather?.description
                this.tempTextview.text = hour.temp.toString()
                this.weatherImage.setImageResource(
                    hour.weather?.condition?.getImageResource(
                        hour.dt,
                        hour.temp
                ) ?: 0)
                this.timeTextview.text = hour.dt?.toLocalTime()?.toString()
            }
        }
    }
}