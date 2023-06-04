package com.example.hw13_weatherapp.view.homeFragment

import android.content.Context
import com.example.hw13_weatherapp.data.local.WeatherDB
import com.example.hw13_weatherapp.data.local.WeatherDao
import com.example.hw13_weatherapp.network.WeatherApiService
import com.example.hw13_weatherapp.repo.LocalDataSource
import com.example.hw13_weatherapp.repo.RemoteDataSource
import com.example.hw13_weatherapp.repo.WeatherAppRepository
import com.example.hw13_weatherapp.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun provideWeatherApiService(retrofit: Retrofit) : WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    fun provideWeatherDao(weatherDB: WeatherDB) : WeatherDao {
        return weatherDB.weatherDao()
    }

    @Provides
    fun provideIsInternetAvailable(@ApplicationContext context: Context) : Boolean {
        return NetworkUtil.isInternetAvailable(context)
    }

    @Provides
    fun provideRemoteDataSource(weatherApiService: WeatherApiService) : RemoteDataSource {
        return RemoteDataSource(weatherApiService)
    }

    @Provides
    fun provideLocalDataSource(weatherDao: WeatherDao) : LocalDataSource {
        return LocalDataSource(weatherDao)
    }

    @Provides
    fun provideWeatherAppRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        isInternetAvailable : Boolean
    ) : WeatherAppRepository {
        return WeatherAppRepository(remoteDataSource, localDataSource, isInternetAvailable)
    }

}