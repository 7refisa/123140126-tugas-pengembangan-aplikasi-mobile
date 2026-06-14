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

Total Line Coverage **83.3%**

| Testing | Coverage Results |
| --- | --- |
| <img width="1536" height="816" alt="image" src="https://github.com/user-attachments/assets/a27ce39b-f60b-4db5-9e90-b3a0cf133423" /> | <img width="1536" height="812" alt="image" src="https://github.com/user-attachments/assets/6cdbe13c-9191-4dba-89cf-2103e4fab9f8" /> |

### Video Demo Testing

https://github.com/user-attachments/assets/ab822dbe-b0b4-482d-ab19-ea6013172f86

---
