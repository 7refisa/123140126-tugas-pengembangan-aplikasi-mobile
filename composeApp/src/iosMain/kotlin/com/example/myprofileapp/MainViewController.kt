package com.example.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController

import com.example.myprofileapp.local.DatabaseDriverFactory
import com.example.myprofileapp.local.SettingsFactory

fun MainViewController() = ComposeUIViewController { 
    val driverFactory = DatabaseDriverFactory()
    val settingsFactory = SettingsFactory()
    App(driverFactory, settingsFactory) 
}