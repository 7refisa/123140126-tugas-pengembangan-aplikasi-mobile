package com.example.myprofileapp.local

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory(private val context: Context) {
    actual fun createSettings(): ObservableSettings {
        val delegate = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}
