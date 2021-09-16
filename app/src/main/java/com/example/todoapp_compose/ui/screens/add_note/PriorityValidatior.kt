package com.example.todoapp_compose.ui.screens.add_note

import com.example.todoapp_compose.util.Priority

fun validatePriority(string: String): Priority {
    return when (string) {
        "High" -> Priority.High
        "Medium" -> Priority.Medium
        "Low" -> Priority.Low
        else -> Priority.Low
    }
}

fun fromPriority(priority: Priority): String {
    return when (priority) {
        Priority.High -> "H"
        Priority.Medium -> "M"
        Priority.Low -> "L"
    }
}