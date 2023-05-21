package com.example.hw13_weatherapp.repo


import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.network.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class WeatherAppRepository(
    private val weatherApiService: WeatherApiService,
    weatherDB: WeatherDB,
    private val isInternetAvailable: Boolean
) {

    private val weatherDao = weatherDB.weatherDao()

    fun fetchWeatherData(): Flow<WeatherResponse> {

        return flow {
            if (isInternetAvailable) {
                emit(fetchFromService())
            } else {
                emit(fetchFromDataBase())
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchFromService(): WeatherResponse {

        val weatherResponse = weatherApiService.getWeatherResult()

        weatherDao.deleteAll()
        insertWeatherToDataBase(weatherResponse)

        return weatherResponse
    }

    private suspend fun fetchFromDataBase(): WeatherResponse {
        return weatherDao.getAll()
    }

    private suspend fun insertWeatherToDataBase(weatherData: WeatherResponse) {

        try {
            weatherDao.deleteAll()
            weatherDao.insertAll(weatherData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}