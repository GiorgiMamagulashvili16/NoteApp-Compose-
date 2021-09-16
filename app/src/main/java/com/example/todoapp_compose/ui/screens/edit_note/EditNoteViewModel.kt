package com.example.todoapp_compose.ui.screens.edit_note

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.repositories.NoteRepoImpl
import com.example.todoapp_compose.ui.screens.dashboard.DashBoardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repo: NoteRepoImpl
) : ViewModel() {

    var newTitle = mutableStateOf("")
    fun onTitleChange(new: String) = viewModelScope.launch {
        newTitle.value = new
    }

    var newPriority = mutableStateOf("High")
    fun onPriorityChange(new: String) = viewModelScope.launch {
        newPriority.value = new
    }

    var newDesc = mutableStateOf("")
    fun onDescriptionChange(new: String) = viewModelScope.launch {
        newDesc.value = new
    }

    var color = mutableStateOf("Green")
    fun onColorChange(new: String) = viewModelScope.launch {
        color.value = new
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repo.updateNote(note)
    }

    suspend fun getNoteById(id: Int): Note {
        return repo.getNoteById(id)
    }

}