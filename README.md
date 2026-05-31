Aplikasi profil pribadi berbasis **Kotlin Multiplatform** dengan **Jetpack Compose**

## Profil
**Nama:** Refi Ikhsanti 

**NIM:** 123140126 

## Screenshot
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/b9bb884a-278e-48e0-b20b-482c943ec119" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/cb3e052b-cac3-44a6-a1ca-e861ed3b9d18" />

## Composable Functions
Ada 3 composable function utama yang reusable:
### 1. `ProfileHeader()`
Menampilkan foto profil berbentuk circular, nama, dan role/jurusan.

### 2. `ProfileCard()`
Container card generik yang bisa diisi konten apa saja. 

### 3. `InfoItem()`
Menampilkan satu baris informasi dengan ikon, label, dan nilai. 

## Komponen yang Digunakan
- `Column` — menyusun elemen secara vertikal
- `Row` — menyusun elemen secara horizontal
- `Box` — menumpuk elemen
- `Card` — container dengan elevation dan rounded corner
- `Text` — menampilkan teks
- `Button` — tombol aksi Contact Me
- `Image` — foto profil
- `Icon` — ikon di setiap info item
- `AlertDialog` — popup saat tombol Contact Me diklik

## Cara Build & Run

### Android
1. Buka project di **Android Studio**
2. Sambungkan HP atau jalankan emulator
3. Klik tombol **Run ▶️**

Atau via terminal:
```shell
.\gradlew.bat :composeApp:assembleDebug
```

### Desktop (JVM)
```shell
.\gradlew.bat :composeApp:run
```
