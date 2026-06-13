package com.example.myprofileapp.platform

actual class BatteryInfo {
    actual fun getBatteryLevel(): Int = 100
    actual fun isCharging(): Boolean = true
}
