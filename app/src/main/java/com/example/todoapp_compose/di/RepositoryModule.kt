package com.example.todoapp_compose.di

import com.example.todoapp_compose.db.NoteDao
import com.example.todoapp_compose.repositories.NoteRepo
import com.example.todoapp_compose.repositories.NoteRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepo(dao: NoteDao): NoteRepo = NoteRepoImpl(dao)
}