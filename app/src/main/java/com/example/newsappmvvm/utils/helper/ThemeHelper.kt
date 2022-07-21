package com.example.newsappmvvm.utils.helper

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsappmvvm.utils.DARK
import com.example.newsappmvvm.utils.LIGHT

object ThemeHelper {
    fun applyTheme(uiMode: String) {

        return when (uiMode) {
            LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {throw Exception("The theme is unknown")}
        }
    }

}