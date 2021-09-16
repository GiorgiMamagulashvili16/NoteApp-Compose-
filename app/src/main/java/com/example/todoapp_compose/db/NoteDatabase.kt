package com.example.todoapp_compose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp_compose.entity.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(PriorityConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao
}