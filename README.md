# Tugas Minggu 6 — Networking & REST API

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App & News Reader** berbasis Compose Multiplatform. Dikembangkan dari tugas minggu sebelumnya (Minggu 5: Navigasi Antar Layar) dengan menambahkan fitur pengambilan data dari Internet (*networking*) melalui Ktor Client untuk memuat daftar berita terkini menggunakan REST API.

---

## Fitur yang Diimplementasikan

- **Tab News Baru** di dalam Bottom Navigation.
- **Fetch API Otomatis** saat halaman News dibuka.
- **Pull-to-Refresh** untuk menyegarkan daftar berita dari server kapan saja.
- **State Management (Loading, Success, Error)** untuk mempermudah UX saat data diambil dari internet atau saat koneksi terputus.
- **Parsing JSON Otomatis** dengan *Kotlinx Serialization*.
- **Memuat Gambar secara Asinkron** (*Lazy Image Loading*) dari link gambar (*urlToImage*) yang dikembalikan oleh API menggunakan `Coil3`.
- **Detail Berita** menampilkan tautan atau placeholder artikel ketika diklik.
- Mempertahankan seluruh fungsionalitas dari tugas sebelumnya (Note, Favorites, Profile, dan sinkronisasi Dark Mode lintas tab).

---

## Struktur Folder Terkini (Fokus Week 6)

```
commonMain/kotlin/com/example/myprofileapp/
│
├── navigation/
│   ├── Screen.kt              # Tambahan object NewsList & NewsDetail
│   └── BottomNavItem.kt       # Tambahan tab News
│
├── screens/
│   ├── NewsListScreen.kt      # List berita + UI State (Loading/Error/Success) + Pull-to-Refresh
│   ├── NewsDetailScreen.kt    # Menampilkan parameter detail (URL)
│   └── ... (screen tugas 5 lainnya)
│
├── components/
│   └── NoteComponents.kt      
│
├── data/
│   ├── Article.kt             # Data class model @Serializable untuk response berita
│   ├── NewsRepository.kt      # Mengelola Ktor HttpClient dan fetch ke REST API (mock API)
│   └── ... (Note repo dari tugas 5)
│
├── viewmodel/
│   ├── NewsViewModel.kt       # View model khusus News (Coroutine launch + StateFlow)
│   └── ProfileViewModel.kt    
│
└── App.kt                     # Integrasi ViewModel & Composables ke Navigation
```

---

## Arsitektur & Networking

Aplikasi ini menggunakan architecture `MVVM` yang dikombinasikan dengan `StateFlow`:
1. `NewsRepository` memanggil REST API menggunakan `HttpClient.get()` dan me-return `Result<List<Article>>`.
2. `NewsViewModel` mengatur mutasi UI state melalui *sealed class* `NewsUiState`.
3. `NewsListScreen` me-listen via `collectAsState()` dan merender UI sesuai status terkini secara reaktif.

---

## Teknologi yang Digunakan

| Komponen | Library |
|----------|---------|
| UI Framework | Compose Multiplatform |
| Navigasi | `navigation-compose` |
| Networking / HTTP Client | `io.ktor:ktor-client-core:2.3.7` (beserta engine OkHttp untuk Android) |
| JSON Serialization | `io.ktor:ktor-serialization-kotlinx-json:2.3.7` |
| Image Loader | `io.coil-kt.coil3:coil-compose:3.0.4` |
| Asynchronous Processing | Kotlin Coroutines |

---

## Screenshots 

| Success State | Pull to Refresh | Detail Screen | Error State | Loading State |
| --- | --- | --- | --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260613_170216" src="https://github.com/user-attachments/assets/c857ae5d-54d8-499d-a956-4cf08dd31b47" /> | <img width="1080" height="2400" alt="Screenshot_20260613_170239" src="https://github.com/user-attachments/assets/3c81fcf3-d560-4fb4-8280-4b40d4863290" /> | <img width="1080" height="2400" alt="Screenshot_20260613_170313" src="https://github.com/user-attachments/assets/031f8bb8-6028-4832-b92d-c57f5dda328b" /> |<img width="1080" height="2400" alt="Screenshot_20260613_170521" src="https://github.com/user-attachments/assets/365a5b29-97a0-4247-9d5c-5d75441c8c14" /> | <img width="1080" height="2400" alt="Screenshot_20260613_170612" src="https://github.com/user-attachments/assets/84a336d0-1219-4a2d-bb49-0e58e0c6e8d6" /> |

---

## Video Demo

https://github.com/user-attachments/assets/745751b8-86ff-488d-b79a-5b85f80fd9ff

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka dengan Android Studio Hedgehog atau lebih baru.
3. Tunggu hingga proses *Sync Gradle* dan *Downloading Dependencies* selesai (mungkin akan membutuhkan waktu sedikit lebih lama karena penambahan library Ktor dan Coil).
4. Jalankan aplikasi di emulator atau *device* Android dengan koneksi internet yang aktif.
5. Akses tab **News** pada *Bottom Navigation* untuk melihat hasil *fetching* REST API!
