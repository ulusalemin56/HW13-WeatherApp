package com.example.hw13_weatherapp.view.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw13_weatherapp.model.api.WeatherApiService
import com.example.hw13_weatherapp.model.data.WeatherResponse

class HomeViewModel : ViewModel() {

    private val weatherApiService = WeatherApiService.create()

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData : LiveData<WeatherResponse> = _weatherData



}