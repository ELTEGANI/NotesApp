package com.example.notesapp.feature_note.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState


sealed class AddEditNoteEvent {
    data class EnteredTitle(val value:String):AddEditNoteEvent()
    data class ChangeTitleFocus(val focus:FocusState):AddEditNoteEvent()
    data class EnteredContent(val value:String):AddEditNoteEvent()
    data class ChangeContentFocus(val focusState:FocusState):AddEditNoteEvent()
    data class changeColor(val color:Int):AddEditNoteEvent()
    object saveNote : AddEditNoteEvent()

}