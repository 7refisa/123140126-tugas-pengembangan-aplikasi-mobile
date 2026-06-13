package com.example.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController

import com.example.myprofileapp.di.initKoin

fun MainViewController() = ComposeUIViewController { 
    App() 
}

fun initKoinIos() {
    initKoin()
}