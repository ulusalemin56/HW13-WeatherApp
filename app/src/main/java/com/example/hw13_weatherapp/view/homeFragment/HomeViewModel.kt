package com.example.hw13_weatherapp.view.homeFragment

import android.app.Application
import android.icu.text.DateFormat
import android.icu.util.Calendar
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hw13_weatherapp.util.Consts
import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.model.api.WeatherApiService
import com.example.hw13_weatherapp.model.data.Daily
import com.example.hw13_weatherapp.model.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val weatherApiService = WeatherApiService.create(app.applicationContext)

    private val weatherDb = WeatherDB.getInstance(app.applicationContext)

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData


    fun getDataService() {

        weatherApiService.getWeatherResult().enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {

                    val weatherResponse = response.body()

                    // Iconlar hava durumuna göre dinamikleştirildi
                    setIcons(weatherResponse)

                    // Api dan gelen tarih daha düzenli bir tarih gösterimi ile değiştirildi.
                    setDates(weatherResponse?.daily)

                    _weatherData.value = weatherResponse

                    Thread(Runnable {
                        weatherDb.weatherDao().deleteAll()
                        weatherResponse?.let { weatherResponse -> weatherDb.weatherDao().insertAll(weatherResponse) }
                    }).start()
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Thread(Runnable {
                    val weatherResponse = weatherDb.weatherDao().getAll()
//                    _weatherData.postValue(weatherResponse)
                 Handler(Looper.getMainLooper()).post(Runnable {
                        _weatherData.value = weatherResponse
                    })
                }).start()
            }
        })
    }

    fun setIcons(weatherResponse: WeatherResponse?) {
        val weatherCodes = weatherResponse?.daily?.weathercode
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

        weatherResponse?.icons = icons

    }

    fun setDates(daily: Daily?) {

        val dt = Date()
        val calendar = Calendar.getInstance()
        calendar.time=dt
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