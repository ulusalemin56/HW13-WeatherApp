package com.example.hw13_weatherapp.di

import android.content.Context
import com.example.hw13_weatherapp.network.MyCustomerInterCeptor
import com.example.hw13_weatherapp.util.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHTTPClient(@ApplicationContext context : Context) : OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .cache(myCache)
            .addInterceptor(MyCustomerInterCeptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHTTPClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHTTPClient)
            .build()
    }

}