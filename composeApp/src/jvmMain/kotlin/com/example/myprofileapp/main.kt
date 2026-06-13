package com.example.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import com.example.myprofileapp.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyProfileApp",
    ) {
        App()
    }
}