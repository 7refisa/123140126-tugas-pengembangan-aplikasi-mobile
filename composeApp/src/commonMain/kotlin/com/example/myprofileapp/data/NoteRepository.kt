package com.example.myprofileapp.data

import androidx.compose.runtime.mutableStateListOf

/**
 * Repository dengan mutable state menggunakan mutableStateListOf
 * agar Compose bisa otomatis recompose saat data berubah.
 *
 * Data bertahan selama app berjalan (in-memory).
 * Minggu 7: akan diganti dengan Room/SQLDelight.
 */
object NoteRepository {

    // mutableStateListOf membuat Compose reaktif terhadap perubahan list
    val notes = mutableStateListOf(
        Note(
            id         = 1,
            title      = "Belajar Compose Navigation",
            content    = "NavHost, NavController, dan Routes adalah tiga komponen inti navigasi di Jetpack Compose.",
            isFavorite = true,
            createdAt  = "18 Mei 2026"
        ),
        Note(
            id         = 2,
            title      = "Kotlin Sealed Class",
            content    = "Sealed class cocok untuk merepresentasikan hierarki terbatas dan routes secara type-safe.",
            isFavorite = false,
            createdAt  = "17 Mei 2026"
        )
    )

    private var nextId: Int = 3

    /** Tambah catatan baru */
    fun addNote(title: String, content: String) {
        notes.add(
            Note(
                id        = nextId++,
                title     = title,
                content   = content,
                createdAt = "1 Jun 2026"
            )
        )
    }

    /** Update catatan yang sudah ada berdasarkan ID */
    fun updateNote(id: Int, title: String, content: String) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes[index] = notes[index].copy(title = title, content = content)
        }
    }

    /** Toggle status favorit */
    fun toggleFavorite(id: Int) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes[index] = notes[index].copy(isFavorite = !notes[index].isFavorite)
        }
    }

    /** Cari catatan berdasarkan ID */
    fun findById(id: Int): Note? = notes.find { it.id == id }

    /** Daftar favorit */
    val favoriteNotes: List<Note>
        get() = notes.filter { it.isFavorite }
}