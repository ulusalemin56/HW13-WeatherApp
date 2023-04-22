package com.example.hw13_weatherapp.view.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hw13_weatherapp.R
import com.example.hw13_weatherapp.model.data.WeatherResponse

class WeatherDataAdapter(private val weatherResponse : WeatherResponse) : Adapter<WeatherDataAdapter.WeatherDataViewHolder>() {

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
        val view = layoutInflater.inflate(R.layout.next_days_item, parent, false)


        return WeatherDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDataAdapter.WeatherDataViewHolder, position: Int) {
        
    }

    override fun getItemCount(): Int {
        return times?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class WeatherDataViewHolder(private val itemView : View) : ViewHolder(itemView) {


        fun bind() {


        }


    }
}