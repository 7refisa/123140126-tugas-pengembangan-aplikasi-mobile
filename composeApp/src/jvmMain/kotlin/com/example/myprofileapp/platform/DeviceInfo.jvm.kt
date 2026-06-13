package com.example.myprofileapp.platform

actual class DeviceInfo {
    actual fun getDeviceName(): String = System.getProperty("os.name") ?: "Desktop"
    actual fun getOsVersion(): String = System.getProperty("os.version") ?: "Unknown"
    actual fun getAppVersion(): String = "1.0.0"
}
