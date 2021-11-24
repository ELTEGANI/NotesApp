package com.example.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.feature_note.presentation.add_edit_notes.AddEditNoteScreen
import com.example.notesapp.feature_note.presentation.notes.NotesScreen
import com.example.notesapp.feature_note.presentation.util.Screen
import com.example.notesapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
               Surface(
                   color = MaterialTheme.colors.background
               ) {
                val navController = rememberNavController()
                NavHost(
                navController = navController,
                startDestination = Screen.NotesScreen.route){
                   composable(route = Screen.NotesScreen.route){
                       NotesScreen(navController = navController)
                   }
                    composable(route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                    arguments = listOf(
                        navArgument(name = "noteId"){
                             type = NavType.IntType
                             defaultValue = -1
                        },
                        navArgument(name = "noteColor"){
                            type = NavType.IntType
                            defaultValue = -1
                        },
                    )
                    ){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                }
               }
            }
        }
    }
}

