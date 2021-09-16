package com.example.todoapp_compose.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp_compose.util.Priority

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var title:String?,
    var description:String?,
    var priority:Priority,
    var bgColor: String
)
