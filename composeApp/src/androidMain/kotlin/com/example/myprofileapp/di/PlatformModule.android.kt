package com.example.myprofileapp.di

import com.example.myprofileapp.local.DatabaseDriverFactory
import com.example.myprofileapp.local.SettingsFactory
import com.example.myprofileapp.platform.BatteryInfo
import com.example.myprofileapp.platform.DeviceInfo
import com.example.myprofileapp.platform.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(get()) }
    single { SettingsFactory(get()) }
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
}
