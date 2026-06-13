# Tugas Praktikum Minggu 7 — Local Data Storage

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App & News Reader** berbasis Compose Multiplatform. Dikembangkan dari tugas minggu sebelumnya (Minggu 6: Networking & REST API) dengan menambahkan fungsionalitas penyimpanan data lokal (*Local Data Storage*). Aplikasi ini kini mengimplementasikan arsitektur *Offline-First*, menggunakan SQLDelight untuk penyimpanan struktur relasional (catatan) dan Multiplatform Settings (DataStore) untuk key-value preferences (pengaturan tema dan sortir).

---

## Fitur yang Diimplementasikan (Week 7)

- **SQLDelight Database:** Data catatan (Notes) sekarang disimpan secara persisten menggunakan SQLDelight.
- **Offline-First Architecture:** Aplikasi memprioritaskan data dari database lokal, memungkinkan fitur penuh Notes meskipun tidak ada koneksi internet.
- **Multiplatform Settings:** Menyimpan preferensi pengguna seperti Dark Mode dan preferensi pengurutan (Sort Order) agar tidak hilang saat aplikasi ditutup.
- **Fitur Search:** Menambahkan bilah pencarian pada halaman list catatan yang secara reaktif melakukan filter query via *StateFlow*.
- **Fitur Sort:** Pengguna dapat mengurutkan catatan berdasarkan "Terbaru" atau "Terlama".
- **Fitur Delete:** Ditambahkan popup konfirmasi sebelum menghapus catatan secara permanen dari database.
- Mempertahankan seluruh fungsionalitas dari tugas sebelumnya termasuk pengambilan data berita via *Networking*.

---

## Struktur Folder Terkini (Fokus Week 7)

```
commonMain/kotlin/com/example/myprofileapp/
│
├── local/
│   ├── DatabaseDriverFactory.kt   # Expect class untuk Driver SQLDelight
│   ├── SettingsFactory.kt         # Expect class untuk Multiplatform Settings
│   └── SettingsManager.kt         # Mengatur DataStore Preferences (Dark Mode, Sort)
│
├── sqldelight/
│   └── com/example/myprofileapp/db/Note.sq # Definisi Skema Tabel dan Query SQL
│
├── viewmodel/
│   ├── NotesViewModel.kt          # Source of truth StateFlow untuk UI Notes (CRUD & Search)
│   └── ProfileViewModel.kt        # Mengonsumsi SettingsManager untuk persistensi UI
│
└── data/
    └── NoteRepository.kt          # Mengelola eksekusi query dari SQLDelight database
```

---

## Arsitektur & Penyimpanan Lokal

Aplikasi ini menggunakan pola `MVVM` untuk integrasi penyimpanan:
1. `SQLDelight` menyediakan driver platform-spesifik (`AndroidSqliteDriver`, `NativeSqliteDriver`) dan diinjeksi ke level aplikasi.
2. `NoteRepository` menjembatani ViewModel dengan *Generated Database Queries*.
3. `NotesViewModel` mengubah database event (Flow) menjadi `StateFlow` dan menangani *Search* serta *Sort* secara *Asynchronous*.

---

## Teknologi yang Digunakan

| Komponen | Library |
|----------|---------|
| UI Framework | Compose Multiplatform |
| Local Relational DB | `app.cash.sqldelight:android-driver / native-driver` |
| Local Preferences | `com.russhwolf:multiplatform-settings-coroutines` |
| Tanggal & Waktu | `org.jetbrains.kotlinx:kotlinx-datetime` |
| Networking | `io.ktor:ktor-client-core` & Serialization |
| Image Loader | `io.coil-kt.coil3:coil-compose` |

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka dengan Android Studio Hedgehog atau lebih baru.
3. Tunggu hingga proses *Sync Gradle* dan *Downloading Dependencies* selesai (termasuk library SQLDelight).
4. Jalankan aplikasi di emulator atau *device* Android.
5. Coba buat, ubah, hapus catatan, serta ubah mode gelap. Matikan aplikasi dan buka lagi untuk melihat data yang bertahan secara lokal!
