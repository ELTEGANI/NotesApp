package com.example.notesapp.feature_note.domain.use_case



data class NoteUseCases(
    val getNotes: GetNotes,
    val deleteNotes: DeleteNotes,
    val addNote:AddNotes,
    val getNote: GetNote
)