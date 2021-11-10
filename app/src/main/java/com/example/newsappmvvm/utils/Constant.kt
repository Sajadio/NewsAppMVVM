package com.example.newsappmvvm.utils

import android.icu.text.SimpleDateFormat
import com.example.newsappmvvm.R
import java.util.*
import org.ocpsoft.prettytime.PrettyTime

const val API_KEY = "712f8b1496134646a52f5282da844fb6"//6953a7421bb749c589e687746c85eee8
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

fun dateToTimeFormat(time: String): String {
    val prettyTime = PrettyTime(Locale(getCountry()))
    var isTime: String? = null
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val date: Date = sdf.parse(time)
    isTime = prettyTime.format(date)
    return isTime
}

fun dateFormat(time: String?): String? {
    val newDate: String?
    val dateFormat = SimpleDateFormat("E, d MMM yyyy", Locale(getCountry()))
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(time)
    newDate = dateFormat.format(date)
    return newDate
}

fun getCountry() = (Locale.getDefault().country).toLowerCase()

fun times(value:String) = value
