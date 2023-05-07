package com.example.hw13_weatherapp.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "weather_response_tablo")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 1,

    @SerializedName("current_weather")
    @Embedded
    val currentWeather: CurrentWeather?,

    @SerializedName("daily")
    @Embedded
    val daily: Daily?,

    var icons : List<Int>,
)