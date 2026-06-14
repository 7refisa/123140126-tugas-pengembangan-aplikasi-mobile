# Tugas Minggu 8 — Platform-Specific Features

| Nama | NIM |
| --- | --- |
| Refi Ikhsanti | 123140126 |

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App & News Reader** berbasis Compose Multiplatform. Dikembangkan dari tugas minggu sebelumnya dengan menambahkan fungsionalitas fitur spesifik platform (*Platform-Specific Features*). Aplikasi ini kini mengimplementasikan **Dependency Injection** menggunakan Koin dan menggunakan mekanisme `expect/actual` dari Kotlin Multiplatform untuk mengakses API native tiap platform seperti informasi perangkat dan status jaringan.

---

## Fitur yang Diimplementasikan

- **Dependency Injection (Koin):** Seluruh dependensi (DatabaseDriver, Settings, ViewModel, Repository) kini diinjeksi menggunakan Koin secara global.
- **DeviceInfo (expect/actual):** Menampilkan nama model perangkat, versi OS, dan versi aplikasi secara dinamis dari API native tiap platform.
- **NetworkMonitor (expect/actual):** Mendeteksi koneksi internet secara *real-time* dan menampilkan indikator merah (No Internet Connection) jika offline.
- **BatteryInfo (Bonus):** Menampilkan sisa persentase baterai dan status *charging* di halaman Profil.
- Mempertahankan fungsionalitas dari tugas sebelumnya (SQLDelight, Ktor, Settings).

---

## Struktur Folder

```
composeApp/src/
│
├── commonMain/.../myprofileapp/
│   ├── di/
│   │   ├── AppModule.kt           # Koin module utama (factory, single)
│   │   ├── KoinHelper.kt          # Fungsi initKoin
│   │   └── PlatformModule.kt      # Expect function untuk platformModule
│   ├── platform/
│   │   ├── DeviceInfo.kt          # Expect class
│   │   ├── NetworkMonitor.kt      # Expect class
│   │   └── BatteryInfo.kt         # Expect class
│
├── androidMain/.../myprofileapp/
│   ├── MyApp.kt                   # Entry point inisialisasi Koin di Android
│   ├── di/PlatformModule.android.kt # Actual module
│   └── platform/                  # Actual implementation (Build.MODEL, ConnectivityManager, BatteryManager)
│
├── iosMain/.../myprofileapp/
│   ├── di/PlatformModule.ios.kt   # Actual module
│   └── platform/                  # Actual implementation (UIDevice)
```

---

## Arsitektur & Injeksi Dependensi

Aplikasi ini menggunakan pola **Dependency Injection (DI)** menggunakan **Koin**:
1. `KoinContext` digunakan di `App.kt` dan modul diinisialisasi melalui `startKoin` di *entry point* masing-masing platform.
2. `koinInject<T>()` digunakan pada *Composable* untuk mendapatkan instance dari `ViewModel` atau *Platform APIs* seperti `DeviceInfo`.
3. Fungsi spesifik platform memanfaatkan API bawaan (`Context` di Android, `UIDevice` di iOS) dan dikelola via *Koin Component*.

---

## Teknologi yang Digunakan

| Komponen | Library |
|----------|---------|
| UI Framework | Compose Multiplatform |
| Dependency Injection | `io.insert-koin:koin-core`, `koin-compose` |
| Local Relational DB | `app.cash.sqldelight` |
| Local Preferences | `com.russhwolf:multiplatform-settings` |
| Networking | `io.ktor:ktor-client-core` |

---

## Screenshoots

| Network Status Indicator | Device & Battery Info |
| --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260614_103645" src="https://github.com/user-attachments/assets/a4959331-5d41-48a9-9892-b1a5dcadb588" /> | <img width="1080" height="2400" alt="Screenshot_20260614_103615" src="https://github.com/user-attachments/assets/1a45fde5-86c2-4a15-bb07-56c024389a87" /> |

---

## Video Demo

https://github.com/user-attachments/assets/be939dfa-7c27-4b37-9fa1-d2f6084ffb6b

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka dengan Android Studio Hedgehog atau lebih baru.
3. Tunggu hingga proses *Sync Gradle* dan *Downloading Dependencies* selesai (termasuk library SQLDelight).
4. Jalankan aplikasi di emulator atau *device* Android.
5. Coba buat, ubah, hapus catatan, serta ubah mode gelap. Matikan aplikasi dan buka lagi untuk melihat data yang bertahan secara lokal!
