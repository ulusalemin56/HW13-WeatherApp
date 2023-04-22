package com.example.hw13_weatherapp.view.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hw13_weatherapp.R
import com.example.hw13_weatherapp.constants.Consts
import com.example.hw13_weatherapp.constants.addCelcius
import com.example.hw13_weatherapp.constants.addSpeedText
import com.example.hw13_weatherapp.databinding.CurrentDayItemBinding
import com.example.hw13_weatherapp.databinding.NextDaysItemBinding
import com.example.hw13_weatherapp.model.data.WeatherResponse

class WeatherDataAdapter(private val weatherResponse: WeatherResponse) :
    Adapter<WeatherDataAdapter.WeatherDataViewHolder>() {

    private val currentWeather = weatherResponse.currentWeather
    private val times = weatherResponse.daily?.time
    private val weatherCodes = weatherResponse.daily?.weathercode
    private val maxTemps = weatherResponse.daily?.apparentTemperatureMax
    private val minTemps = weatherResponse.daily?.apparentTemperatureMin

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherDataAdapter.WeatherDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            Consts.VIEW_TYPE_CURRENT_DAY -> layoutInflater.inflate(
                R.layout.current_day_item,
                parent,
                false
            )
            else -> layoutInflater.inflate(
                R.layout.next_days_item, parent,
                false
            )
        }
        return WeatherDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDataAdapter.WeatherDataViewHolder, position: Int) {
        holder.bind(
            time = times?.get(position),
            weatherCode = weatherCodes?.get(position),
            maxTemp = maxTemps?.get(position),
            minTemp = minTemps?.get(position)
        )
    }

    override fun getItemCount(): Int {
        return times?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 0
        } else
            return position
    }


    inner class WeatherDataViewHolder(itemView: View) : ViewHolder(itemView) {



        fun bind(
            time: String?,
            weatherCode: Int?,
            maxTemp: Double?,
            minTemp: Double?
        ) {

            if (adapterPosition == Consts.VIEW_TYPE_CURRENT_DAY) {
                val binding = CurrentDayItemBinding.bind(itemView)

                binding.apply {
                    tvTemperature.text = currentWeather?.temperature?.toString()?.addCelcius()
                    tvWindSpeed.text = currentWeather?.windspeed?.toString()?.addSpeedText()
                    tvWindDirection.text = currentWeather?.winddirection?.toString()
                }

            } else {
                val binding = NextDaysItemBinding.bind(itemView)

                binding.apply {
                    tvDate.text = time ?: "Merhaba"
                    tvMinTemp.text = minTemp.toString().addCelcius()
                    tvMaxTemp.text = maxTemp.toString().addCelcius()
                }

            }


        }


    }
}