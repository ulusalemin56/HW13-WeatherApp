package com.example.hw13_weatherapp.repo

import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.network.WeatherApiService

class RemoteDataSource (
    private val weatherApiService: WeatherApiService
) {

    suspend fun getFromApiService() : WeatherResponse {
        return weatherApiService.getWeatherResult()
    }

}