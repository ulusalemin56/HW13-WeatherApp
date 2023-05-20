package com.example.hw13_weatherapp.repo

import android.icu.text.DateFormat
import android.icu.util.Calendar
import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.model.Daily
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.network.WeatherApiService
import com.example.hw13_weatherapp.util.Consts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.Date

class WeatherAppRepository(
    private val weatherApiService: WeatherApiService,
    weatherDB: WeatherDB,
    private val isInternetAvailable: Boolean
) {

    private val weatherDao = weatherDB.weatherDao()

    suspend fun fetchWeatherData() : WeatherResponse {

       return if (isInternetAvailable) {
            fetchFromService()
        } else {
            fetchFromDataBase()
        }

    }

    private suspend fun fetchFromService() : WeatherResponse {
       val weatherResponse = withContext(Dispatchers.IO) {
            val response = weatherApiService.getWeatherResult().body()

            weatherDao.deleteAll()
            response?.let {

                // Iconlar hava durumuna göre dinamikleştirildi
                setIcons(it)

                // Api dan gelen tarih daha düzenli bir tarih gösterimi ile değiştirildi.
                setDates(it.daily)


                insertWeatherToDataBase(it)
            }

           response
        }

        return weatherResponse ?: fetchFromDataBase()

        /*weatherApiService.getWeatherResult().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {

                    val responseBody = response.body()

                    responseBody?.let { weatherResponse->

                        // Iconlar hava durumuna göre dinamikleştirildi
                        setIcons(weatherResponse)

                        // Api dan gelen tarih daha düzenli bir tarih gösterimi ile değiştirildi.
                        setDates(weatherResponse.daily)

                        callBack(weatherResponse)

                        insertWeatherToDataBase(weatherResponse)
                    }

                } else {

                    fetchFromDataBase(callBack)

                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                fetchFromDataBase(callBack)

            }

        })
*/
    }

    private suspend fun fetchFromDataBase(): WeatherResponse {

        val weatherResponse = withContext(Dispatchers.IO) {
            weatherDao.getAll()
        }

        return weatherResponse
    }

    private suspend fun insertWeatherToDataBase(weatherData: WeatherResponse) {

        try {
            weatherDao.deleteAll()
            weatherDao.insertAll(weatherData)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setIcons(weatherResponse: WeatherResponse) {
        val weatherCodes = weatherResponse.daily?.weathercode
        val icons = ArrayList<Int>()

        weatherCodes?.forEach {
            when (it) {
                0 -> icons.add(Consts.GUNESLI)
                in 1..3 -> icons.add(Consts.PARCALI_BULUTLU)
                45, 48 -> icons.add(Consts.SISLI)
                51, 53, 55, 56, 57 -> icons.add(Consts.CISELEME_HAFIF)
                61, 63, 65, 66, 67 -> icons.add(Consts.NORMAL_YAGMUR)
                71, 73, 75, 77 -> icons.add(Consts.KAR)
                80, 81, 82 -> icons.add(Consts.SAGANAK_YAGMUR)
                85, 86 -> icons.add(Consts.YOGUN_KAR)
                95 -> icons.add(Consts.FIRTINA_YAGMUR)
                96, 99 -> icons.add(Consts.FIRTINA_DOlU)
            }
        }

        weatherResponse.icons = icons

    }

    fun setDates(daily: Daily?) {

        val dt = Date()
        val calendar = Calendar.getInstance()
        calendar.time = dt
        val format = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val formatTitle = DateFormat.getDateInstance(DateFormat.FULL)

        val dates = daily?.time as ArrayList<String>

        for (a in 0 until dates.size) {
            if (a == 0) {
                val currentData = formatTitle.format(calendar.time)
                dates[a] = currentData
                calendar.add(Calendar.DATE, 1)
            } else {
                val currentData = format.format(calendar.time)
                dates[a] = currentData
                calendar.add(Calendar.DATE, 1)
            }
        }

    }
}