package com.example.hw13_weatherapp.util

import com.example.hw13_weatherapp.R

object Consts {

    // Base Url tanımlandı
    const val BASE_URL = "https://api.open-meteo.com/"

    // ViewType layoutları için sabit değişkenler tanımlandı
    const val VIEW_TYPE_CURRENT_DAY = 0
    const val VIEW_TYPE_NEXT_DAYS = 1

    // Icons sabitleri oluşturuldu
    val GUNESLI = R.drawable.ic_gunesli
    val PARCALI_BULUTLU = R.drawable.ic_parcali_bulutlu
    val SISLI = R.drawable.ic_sisli
    val CISELEME_HAFIF = R.drawable.ic_ciseleme_hafif
    val NORMAL_YAGMUR = R.drawable.ic_normal_yagmur
    val KAR = R.drawable.ic_kar
    val SAGANAK_YAGMUR = R.drawable.ic_saganak_yagmur
    val YOGUN_KAR = R.drawable.ic_yogun_kar
    val FIRTINA_YAGMUR = R.drawable.ic_firtinali_yagmur
    val FIRTINA_DOlU = R.drawable.ic_dolu_firtina

    const val CHANNEL_ID = "CHANNEL_ID"
}

fun String.addCelcius(): String {
    return "$this °C"
}

fun String.addSpeedText(): String {
    return "$this km/hr"
}

