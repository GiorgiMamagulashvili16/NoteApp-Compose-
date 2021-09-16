package com.example.todoapp_compose.db

import androidx.room.*
import com.example.todoapp_compose.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table")
    fun getAllNote(): Flow<List<Note>>

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE id =:noteId")
    fun getNoteById(noteId: Int): Note

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note_table WHERE title LIKE :query")
     fun searchNote(query: String): Flow<List<Note>>
}