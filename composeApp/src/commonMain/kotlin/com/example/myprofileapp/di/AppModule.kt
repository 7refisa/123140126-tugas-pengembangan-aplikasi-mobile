package com.example.myprofileapp.di

import com.example.myprofileapp.ai.*
import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.local.DatabaseDriverFactory
import com.example.myprofileapp.local.SettingsFactory
import com.example.myprofileapp.local.SettingsManager
import com.example.myprofileapp.viewmodel.ChatViewModel
import com.example.myprofileapp.viewmodel.NewsViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
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
        }
    }

    // AI Services
    single { GeminiService(get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }

    viewModelOf(::ProfileViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::ChatViewModel)
    factory { NewsViewModel() }
}

val appModule = module {
    includes(commonModule, platformModule())
}
