package com.example.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyProfileApp",
    ) {
        val driverFactory = com.example.myprofileapp.local.DatabaseDriverFactory()
        val settingsFactory = com.example.myprofileapp.local.SettingsFactory()
        App(driverFactory, settingsFactory)
    }
}