package com.example.myprofileapp.platform

import android.os.Build

actual class DeviceInfo {
    actual fun getDeviceName(): String = Build.MODEL
    actual fun getOsVersion(): String = "Android ${Build.VERSION.RELEASE}"
    actual fun getAppVersion(): String = "1.0.0" // Ideally from BuildConfig, stub for now
}
