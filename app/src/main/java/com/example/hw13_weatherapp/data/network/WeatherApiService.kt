package com.example.hw13_weatherapp.data.network


import com.example.hw13_weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getWeatherResult(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") current_weather: Boolean = true,
        @Query("timezone") timezone: String = "auto",
        @Query("daily") daily: String = "weathercode,apparent_temperature_max,apparent_temperature_min",
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("forecast_days") forecastDays: Int = 14
    ): WeatherResponse


}