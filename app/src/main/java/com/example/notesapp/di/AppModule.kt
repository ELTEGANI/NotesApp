package com.example.notesapp.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.feature_note.data.data_source.NoteDataBase
import com.example.notesapp.feature_note.data.repository.NoteRepositoryImp
import com.example.notesapp.feature_note.domain.repository.NoteRepository
import com.example.notesapp.feature_note.domain.use_case.DeleteNotes
import com.example.notesapp.feature_note.domain.use_case.GetNotes
import com.example.notesapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDataBase(application: Application):NoteDataBase{
        return Room.databaseBuilder(application,NoteDataBase::class.java,NoteDataBase.DATABASE_NAME)
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
            deleteNotes = DeleteNotes(noteRepository)
        )
    }
}