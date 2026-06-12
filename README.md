# Tugas Minggu 4 - State Management MVVM

| Nama | NIM |
| --- | --- |
| Refi Ikhsanti | 123140126 |


**Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Program Studi:** Teknik Informatika — Institut Teknologi Sumatera  
**Tahun Akademik:** Genap 2025/2026

---

## Screenshots
**Light Mode**
| Profile | Edit Profile | Dialog Pop-Up |
| --- | --- | --- |
| <img width="1080" height="2400" alt="Screenshot_20260612_232723" src="https://github.com/user-attachments/assets/3d1864b0-bd2a-45e6-a92e-9f0645a0aa0e" /> | <img width="1080" height="2400" alt="Screenshot_20260612_232743" src="https://github.com/user-attachments/assets/b5787560-f878-496c-a5f5-78f3c5e1da50" /> | <img width="1080" height="2400" alt="Screenshot_20260612_234133" src="https://github.com/user-attachments/assets/c9dfa544-5ff2-4d24-aa93-a39db294ab9c" /> |

**Dark Mode**
| Profile | Edit Profile | Dialog Pop-Up | 
| --- | --- | --- | 
| <img width="1080" height="2400" alt="Screenshot_20260612_232818" src="https://github.com/user-attachments/assets/abf894c6-01ba-4597-a900-6b108613a568" /> | <img width="1080" height="2400" alt="Screenshot_20260612_232831" src="https://github.com/user-attachments/assets/b6494ba0-fa07-4f21-82e5-fab50e9661b2" /> | <img width="1080" height="2400" alt="Screenshot_20260612_234146" src="https://github.com/user-attachments/assets/2af2f4f9-88a0-46b1-9579-fa8483156272" /> |

---

## Fitur
- **Tampilan Profile** — menampilkan foto, nama, dan role rata kiri dengan *badge* edit, disertai *banner* hijau untuk bio dan *background* berwarna *cream* hangat.
- **Edit Profile** — form edit profil, memungkinkan pengguna untuk tidak hanya mengubah nama dan bio, melainkan juga informasi kontak (Email, Phone, Location).
- **Dark / Light Mode** — toggle interaktif di pojok kanan atas dengan indikator emoji 🌙 / ☀️.
- **Daftar Kontak Interaktif** — informasi kontak ditampilkan dalam bentuk *list* dengan ikon bulat berwarna-warni. Setiap *list item* dapat diklik untuk menampilkan dialog pop-up berisi detail informasi kontaknya.

---

## Arsitektur MVVM
Proyek ini menerapkan pola **MVVM** untuk memisahkan logika bisnis dari tampilan UI:
- **Model** → `ProfileUiState.kt` menyimpan seluruh data UI sebagai data class yang immutable
- **ViewModel** → `ProfileViewModel.kt` mengelola state lewat `StateFlow` dan mengekspos event handler ke UI
- **View** → `ProfileScreen.kt` dan `EditProfileScreen.kt` bersifat stateless, hanya menerima state dan memanggil event

---

## Cara Clone & Jalankan
```bash
# Clone repository
git clone https://github.com/username/MyProfileMVVM.git

# Masuk ke folder project
cd MyProfileMVVM
```

**Jalankan di Desktop:**
```bash
./gradlew :composeApp:jvmRun
```

**Jalankan di Android:**
Buka di Android Studio → pilih konfigurasi `composeApp` → Run pada emulator atau perangkat fisik.

---
