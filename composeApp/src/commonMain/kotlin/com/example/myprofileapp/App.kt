package com.example.myprofileapp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import com.example.myprofileapp.screens.EditProfileScreen
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.data.ProfileUiState
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// App.kt — Root Composable
// ─────────────────────────────────────────────────────────────────────────────

val CreamColorScheme = lightColorScheme(
    primary = Color(0xFFA5B872), // GreenBanner
    onPrimary = Color(0xFF2C2C2C), // DarkText
    primaryContainer = Color(0xFFF2ECE0), // CardBackground
    onPrimaryContainer = Color(0xFF2C2C2C), // DarkText
    background = Color(0xFFFAF5E9), // CreamBackground
    onBackground = Color(0xFF2C2C2C), // DarkText
    surface = Color(0xFFFAF5E9), // CreamBackground
    onSurface = Color(0xFF2C2C2C), // DarkText
    surfaceVariant = Color(0xFFF2ECE0), // CardBackground
    onSurfaceVariant = Color(0xFF7A7A7A), // LightText
    outline = Color(0xFF7A7A7A) // LightText
)

val CreamDarkColorScheme = darkColorScheme(
    primary = Color(0xFFA5B872), 
    onPrimary = Color(0xFF2C2C2C), 
    primaryContainer = Color(0xFF3C3C3C), 
    onPrimaryContainer = Color(0xFFEEEEEE), 
    background = Color(0xFF1E1E1E), 
    onBackground = Color(0xFFEEEEEE), 
    surface = Color(0xFF1E1E1E), 
    onSurface = Color(0xFFEEEEEE), 
    surfaceVariant = Color(0xFF2C2C2C), 
    onSurfaceVariant = Color(0xFFB0B0B0), 
    outline = Color(0xFFB0B0B0) 
)

@Composable
fun App() {
    val profileViewModel = remember { ProfileViewModel() }
    val uiState by profileViewModel.uiState.collectAsState()

    MaterialTheme(colorScheme = if (uiState.isDarkMode) CreamDarkColorScheme else CreamColorScheme) {
        AppNavigation(profileViewModel, uiState)
    }
}

/**
 * Root navigation composable with ModalNavigationDrawer.
 */
@Composable
fun AppNavigation(profileViewModel: ProfileViewModel, uiState: ProfileUiState) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = if (uiState.isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFAF5E9),
                drawerContentColor = if (uiState.isDarkMode) Color(0xFFEEEEEE) else Color(0xFF2C2C2C)
            ) {
                Spacer(Modifier.height(16.dp))
                Text("Menu Utama", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                BottomNavItem.items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label) },
                        selected = currentRoute == item.route,
                        icon = { Icon(item.icon, contentDescription = null) },
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(Screen.NoteList.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = if (uiState.isDarkMode) Color(0xFFB8860B) else Color(0xFFFFE066),
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = if (uiState.isDarkMode) Color.White else Color(0xFF2C2C2C),
                            unselectedIconColor = if (uiState.isDarkMode) Color.LightGray else Color(0xFF7A7A7A),
                            selectedTextColor = if (uiState.isDarkMode) Color.White else Color(0xFF2C2C2C),
                            unselectedTextColor = if (uiState.isDarkMode) Color.LightGray else Color(0xFF7A7A7A)
                        )
                    )
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                if (currentRoute in BottomNavItem.routes) {
                    BottomNavigationBar(navController = navController, isDarkMode = uiState.isDarkMode)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController    = navController,
                startDestination = Screen.NoteList.route,
                modifier         = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
            ) {

                // ================================================================
                // TAB 1 — NoteList
                // ================================================================
                composable(route = Screen.NoteList.route) {
                    NoteListScreen(
                        onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                        onAddClick = { navController.navigate(Screen.AddNote.route) },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        isDarkMode = uiState.isDarkMode,
                        onToggleDark = { profileViewModel.toggleDarkMode() }
                    )
                }

                // ================================================================
                // TAB 2 — Favorites
                // ================================================================
                composable(route = Screen.Favorites.route) {
                    FavoritesScreen(
                        onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        isDarkMode = uiState.isDarkMode,
                        onToggleDark = { profileViewModel.toggleDarkMode() }
                    )
                }

                // ================================================================
                // TAB 3 — Profile
                // ================================================================
                composable(route = Screen.Profile.route) {
                    ProfileScreen(
                        uiState = uiState,
                        onEditClick = { navController.navigate(Screen.EditProfile.route) },
                        onToggleDark = { profileViewModel.toggleDarkMode() },
                        onSaveContact = { field, value -> profileViewModel.updateContactField(field, value) },
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
                
                // ================================================================
                // EDIT PROFILE
                // ================================================================
                composable(route = Screen.EditProfile.route) {
                    EditProfileScreen(
                        uiState = uiState,
                        onSave = { name, bio, email, phone, location ->
                            profileViewModel.saveProfile(name, bio, email, phone, location)
                            navController.popBackStack()
                        },
                        onCancel = { navController.popBackStack() }
                    )
                }

                // ================================================================
                // ADD NOTE
                // ================================================================
                composable(route = Screen.AddNote.route) {
                    AddNoteScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                // ================================================================
                // NOTE DETAIL
                // ================================================================
                composable(
                    route     = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    NoteDetailScreen(
                        noteId      = noteId,
                        onBack      = { navController.popBackStack() },
                        onEditClick = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                    )
                }

                // ================================================================
                // EDIT NOTE
                // ================================================================
                composable(
                    route     = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
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
}

// ─────────────────────────────────────────────────────────────────────────────
// BottomNavigationBar
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun BottomNavigationBar(navController: NavController, isDarkMode: Boolean) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFAF5E9),
        contentColor = if (isDarkMode) Color(0xFFEEEEEE) else Color(0xFF2C2C2C)
    ) {
        BottomNavItem.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick  = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.NoteList.route) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                icon  = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = if (isDarkMode) Color(0xFFEEEEEE) else Color(0xFF2C2C2C),
                    unselectedIconColor = if (isDarkMode) Color(0xFF7A7A7A) else Color(0xFF7A7A7A),
                    selectedTextColor = if (isDarkMode) Color(0xFFEEEEEE) else Color(0xFF2C2C2C),
                    unselectedTextColor = if (isDarkMode) Color(0xFF7A7A7A) else Color(0xFF7A7A7A),
                    indicatorColor = if (isDarkMode) Color(0xFFB8860B) else Color(0xFFFFE066) // Yellow accent
                )
            )
        }
    }
}