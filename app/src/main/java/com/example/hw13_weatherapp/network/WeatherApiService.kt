package com.example.hw13_weatherapp.network


import android.content.Context
import com.example.hw13_weatherapp.util.Consts
import com.example.hw13_weatherapp.model.WeatherResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getWeatherResult(
        @Query("latitude") latitude: Double = 40.7750,
        @Query("longitude") longitude: Double = 29.9480,
        @Query("current_weather") current_weather: Boolean = true,
        @Query("timezone") timezone: String = "auto",
        @Query("daily") daily: String = "weathercode,apparent_temperature_max,apparent_temperature_min",
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("forecast_days") forecastDays: Int = 14
    ): WeatherResponse

    companion object {

        @Volatile
        private var instance : WeatherApiService? = null
        fun create(context: Context): WeatherApiService {
            return instance ?: synchronized(this) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Consts.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(createOkHttpClient(context))
                    .build().create(WeatherApiService::class.java)
                instance = retrofit
                retrofit
            }
        }

        private fun createOkHttpClient(context: Context): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)

            return OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cache(myCache)
                .addInterceptor(MyCustomerInterCeptor())
                .build()
        }

    }
}