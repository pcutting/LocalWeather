 package com.philipcutting.localweather.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.philipcutting.localweather.databinding.DailyItemBinding
import com.philipcutting.localweather.models.Daily
import com.philipcutting.localweather.recyclerview.DailyListAdapter.DailyViewHolder
import java.math.RoundingMode
import java.time.LocalDateTime

 private const val TAG = "DailyListAdapter"

class DailyListAdapter : ListAdapter<Daily, DailyViewHolder> (diffUtil){
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Daily>() {
            override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem.dt == newItem.dt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DailyItemBinding.inflate(inflater, parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val daily = getItem(position)
        holder.bind(daily)
    }

    class DailyViewHolder(
        private val dailyBinding: DailyItemBinding
    ) : RecyclerView.ViewHolder(dailyBinding.root) {

        fun bind(day: Daily){
//            Log.d(TAG, "onBind: Day: $day")
            val timeSetting : LocalDateTime = day.dt ?: LocalDateTime.now()
            dailyBinding.apply {
                this.dateTextview.text = day.dt?.toLocalDate()?.dayOfWeek?.toString()?.subSequence(0,3)
                this.descriptionTextview.text = day.weather?.description
                this.tempTextview.text =
                    "${reduceDoubleSize(1,day.tempMin).toString()} .. ${reduceDoubleSize(1,day.tempMax).toString()}"
                this.weatherImage.setImageResource(
                    day.weather?.condition?.getImageResource(
                        timeSetting,
                        day.tempDay
                ) ?: 0)
            }
        }
        
        private fun reduceDoubleSize(precision: Int, number: Double?) : Double {
            return number?.toBigDecimal()?.setScale(precision, RoundingMode.HALF_UP)?.toDouble() ?: 0.0
        }

    }
}