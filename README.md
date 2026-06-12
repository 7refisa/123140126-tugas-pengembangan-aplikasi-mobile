# Tugas 4 - Pengembangan Aplikasi Mobile

## Identitas Mahasiswa
* **Nama:** Refi Ikhsanti
* **NIM:** 123140126
* **Program Studi:** Teknik Informatika

---

## Screenshots
**Light Mode**

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/73aeade3-ce54-45a8-9b2e-a4fc87f5b561" />

**Edit Profil**

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/37382fe8-1e7d-444f-9b58-ecbdfb17e171" />

**Dark Mode**

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/389f5af2-1dcc-4a6d-a580-15085027d279" />

**Pop Up Contact Information**

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a57760b7-20a7-4ef5-9cf4-864327e753d1" />

---

## Fitur
- **Tampilan Profil (Clinical UI Theme)** — menampilkan foto, nama, dan role rata kiri dengan *badge* edit, disertai *banner* hijau untuk bio dan *background* berwarna *cream* hangat.
- **Edit Profil Menyeluruh** — form edit profil sekarang diperluas, memungkinkan pengguna untuk tidak hanya mengubah nama dan bio, melainkan juga informasi kontak (Email, Phone, Location) dengan desain *text field* yang variatif.
- **Dark / Light Mode** — toggle interaktif di pojok kanan atas dengan indikator emoji 🌙 / ☀️. Warna teks pada mode gelap sudah dioptimalkan agar tetap terbaca jelas.
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
