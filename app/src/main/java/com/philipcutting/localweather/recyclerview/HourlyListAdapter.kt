 package com.philipcutting.localweather.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.HourlyItemBinding
import com.philipcutting.localweather.models.Hourly
import com.philipcutting.localweather.recyclerview.HourlyListAdapter.HourlyViewHolder

class HourlyListAdapter : ListAdapter<Hourly?, HourlyViewHolder> (diffUtil){

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Hourly?>() {
            override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean = true

            override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HourlyItemBinding.inflate(inflater, parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val hourly = getItem(position)
        holder.onBind(hourly)
    }

    class HourlyViewHolder (
        private val binding: HourlyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(hour: Hourly?){
            binding.apply {
                this.tempTextview.text = hour?.temp.toString()
                this.descriptionTextview.text = hour?.weather?.description

            }
        }
    }
}