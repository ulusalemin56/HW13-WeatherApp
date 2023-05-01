package com.example.hw13_weatherapp.model.api


import android.os.Environment
import com.example.hw13_weatherapp.constants.Consts
import com.example.hw13_weatherapp.model.data.WeatherResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    fun getWeatherResult(
        @Query("latitude") latitude: Double = 40.7750,
        @Query("longitude") longitude: Double = 29.9480,
        @Query("current_weather") current_weather: Boolean = true,
        @Query("timezone") timezone: String = "auto",
        @Query("daily") daily: String = "weathercode,apparent_temperature_max,apparent_temperature_min",
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("forecast_days") forecastDays: Int = 14
    ): Call<WeatherResponse>

    companion object {

        fun create(): WeatherApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val cacheSize = (10 * 1024 * 1024).toLong()
            val myCache = Cache(Environment.getDownloadCacheDirectory(), cacheSize)

            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(MyCustomerInterCeptor())
                .cache(myCache)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(WeatherApiService::class.java)
        }


    }
}