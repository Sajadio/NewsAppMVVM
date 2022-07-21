package com.example.newsappmvvm.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsappmvvm.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStorageImp @Inject constructor(context: Context) : DataStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORAGE_NAME)
    private val dataStore = context.dataStore

    //keys
    private companion object {
        val SELECTED_THEME = stringPreferencesKey(THEME_APP)
    }


    // get the value according to condition
    override val uiModeFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            when (preference[SELECTED_THEME]) {
                LIGHT -> LIGHT
                DARK -> DARK
                else -> ""
            }
        }

    // update value in storage
    override suspend fun setUIMode(uiMode: UiMode) {
        dataStore.edit { preferences ->
            preferences[SELECTED_THEME] = when (uiMode) {
                UiMode.LIGHT -> LIGHT
                UiMode.DARK -> DARK
            }.toString()
        }
    }
}