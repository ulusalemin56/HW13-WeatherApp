package com.example.hw13_weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hw13_weatherapp.data.model.CurrentWeather
import com.example.hw13_weatherapp.data.model.Daily
import com.example.hw13_weatherapp.data.model.WeatherResponse
import com.example.hw13_weatherapp.util.DataBaseConverter

@Database(
    entities = [WeatherResponse::class, CurrentWeather::class, Daily::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataBaseConverter::class)
abstract class WeatherDB : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}