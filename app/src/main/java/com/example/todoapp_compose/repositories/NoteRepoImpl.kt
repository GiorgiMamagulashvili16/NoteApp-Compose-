package com.example.todoapp_compose.repositories

import com.example.todoapp_compose.db.NoteDao
import com.example.todoapp_compose.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepoImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepo {
    override suspend fun addNote(note: Note) = withContext(Dispatchers.IO) {
        dao.addNote(note)
    }

    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNote()
    override suspend fun deleteAllNote() = withContext(Dispatchers.IO) {
        dao.deleteAllNotes()
    }

    override suspend fun getNoteById(id: Int): Note = withContext(Dispatchers.IO) {
        return@withContext dao.getNoteById(id)
    }

    override suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        dao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        dao.deleteNote(note)
    }

    override fun searchNote(query: String): Flow<List<Note>> = dao.searchNote(query = query)
}