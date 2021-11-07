package com.example.notesapp.feature_note.domain.use_case

import com.example.notesapp.feature_note.domain.model.InvalidNoteException
import com.example.notesapp.feature_note.domain.model.Note
import com.example.notesapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws


class AddNotes(
    private val notesRepository: NoteRepository
){

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't empty")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't empty")
        }
         notesRepository.insertNote(note)
    }
}