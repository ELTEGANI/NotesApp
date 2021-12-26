package com.example.notesapp.feature_note.presentation.notes

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.notesapp.di.AppModule
import com.example.notesapp.feature_note.presentation.MainActivity
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

    @Before
    fun setUp(){
        hiltRule.inject()
    }
}