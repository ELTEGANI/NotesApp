package com.example.notesapp.feature_note.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.feature_note.domain.model.InvalidNoteException
import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter Title ..."
    ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some Content..."
    ))

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("noteid")?.let {noteId->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {note->
                          currentId = note.id
                          _noteTitle.value = noteTitle.value.copy(
                              text = note.title,
                              isHintVisible = false
                          )
                        _noteContent.value = noteTitle.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(addEditNoteEvent: AddEditNoteEvent){
          when(addEditNoteEvent){
              is AddEditNoteEvent.EnteredTitle->{
                  _noteContent.value = noteTitle.value.copy(
                      text = addEditNoteEvent.value
                  )
              }
              is AddEditNoteEvent.ChangeContentFocus->{
                  _noteTitle.value = noteTitle.value.copy(
                      isHintVisible = !addEditNoteEvent.focusState.isFocused
                              && noteTitle.value.text.isBlank()
                  )
              }
              is AddEditNoteEvent.EnteredContent->{
                  _noteContent.value = _noteContent.value.copy(
                      text = addEditNoteEvent.value
                  )
              }
              is AddEditNoteEvent.ChangeContentFocus->{
                  _noteTitle.value = _noteContent.value.copy(
                      isHintVisible = !addEditNoteEvent.focusState.isFocused
                              && noteContent.value.text.isBlank()
                  )
              }
              is AddEditNoteEvent.changeColor->{
                  _noteColor.value = addEditNoteEvent.color
              }
              is AddEditNoteEvent.saveNote->{
                  viewModelScope.launch {
                      try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timesStamp = System.currentTimeMillis().toString(),
                                color = noteColor.value,
                                id = currentId
                            )
                        )
                          _eventFlow.emit(UiEvent.SaveNote)
                      }catch (e:InvalidNoteException){
                         _eventFlow.emit(
                             UiEvent.ShowSnackBar(
                                 message = e.message ?: "Couldnt Save Note"
                             )
                         )
                      }
                  }
              }
          }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
        object SaveNote:UiEvent()
    }
}