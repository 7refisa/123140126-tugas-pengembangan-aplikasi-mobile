# Tugas Minggu 7 — Local Data Storage

| Nama | NIM |
| --- | --- |
| Refi Ikhsanti | 123140126 |

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App & News Reader** berbasis Compose Multiplatform. Dikembangkan dari tugas minggu sebelumnya (Minggu 6: Networking & REST API) dengan menambahkan fungsionalitas penyimpanan data lokal (*Local Data Storage*). Aplikasi ini kini mengimplementasikan arsitektur *Offline-First*, menggunakan SQLDelight untuk penyimpanan struktur relasional (catatan) dan Multiplatform Settings (DataStore) untuk key-value preferences (pengaturan tema dan sortir).

---

## Fitur yang Diimplementasikan

- **SQLDelight Database:** Data catatan (Notes) sekarang disimpan secara persisten menggunakan SQLDelight.
- **Offline-First Architecture:** Aplikasi memprioritaskan data dari database lokal, memungkinkan fitur penuh Notes meskipun tidak ada koneksi internet.
- **Multiplatform Settings:** Menyimpan preferensi pengguna seperti Dark Mode dan preferensi pengurutan (Sort Order) agar tidak hilang saat aplikasi ditutup.
- **Fitur Search:** Menambahkan bilah pencarian pada halaman list catatan yang secara reaktif melakukan filter query via *StateFlow*.
- **Fitur Sort:** Pengguna dapat mengurutkan catatan berdasarkan "Terbaru" atau "Terlama".
- **Fitur Delete:** Ditambahkan popup konfirmasi sebelum menghapus catatan secara permanen dari database.
- Mempertahankan seluruh fungsionalitas dari tugas sebelumnya termasuk pengambilan data berita via *Networking*.

---

## Struktur Folder

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

## Screenshoots

| Create Notes | Shorting | Searching | Detail Catatan |
| --- | --- | --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260614_004222" src="https://github.com/user-attachments/assets/f89a64b8-5def-45aa-a6ce-3cc0fa0a4f19" /> | <img width="1080" height="2400" alt="Screenshot_20260614_004302" src="https://github.com/user-attachments/assets/abb1f9c8-6ba3-44d9-8cfc-7f001e35112a" /> | <img width="1080" height="2400" alt="Screenshot_20260614_004505" src="https://github.com/user-attachments/assets/19aae4e4-09aa-4c6d-9325-05942889863c" /> | <img width="1080" height="2400" alt="Screenshot_20260614_004324" src="https://github.com/user-attachments/assets/5bc227d4-198b-440f-897c-caed0960cbff" /> |

| Edit Catatan | Hapus Catatan | Favorite | 
| --- | --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260614_004341" src="https://github.com/user-attachments/assets/14fd3a8c-6d69-4c68-90e9-95c908345ff5" /> | <img width="1080" height="2400" alt="Screenshot_20260614_004415" src="https://github.com/user-attachments/assets/0f112ab5-07ff-41e8-8140-29ae944abdf5" /> | <img width="1080" height="2400" alt="Screenshot_20260614_004527" src="https://github.com/user-attachments/assets/62ec974d-6a3c-4277-ba3c-a2229370ffbc" /> |

---

## Video Demo

https://github.com/user-attachments/assets/a17f1ba2-c9a2-4ecf-8a4f-595ec8ce2413

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka dengan Android Studio Hedgehog atau lebih baru.
3. Tunggu hingga proses *Sync Gradle* dan *Downloading Dependencies* selesai (termasuk library SQLDelight).
4. Jalankan aplikasi di emulator atau *device* Android.
5. Coba buat, ubah, hapus catatan, serta ubah mode gelap. Matikan aplikasi dan buka lagi untuk melihat data yang bertahan secara lokal!
