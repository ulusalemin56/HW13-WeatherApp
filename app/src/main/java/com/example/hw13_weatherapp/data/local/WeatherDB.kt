package com.example.hw13_weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hw13_weatherapp.model.CurrentWeather
import com.example.hw13_weatherapp.model.Daily
import com.example.hw13_weatherapp.model.WeatherResponse
import com.example.hw13_weatherapp.util.DataBaseConverter

@Database(
    entities = [WeatherResponse::class, CurrentWeather::class, Daily::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataBaseConverter::class)
abstract class WeatherDB : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: WeatherDB? = null

        fun getInstance(context: Context): WeatherDB {
            return instance ?: synchronized(this) {
                val dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDB::class.java,
                    "weather_response_database"
                ).build()
                instance = dataBase
                dataBase
            }

        }


    }
}