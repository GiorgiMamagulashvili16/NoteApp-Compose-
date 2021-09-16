package com.example.todoapp_compose.ui.screens.add_note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.repositories.NoteRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    val repoImpl: NoteRepoImpl
) : ViewModel() {
    val title = mutableStateOf("")
    fun onTitleChange(new: String) = viewModelScope.launch {
        title.value = new
    }

    val description = mutableStateOf("")
    fun onDescChange(newValue: String) = viewModelScope.launch {
        description.value = newValue
    }

    var isLoading = mutableStateOf(false)
    fun addNote(note: Note) = viewModelScope.launch {
        isLoading.value = true
        withContext(Dispatchers.IO) {
            repoImpl.addNote(note)
            isLoading.value = false
        }
    }
}