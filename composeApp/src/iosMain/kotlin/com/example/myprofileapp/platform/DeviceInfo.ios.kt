package com.example.myprofileapp.platform

import platform.UIKit.UIDevice
import platform.Foundation.NSBundle

actual class DeviceInfo {
    actual fun getDeviceName(): String = UIDevice.currentDevice.name
    actual fun getOsVersion(): String = "iOS ${UIDevice.currentDevice.systemVersion}"
    actual fun getAppVersion(): String {
        return NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "1.0.0"
    }
}
