package com.example.hw13_weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.hw13_weatherapp.data.local.WeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideWeatherDB(@ApplicationContext context : Context) : WeatherDB {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDB::class.java,
            "weather_response_database"
        ).build()
    }

}