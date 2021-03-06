package com.example.notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.feature_note.data.data_source.NoteDataBase
import com.example.notesapp.feature_note.data.repository.NoteRepositoryImp
import com.example.notesapp.feature_note.domain.repository.NoteRepository
import com.example.notesapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    fun provideNoteDataBase(application: Application):NoteDataBase{
        return Room.inMemoryDatabaseBuilder(application,NoteDataBase::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDataBase: NoteDataBase):NoteRepository{
        return NoteRepositoryImp(noteDataBase.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(noteRepository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNotes = DeleteNotes(noteRepository),
            addNote = AddNotes(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}