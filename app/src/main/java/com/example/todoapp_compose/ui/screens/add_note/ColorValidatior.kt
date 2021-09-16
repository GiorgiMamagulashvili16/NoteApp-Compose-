package com.example.todoapp_compose.ui.screens.add_note

fun validateColor(color: String): String {
    return when (color) {
        "Green" -> BackgroundColor.Green.color
        "Yellow" -> BackgroundColor.Yellow.color
        "Gray" -> BackgroundColor.Gray.color
        else -> BackgroundColor.Blue.color

    }
}

sealed class BackgroundColor(val color: String) {
    object Green : BackgroundColor("#1abc9c")
    object Yellow : BackgroundColor("#e1b12c")
    object Blue : BackgroundColor("#2980b9")
    object Gray:BackgroundColor("#718093")
}