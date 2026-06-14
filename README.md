# Tugas Minggu 10 — Testing & Dependency Injection

| Nama | NIM |
| --- | --- |
| Refi Ikhsanti | 123140126 |

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## 🧪 Tugas Minggu 10: Testing & Code Coverage

### Daftar Test Cases

1. **NoteRepositoryTest (5 Test Cases)**
   - `should return empty list when no notes exist`
   - `should insert and retrieve note correctly`
   - `should update note correctly`
   - `should delete note correctly`
   - `should return flow of notes using Turbine`
2. **NotesViewModelTest (4 Test Cases)**
   - `initial state should load notes`
   - `addNote should call repository insert`
   - `deleteNote should call repository delete`
   - `toggleSortOrder should update state correctly`
3. **NewsViewModelTest (3 Test Cases)**
   - `fetchNews success updates state with articles`
   - `fetchNews error updates state with error message`
   - `refreshNews clears and fetches news again`
4. **ProfileViewModelTest (9 Test Cases)**
   - Memverifikasi inisialisasi state awal
   - Memverifikasi fungsi toggle *dark mode*
   - Memverifikasi fungsi *update* nama, biodata, dan preferensi profil
5. **NotesScreen UI Test (3 Test Cases)**
   - `displays empty state when no notes`
   - `displays notes when notes are available`
   - `navigates to add note screen when fab is clicked`

### Test Coverage Report

Total Line Coverage telah mencapai **83.3%** (Syarat: >60%, Bonus: >80%).
*(Tambahkan Screenshot Kover HTML Report Anda di sini)*

### Video Demo Testing

*(Tambahkan link/video saat menjalankan `./gradlew test` dan `./gradlew koverHtmlReport` di sini)*

---

## 📝 Tugas Minggu 9 — Integrasi AI API

Aplikasi **Notes App & News Reader** berbasis Compose Multiplatform. Pada minggu ini, aplikasi ditambahkan fitur kecerdasan buatan berupa **Smart Assistant** terintegrasi dengan **Gemini API**. Asisten cerdas ini siap membantu pengguna merangkum catatan, memberikan ide, dan berbincang interaktif langsung dari dalam aplikasi.

---

## Fitur yang Diimplementasikan

- **Gemini API Integration:** Menggunakan `Ktor Client` untuk terhubung ke endpoint `gemini-flash-latest` (otomatis mendeteksi model tercepat terbaru yang didukung).
- **Smart Chatbot / AI Assistant:** Menyediakan antarmuka interaktif bagi pengguna untuk mengobrol dengan AI, dilengkapi memori percakapan (*multi-turn*).
- **Prompt Engineering & Persona:** Disematkan `System Prompt` khusus agar AI bertindak konsisten sebagai asisten produktivitas dengan gaya bahasa yang natural, *to the point*, dan tidak bertele-tele.
- **Robust Error Handling:** Menggunakan mekanisme `sealed class` untuk pemetaan error API, *timeout handling* yang dioptimasi (60 detik), dan *safe parsing*.
- **Responsive AI UI/UX:** Menampilkan animasi *typing indicator* reaktif saat AI sedang berpikir dan desain *bubble chat* yang rapi.
- Mempertahankan fungsionalitas dari tugas sebelumnya (SQLDelight, Ktor, Settings, DI Koin, Expect/Actual Platform APIs).

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
| Generative AI | Google Gemini 2.0 Flash API |
| Networking | `io.ktor:ktor-client-core`, `ktor-client-content-negotiation` |
| Data Serialization | `org.jetbrains.kotlinx:kotlinx-serialization-json` |

---

## Prasyarat (API Key Gemini)

Untuk menjalankan fitur AI, Anda wajib memasukkan **Gemini API Key**:
1. Dapatkan kunci gratis di [Google AI Studio](https://aistudio.google.com).
2. Buat file bernama `local.properties` di folder root project.
3. Tambahkan baris berikut:
   ```properties
   GEMINI_API_KEY=YOUR_GEMINI_API
   ```

---

## Screenshoots

| Error Handling | Loading State |
| --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260614_155808" src="https://github.com/user-attachments/assets/4641aa6f-8579-48d5-9220-8021f4b0e825" /> | <img width="1080" height="2400" alt="Screenshot_20260614_160004" src="https://github.com/user-attachments/assets/02cc721a-7f0e-4ee5-afcf-3a5066f2e60b" /> |

---

## Video Demo

https://github.com/user-attachments/assets/eb38bc54-4280-4bd3-ab1e-5ef19ccf7554

---

## Cara Menjalankan

1. Clone repository ini.
2. Buka dengan Android Studio Hedgehog atau lebih baru.
3. Tunggu hingga proses *Sync Gradle* dan *Downloading Dependencies* selesai (termasuk library SQLDelight).
4. Jalankan aplikasi di emulator atau *device* Android.
5. Coba buat, ubah, hapus catatan, serta ubah mode gelap. Matikan aplikasi dan buka lagi untuk melihat data yang bertahan secara lokal!
