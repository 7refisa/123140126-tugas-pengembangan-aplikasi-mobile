package com.example.myprofileapp.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myprofileapp.data.Note
import com.example.myprofileapp.utils.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyStateIsDisplayedWhenNoNotes() {
        composeTestRule.setContent {
            NoteListContent(
                notes = emptyList(),
                searchQuery = "",
                sortOrder = "newest",
                onSearchQueryChange = {},
                onNoteClick = {},
                onAddClick = {},
                onChatClick = {},
                onMenuClick = {},
                onToggleFavorite = {},
                isDarkMode = false,
                onToggleDark = {},
                onSetSortOrder = {}
            )
        }

        // Verify empty state message
        composeTestRule.onNodeWithText("Belum ada catatan.\nTekan + untuk mulai menulis!").assertIsDisplayed()
        // Verify FAB is displayed
        composeTestRule.onNodeWithTag(TestTags.FAB_ADD_NOTE).assertIsDisplayed()
    }

    @Test
    fun testNotesAreDisplayedCorrectly() {
        val notes = listOf(
            Note(1L, "First Note", "Content 1", false, "1 Jan 2026"),
            Note(2L, "Second Note", "Content 2", true, "2 Jan 2026")
        )

        composeTestRule.setContent {
            NoteListContent(
                notes = notes,
                searchQuery = "",
                sortOrder = "newest",
                onSearchQueryChange = {},
                onNoteClick = {},
                onAddClick = {},
                onChatClick = {},
                onMenuClick = {},
                onToggleFavorite = {},
                isDarkMode = false,
                onToggleDark = {},
                onSetSortOrder = {}
            )
        }

        // Verify list is displayed
        composeTestRule.onNodeWithTag(TestTags.NOTES_LIST).assertIsDisplayed()
        
        // Verify items
        composeTestRule.onNodeWithText("First Note").assertIsDisplayed()
        composeTestRule.onNodeWithText("Content 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Note").assertIsDisplayed()
    }

    @Test
    fun testSearchQueryEmptyState() {
        composeTestRule.setContent {
            NoteListContent(
                notes = emptyList(),
                searchQuery = "Not Found",
                sortOrder = "newest",
                onSearchQueryChange = {},
                onNoteClick = {},
                onAddClick = {},
                onChatClick = {},
                onMenuClick = {},
                onToggleFavorite = {},
                isDarkMode = false,
                onToggleDark = {},
                onSetSortOrder = {}
            )
        }

        // Verify search empty state message
        composeTestRule.onNodeWithText("Tidak ada catatan yang cocok").assertIsDisplayed()
    }
}
