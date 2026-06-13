package com.example.myprofileapp

import android.app.Application
import com.example.myprofileapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}
