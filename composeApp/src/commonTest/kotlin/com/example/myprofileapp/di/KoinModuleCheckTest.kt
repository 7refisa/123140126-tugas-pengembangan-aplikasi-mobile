package com.example.myprofileapp.di

import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import com.example.myprofileapp.local.SettingsFactory
import com.example.myprofileapp.local.DatabaseDriverFactory
import kotlin.test.Test

import kotlin.test.BeforeTest
import kotlin.test.AfterTest
import org.koin.core.context.stopKoin
import io.mockk.mockk

class KoinModuleCheckTest : KoinTest {

    @BeforeTest
    fun setUp() {
        stopKoin()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun checkAllModules() {
        koinApplication {
            modules(commonModule)
        }.checkModules {
            withInstance<SettingsFactory>(mockk(relaxed = true))
            withInstance<DatabaseDriverFactory>(mockk(relaxed = true))
        }
    }
}
