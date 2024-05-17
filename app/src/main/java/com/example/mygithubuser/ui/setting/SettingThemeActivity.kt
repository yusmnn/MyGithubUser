package com.example.mygithubuser.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.databinding.ActivitySettingThemeBinding

class SettingThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Setting"

        val switchTheme = binding.switchTheme

        val pref = SettingThemePreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingThemeViewModelFactory(pref))[SettingThemeViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener() { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }
}