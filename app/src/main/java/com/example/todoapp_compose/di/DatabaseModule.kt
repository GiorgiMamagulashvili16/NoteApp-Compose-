package com.example.todoapp_compose.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp_compose.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext app: Context): NoteDatabase = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        "NoteDatabase"
    ).build()

    @Provides
    @Singleton
    fun provideDao(db: NoteDatabase) = db.getDao()
}