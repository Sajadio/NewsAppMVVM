package com.example.newsappmvvm.utils

import com.example.newsappmvvm.R
import java.util.*

const val API_KEY = "a913f00d95af4db78447629e95f8b8f3"
const val BASE_URL = "https://newsapi.org/"

const val STORAGE_NAME = "data_Storage"
const val  ARTICLE_TABLE = "article_table"
const val LOCAL_ARTICLE_TABLE = "local_article_table"
const val PAGE_KEY_TABLE = "page_key_table"
const val NAME_DATABASE = "app_db"
const val ITEMS_PER_PAGE = 20
const val  PREF_DISTANCE = 2
const val DEFAULT_PAGE_INDEX = 1

const val LIGHT = "light"
const val DARK = "dark"
const val THEME_APP = "selectTheme"

val listOfCategories = listOf(
    R.string.general,
    R.string.business,
    R.string.entertainment,
    R.string.health,
    R.string.science,
    R.string.sports,
    R.string.technology,
)

fun language() = if (Locale.getDefault().displayLanguage == "English") "en" else "ar"
fun getCountry() = (Locale.getDefault().country).lowercase(Locale.getDefault())