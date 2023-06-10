package com.example.hw13_weatherapp.data.repo

import com.example.hw13_weatherapp.data.model.WeatherResponse
import com.example.hw13_weatherapp.data.network.WeatherApiService

class RemoteDataSource (
    private val weatherApiService: WeatherApiService
) {

    suspend fun getFromApiService(lat: Double, longt : Double) : WeatherResponse {
        return weatherApiService.getWeatherResult(latitude = lat, longitude = longt)
    }

}