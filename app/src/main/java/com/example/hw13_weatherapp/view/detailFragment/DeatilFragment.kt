package com.example.hw13_weatherapp.view.detailFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hw13_weatherapp.databinding.FragmentDeatilBinding
import com.example.hw13_weatherapp.util.addCelcius


class DeatilFragment : Fragment() {

    private lateinit var binding : FragmentDeatilBinding

    private val args : DeatilFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeatilBinding.inflate(inflater)

        val weatherData = args.weatherResponse
        val position = args.position

        binding.apply {
            tvTitle.text = weatherData.daily?.time?.get(position)
            weatherData.icons.let {
                ivWeatherIcon.setImageResource(it[position])
            }

            tvTemperature.text = weatherData.daily?.apparentTemperatureMax?.get(position).toString().addCelcius()
        }

        return binding.root
    }

}