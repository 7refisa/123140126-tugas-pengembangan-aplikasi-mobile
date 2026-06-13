package com.example.myprofileapp.di

import com.example.myprofileapp.data.NoteRepository
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.local.DatabaseDriverFactory
import com.example.myprofileapp.local.SettingsFactory
import com.example.myprofileapp.local.SettingsManager
import com.example.myprofileapp.viewmodel.NewsViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import org.koin.dsl.module

val commonModule = module {
    single { SettingsManager(get<SettingsFactory>().createSettings()) }
    single { NotesDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single { NoteRepository(get()) }
    
    factory { ProfileViewModel(get()) }
    factory { NotesViewModel(get(), get()) }
    factory { NewsViewModel() }
}

val appModule = module {
    includes(commonModule, platformModule())
}
