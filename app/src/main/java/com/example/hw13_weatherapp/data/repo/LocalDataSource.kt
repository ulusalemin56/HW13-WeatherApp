package com.example.hw13_weatherapp.data.repo

import com.example.hw13_weatherapp.data.local.WeatherDao
import com.example.hw13_weatherapp.data.model.WeatherResponse
import java.lang.Exception


class LocalDataSource (
    private val weatherDao: WeatherDao
) {

    suspend fun insertWeatherToDataBase(weatherData: WeatherResponse) {

        try {
            weatherDao.deleteAll()
            weatherDao.insertAll(weatherData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    suspend fun getAllProperties(): WeatherResponse {
        return weatherDao.getAll()
    }

}