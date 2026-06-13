# Tugas Minggu 5 — Navigasi Antar Layar

| Nama | NIM |
| --- | --- |
| Refi Ikhsanti | 123140126 |

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

| Tab Notes | Tambah Catatan | Edit Catatan |
|-----------|----------------|--------------|
| <img width="1080" height="2400" alt="Screenshot_20260613_144748" src="https://github.com/user-attachments/assets/31726896-cf9b-4897-af16-db3b0b6b9bb9" /> | <img width="1080" height="2400" alt="Screenshot_20260613_144835" src="https://github.com/user-attachments/assets/206de21c-8f2e-4131-8da5-f54dae0e4c04" /> | <img width="1080" height="2400" alt="Screenshot_20260613_144852" src="https://github.com/user-attachments/assets/a7a38f6e-4df6-4ba2-9aa5-75b7c21a013c" /> |

| Detail Catatan | Tab Favorite | Tab Profile |
|-------------|--------------|----------------|
| <img width="1080" height="2400" alt="Screenshot_20260613_144903" src="https://github.com/user-attachments/assets/3e399a87-a4e9-4470-aeb8-a416dceae9c5" /> | <img width="1080" height="2400" alt="Screenshot_20260613_144914" src="https://github.com/user-attachments/assets/4cc89d89-fce2-4d2b-9b33-09cdd9d75d54" /> | <img width="1080" height="2400" alt="Screenshot_20260613_144921" src="https://github.com/user-attachments/assets/e36ee625-3516-494a-ae61-737725bbe870" /> |

---

## Navigation Flow Demo

> Video demo: 

https://github.com/user-attachments/assets/e40e7819-03d7-47b2-b50f-e61a355c29f6

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
