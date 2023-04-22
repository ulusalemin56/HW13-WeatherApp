package com.example.hw13_weatherapp.constants

object Consts{

    // Base Url tanımlandı
    const val BASE_URL = "https://api.open-meteo.com/"

    // ViewType layoutları için sabit değişkenler tanımlandı
    const val VIEW_TYPE_CURRENT_DAY = 0
    const val VIEW_TYPE_NEXT_DAYS = 1

}

fun String.addCelcius(): String {
    return "$this °C"
}

fun String.addSpeedText(): String{
    return "$this km/hr"
}

