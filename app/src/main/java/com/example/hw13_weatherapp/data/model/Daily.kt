package com.example.hw13_weatherapp.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "daily_tablo")
@Parcelize
data class Daily(
    @PrimaryKey
    val id3 : Int = 1,

    @SerializedName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double?>?,

    @SerializedName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double?>?,

    @SerializedName("time")
    val time: List<String?>?,

    @SerializedName("weathercode")
    var weathercode: List<Int?>?
) : Parcelable