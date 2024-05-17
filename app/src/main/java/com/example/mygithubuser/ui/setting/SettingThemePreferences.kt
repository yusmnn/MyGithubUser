package com.example.mygithubuser.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class SettingThemePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingThemePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : SettingThemePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingThemePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}