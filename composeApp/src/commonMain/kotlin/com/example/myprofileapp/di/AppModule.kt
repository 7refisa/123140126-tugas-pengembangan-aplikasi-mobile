package com.example.myprofileapp.di

import com.example.myprofileapp.ai.*
import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.local.DatabaseDriverFactory
import com.example.myprofileapp.local.SettingsFactory
import com.example.myprofileapp.local.SettingsManager
import com.example.myprofileapp.viewmodel.NewsViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val commonModule = module {
    single { SettingsManager(get<SettingsFactory>().createSettings()) }
    single { NotesDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single { NoteRepository(get()) }
    
    // HTTP Client
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                level = LogLevel.INFO
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                connectTimeoutMillis = 60000
                socketTimeoutMillis = 60000
            }
        }
    }

    // AI Services
    single { GeminiService(get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }

    factory { ProfileViewModel(get()) }
    factory { NotesViewModel(get(), get()) }
    factory { ChatViewModel(get()) }
    factory { NewsViewModel() }
}

val appModule = module {
    includes(commonModule, platformModule())
}
