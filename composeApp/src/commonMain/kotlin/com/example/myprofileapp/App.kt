package com.example.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myprofileapp.navigation.BottomNavItem
import com.example.myprofileapp.navigation.Screen
import com.example.myprofileapp.screens.AddNoteScreen
import com.example.myprofileapp.screens.EditNoteScreen
import com.example.myprofileapp.screens.FavoritesScreen
import com.example.myprofileapp.screens.NoteDetailScreen
import com.example.myprofileapp.screens.NoteListScreen
import com.example.myprofileapp.screens.ProfileScreen

// ─────────────────────────────────────────────────────────────────────────────
// App.kt — Root Composable
//
// Struktur:
//   MaterialTheme
//     └── Scaffold
//           ├── bottomBar: BottomNavigationBar (hanya di top-level screens)
//           └── content:   NavHost (semua destination terdaftar di sini)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}

/**
 * Root navigation composable.
 *
 * NavHost di sini mendaftarkan SEMUA destination:
 *   - 3 tab bottom nav : NoteList, Favorites, Profile
 *   - Non-tab screens  : AddNote, NoteDetail, EditNote
 *
 * Scaffold membungkus NavHost sehingga NavigationBar selalu tampil
 * di bawah dan content area otomatis mendapat padding yang benar.
 */
@Composable
fun AppNavigation() {
    // ── 1. Buat NavController ────────────────────────────────────────────────
    val navController = rememberNavController()

    // ── 2. Scaffold: bottomBar + NavHost ────────────────────────────────────
    Scaffold(
        bottomBar = {
            // NavigationBar hanya tampil di top-level tab destinations.
            // Di NoteDetail, AddNote, dan EditNote bar ini disembunyikan.
            val currentRoute = navController
                .currentBackStackEntryAsState().value?.destination?.route

            if (currentRoute in BottomNavItem.routes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->

        // ── 3. NavHost: mendaftarkan semua destination ───────────────────────
        NavHost(
            navController    = navController,
            startDestination = Screen.NoteList.route   // Screen pertama saat app dibuka
        ) {

            // ================================================================
            // TAB 1 — NoteList
            // Route: "note_list"
            // ================================================================
            composable(route = Screen.NoteList.route) {
                NoteListScreen(
                    onNoteClick = { noteId ->
                        // Forward navigation dengan argument
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    },
                    onAddClick = {
                        navController.navigate(Screen.AddNote.route)
                    }
                )
            }

            // ================================================================
            // TAB 2 — Favorites
            // Route: "favorites"
            // ================================================================
            composable(route = Screen.Favorites.route) {
                FavoritesScreen(
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }

            // ================================================================
            // TAB 3 — Profile
            // Route: "profile"
            // ================================================================
            composable(route = Screen.Profile.route) {
                ProfileScreen()
            }

            // ================================================================
            // ADD NOTE
            // Route: "add_note"
            // Dipanggil dari FAB di NoteListScreen.
            // Tidak memiliki argument.
            // ================================================================
            composable(route = Screen.AddNote.route) {
                AddNoteScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // ================================================================
            // NOTE DETAIL
            // Route  : "note_detail/{noteId}"
            // Argument: noteId (NavType.IntType) — REQUIRED
            // ================================================================
            composable(
                route     = Screen.NoteDetail.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.IntType   // Tipe argument wajib dideklarasikan
                    }
                )
            ) { backStackEntry ->
                // Ambil argument dari backStackEntry
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0

                NoteDetailScreen(
                    noteId      = noteId,
                    onBack      = { navController.popBackStack() },
                    onEditClick = { id ->
                        navController.navigate(Screen.EditNote.createRoute(id))
                    }
                )
            }

            // ================================================================
            // EDIT NOTE
            // Route  : "edit_note/{noteId}"
            // Argument: noteId (NavType.IntType) — REQUIRED
            // ================================================================
            composable(
                route     = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0

                EditNoteScreen(
                    noteId = noteId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// BottomNavigationBar
//
// Komponen STATELESS yang menerima NavController.
// Logika "tab mana yang aktif" dibaca dari currentBackStackEntry.
// Perpindahan tab menggunakan popUpTo + launchSingleTop agar back stack
// tidak menumpuk saat user berpindah-pindah tab.
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun BottomNavigationBar(navController: NavController) {
    // Observe route aktif saat ini secara reaktif
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavItem.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick  = {
                    navController.navigate(item.route) {
                        // ── popUpTo: hapus semua screen di atas startDestination
                        //    agar back stack tidak menumpuk saat ganti tab
                        popUpTo(Screen.NoteList.route) {
                            saveState = true   // Simpan state tab sebelumnya
                        }
                        // ── launchSingleTop: cegah duplikat jika tab sudah aktif
                        launchSingleTop = true
                        // ── restoreState: pulihkan state tab yang sebelumnya disimpan
                        restoreState    = true
                    }
                },
                icon  = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) }
            )
        }
    }
}