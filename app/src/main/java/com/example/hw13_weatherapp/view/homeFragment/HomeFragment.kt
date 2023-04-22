package com.example.hw13_weatherapp.view.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw13_weatherapp.R
import com.example.hw13_weatherapp.databinding.FragmentHomeBinding
import com.example.hw13_weatherapp.model.api.WeatherApiService
import com.example.hw13_weatherapp.model.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        val weatherApiService = WeatherApiService.create()

        weatherApiService.getWeatherResult().enqueue(object : Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val adapter = WeatherDataAdapter(response.body()!!)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        return binding.root
    }

}