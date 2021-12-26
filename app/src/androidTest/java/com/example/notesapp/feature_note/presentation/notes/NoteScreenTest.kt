package com.example.notesapp.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.di.AppModule
import com.example.notesapp.feature_note.presentation.MainActivity
import com.example.notesapp.feature_note.presentation.util.Screen
import com.example.notesapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenTest{
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            CleanArchitectureNoteAppTheme{
                NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                    composable(route = Screen.NotesScreen.route) { NotesScreen(navController = navController) }
                }
            }
        }
    }
}