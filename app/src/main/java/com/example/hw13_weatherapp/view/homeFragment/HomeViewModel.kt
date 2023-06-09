package com.example.hw13_weatherapp.view.homeFragment


import android.icu.text.DateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw13_weatherapp.model.Daily
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.repo.WeatherAppRepository
import com.example.hw13_weatherapp.util.Consts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherAppRepository: WeatherAppRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData


    fun getCurrentLocationData(lat: Double, longt: Double) {
        initLogicData(lat, longt)
    }

    fun initLogicData(lat: Double = 38.4127, longt: Double = 27.1384) {
        viewModelScope.launch {
            weatherAppRepository.fetchWeatherData(lat, longt)
                .map { weatherResponse ->
                    // Iconlar hava durumuna göre dinamikleştirildi
                    setIcons(weatherResponse)

                    // Api dan gelen tarih daha düzenli bir tarih gösterimi ile değiştirildi.
                    setDates(weatherResponse.daily)

                    weatherResponse
                }.collect { weatherResponse ->
                    _weatherData.value = weatherResponse
                }

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

        weatherResponse.daily?.weathercode = icons

    }

    private fun setDates(daily: Daily?) {

        val dt = Date()
        val calendar = Calendar.getInstance()
        calendar.time = dt
        val format = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val formatTitle = DateFormat.getDateInstance(DateFormat.FULL)

        @Suppress("UNCHECKED_CAST")
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