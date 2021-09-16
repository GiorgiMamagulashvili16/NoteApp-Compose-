package com.example.todoapp_compose.util

sealed class MainNavigation(val route:String) {
    object Dashboard:MainNavigation("dashboard_screen")
    object AddNote:MainNavigation("add_note_screen")
    object EditNote:MainNavigation("edit_note/{id}")
}