package com.example.hw13_weatherapp.repo



import com.example.hw13_weatherapp.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherAppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val isInternetAvailable: Boolean
) {

    fun fetchWeatherData() : Flow<WeatherResponse> {
         return flow {
            if (isInternetAvailable) {

                val weatherResponse = remoteDataSource.getFromApiService()

                localDataSource.insertWeatherToDataBase(weatherResponse)

                emit(weatherResponse)
            } else {
                emit(localDataSource.getAllProperties())
            }
        }.flowOn(Dispatchers.IO)
    }
}

