package com.example.hw13_weatherapp.view.homeFragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.repo.WeatherAppRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val weatherAppRepository: WeatherAppRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData


    fun fetchData() {
        viewModelScope.launch {
            _weatherData.value = weatherAppRepository.fetchWeatherData()
        }

    }
}