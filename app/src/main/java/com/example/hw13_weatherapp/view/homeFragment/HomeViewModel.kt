package com.example.hw13_weatherapp.view.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw13_weatherapp.model.api.WeatherApiService
import com.example.hw13_weatherapp.model.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val weatherApiService = WeatherApiService.create()

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData : LiveData<WeatherResponse> = _weatherData

    fun getDataService(){

        weatherApiService.getWeatherResult().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful){
                    _weatherData.value = response.body()
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

            }

        })


    }

}