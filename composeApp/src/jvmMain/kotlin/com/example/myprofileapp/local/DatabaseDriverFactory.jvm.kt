package com.example.myprofileapp.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.myprofileapp.db.NotesDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:notes.db")
        if (!File("notes.db").exists()) {
            NotesDatabase.Schema.create(driver)
        }
        return driver
    }
}
