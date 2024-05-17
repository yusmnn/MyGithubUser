package com.example.mygithubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SettingThemeViewModelFactory(private val pref: SettingThemePreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingThemeViewModel::class.java)) {
            return SettingThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown VIewModel class: " + modelClass.name)
    }
}
