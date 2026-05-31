# Tugas Praktikum Minggu 5 — Navigasi Antar Layar

**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Deskripsi

Aplikasi **Notes App** berbasis Compose Multiplatform yang mengimplementasikan navigasi multi-screen menggunakan Jetpack Navigation Compose. Dikembangkan dari tugas minggu sebelumnya dengan menambahkan fitur navigasi lengkap antar layar.

---

## Fitur yang Diimplementasikan

- **Bottom Navigation** dengan 3 tab: Notes, Favorites, Profile
- **Tambah catatan** baru via Floating Action Button (FAB)
- **Edit catatan** yang sudah ada dengan passing `noteId` sebagai argument
- **Detail catatan** dengan tombol edit dan toggle favorit
- **Toggle favorit** langsung dari list maupun halaman detail
- **Tab Favorites** menampilkan hanya catatan yang di-favorit-kan
- **Back navigation** yang proper dari semua screen

---

## Struktur Folder

```
commonMain/kotlin/com/example/myprofileapp/
│
├── navigation/
│   ├── Screen.kt              # Sealed class semua routes (type-safe)
│   └── BottomNavItem.kt       # Sealed class 3 item bottom navigation
│
├── screens/
│   ├── NoteListScreen.kt      # Tab Notes + FAB tambah catatan
│   ├── NoteDetailScreen.kt    # Detail catatan + toggle favorit + edit
│   ├── AddEditNoteScreen.kt   # Form tambah & edit catatan
│   ├── FavoritesScreen.kt     # Tab Favorites
│   └── ProfileScreen.kt       # Tab Profile
│
├── components/
│   └── NoteComponents.kt      # Komponen UI stateless yang reusable
│
├── data/
│   ├── Note.kt                # Data class model catatan
│   └── NoteRepository.kt      # Repository dengan mutableStateListOf
│
└── App.kt                     # Root: Scaffold + NavHost lengkap
```

---

## Arsitektur Navigasi

```
Bottom Navigation Tabs
├── Notes      →  NoteListScreen
│                   ├── [Card] → NoteDetailScreen (noteId: Int)
│                   │               └── [Edit] → EditNoteScreen (noteId: Int)
│                   └── [FAB+] → AddNoteScreen
├── Favorites  →  FavoritesScreen
│                   └── [Card] → NoteDetailScreen (noteId: Int)
└── Profile    →  ProfileScreen
```

**Navigation Options yang digunakan:**
- `popUpTo(Screen.NoteList.route) { saveState = true }` — cegah back stack menumpuk saat ganti tab
- `launchSingleTop = true` — cegah duplikat screen di atas stack
- `restoreState = true` — pulihkan state tab sebelumnya

---

## Passing Arguments

| Route | Argument | Tipe | Keterangan |
|-------|----------|------|------------|
| `note_detail/{noteId}` | `noteId` | `NavType.IntType` | ID catatan yang ditampilkan |
| `edit_note/{noteId}` | `noteId` | `NavType.IntType` | ID catatan yang diedit |

---

## Screenshot

| Tab Notes | Tambah Catatan | Tab Favorite |
|-----------|----------------|--------------|
|<img width="1080" height="2280" alt="Screenshot_20260601_045740" src="https://github.com/user-attachments/assets/63316998-5405-415c-a4c1-18b43b84257a" />|<img width="1080" height="2280" alt="Screenshot_20260601_045906" src="https://github.com/user-attachments/assets/f6a1a16f-4d03-4328-86b4-94c822165844" />|<img width="1080" height="2280" alt="Screenshot_20260601_045936" src="https://github.com/user-attachments/assets/f2ff6b9d-1a19-4f00-96e0-ae6f96bc6817" />|

| Tab Profile | Edit Catatan | Detail Catatan |
|-------------|--------------|----------------|
|<img width="1080" height="2280" alt="Screenshot_20260601_045949" src="https://github.com/user-attachments/assets/8341b187-93ef-4bd0-bdcf-046dc7390071" />|<img width="1080" height="2280" alt="Screenshot_20260601_050046" src="https://github.com/user-attachments/assets/18cad39f-2ef3-4511-a6b6-72d8ba5ebdd9" />|<img width="1080" height="2280" alt="Screenshot_20260601_050059" src="https://github.com/user-attachments/assets/197fe60e-a571-4b8c-a9ee-866e00de1ce5" />|

---

## Navigation Flow Demo

> Video demo: 

https://github.com/user-attachments/assets/594f43d9-c425-4077-a6fb-0781fec09235


---

## Teknologi

| Komponen | Library |
|----------|---------|
| UI Framework | Compose Multiplatform |
| Navigasi | `org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10` |
| State Management | `mutableStateListOf` (in-memory) |
| Arsitektur | MVVM + State Hoisting |
| Target Platform | Android |

---

## Dependency yang Ditambahkan

```kotlin
// composeApp/build.gradle.kts — commonMain.dependencies
implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
```

---

## Cara Menjalankan

1. Clone repository ini
2. Buka dengan Android Studio Hedgehog atau lebih baru
3. Sync Gradle
4. Jalankan di emulator atau device Android (min. API 26)
