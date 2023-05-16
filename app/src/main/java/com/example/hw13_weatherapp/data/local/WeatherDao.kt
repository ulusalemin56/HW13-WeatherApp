package com.example.hw13_weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hw13_weatherapp.model.WeatherResponse

@Dao
interface WeatherDao {

   // Api dan gelen Weather verilerini tabloya eklemek için kullanılacak.
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(weatherResponse: WeatherResponse)

   // RoomDataBase inden tüm verileri çekmek için kullanılacak
   @Query("SELECT * FROM weather_response_tablo")
   suspend fun getAll() : WeatherResponse

   // RoomDataBase indeki tüm verileri silmek için kullanılacak
   @Query("DELETE FROM weather_response_tablo")
   suspend fun deleteAll()
}