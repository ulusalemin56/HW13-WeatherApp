package com.example.hw13_weatherapp.constants

import com.example.hw13_weatherapp.R

object Consts{

    // Base Url tanımlandı
    const val BASE_URL = "https://api.open-meteo.com/"

    // ViewType layoutları için sabit değişkenler tanımlandı
    const val VIEW_TYPE_CURRENT_DAY = 0
    const val VIEW_TYPE_NEXT_DAYS = 1

    // Icons sabitleri oluşturuldu
    const val GUNESLI = R.drawable.ic_gunesli
    const val PARCALI_BULUTLU = R.drawable.ic_parcali_bulutlu
    const val SISLI = R.drawable.ic_sisli
    const val CISELEME_HAFIF = R.drawable.ic_ciseleme_hafif
    const val NORMAL_YAGMUR = R.drawable.ic_normal_yagmur
    const val KAR = R.drawable.ic_kar
    const val SAGANAK_YAGMUR = R.drawable.ic_saganak_yagmur
    const val YOGUN_KAR = R.drawable.ic_yogun_kar
    const val FIRTINA_YAGMUR = R.drawable.ic_firtinali_yagmur
    const val FIRTINA_DOlU = R.drawable.ic_dolu_firtina
}

fun String.addCelcius(): String {
    return "$this °C"
}

fun String.addSpeedText(): String{
    return "$this km/hr"
}

