package com.example.myprofileapp.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual class SettingsFactory {
    actual fun createSettings(): ObservableSettings {
        val delegate = Preferences.userRoot().node("com.example.myprofileapp")
        return PreferencesSettings(delegate)
    }
}
