package com.example.newsappmvvm.utils

import com.example.newsappmvvm.R
import java.util.*

const val API_KEY = "6953a7421bb749c589e687746c85eee8"
const val BASE_URL = "https://newsapi.org/"

val categories = mutableListOf(
   R.string.general,
   R.string.business,
   R.string.entertainment,
   R.string.health,
   R.string.science,
   R.string.sports,
   R.string.technology,
)

val randomColor = intArrayOf(
    R.color.green,
    R.color.yellow,
    R.color.pink,
    R.color.orang,
    R.color.blue,
    R.color.sky,
    R.color.blue
)

val categoryImg = intArrayOf(
    R.drawable.general,
    R.drawable.business,
    R.drawable.entertainment,
    R.drawable.healthcare,
    R.drawable.atom,
    R.drawable.sports,
    R.drawable.tech
)

fun getCountry() = (Locale.getDefault().country).toLowerCase()
