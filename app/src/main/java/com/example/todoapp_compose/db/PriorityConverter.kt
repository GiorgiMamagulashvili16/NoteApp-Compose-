package com.example.todoapp_compose.db

import androidx.room.TypeConverter
import com.example.todoapp_compose.util.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}