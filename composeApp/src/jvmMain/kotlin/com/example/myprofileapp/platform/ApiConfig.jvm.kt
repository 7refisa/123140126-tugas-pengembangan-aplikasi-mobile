package com.example.myprofileapp.platform

actual object ApiConfig {
    actual val geminiApiKey: String = System.getenv("GEMINI_API_KEY") ?: "dummy_key_for_jvm"
}
