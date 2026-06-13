package com.example.myprofileapp.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int // 0-100
    fun isCharging(): Boolean
}
