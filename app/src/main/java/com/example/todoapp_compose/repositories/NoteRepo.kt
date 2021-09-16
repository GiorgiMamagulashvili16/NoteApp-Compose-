package com.example.todoapp_compose.repositories

import com.example.todoapp_compose.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepo {

    suspend fun addNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteAllNote()
    suspend fun getNoteById(id: Int): Note
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun searchNote(query: String): Flow<List<Note>>
}