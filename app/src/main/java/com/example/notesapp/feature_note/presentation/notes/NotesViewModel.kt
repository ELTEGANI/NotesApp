package com.example.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notesapp.feature_note.domain.use_case.NoteUseCases
import com.example.notesapp.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel(){

    private val _state = mutableStateOf(NotesState())
    val state:State<NotesState> = _state

    fun onEvent(notesEvent: NotesEvent){
        when(notesEvent){
            is NotesEvent.Order->{

            }

            is NotesEvent.DeleteNote->{

            }

            is NotesEvent.RestoreNote->{

            }

            is NotesEvent.ToggleOrderSection->{

            }
        }
    }
}