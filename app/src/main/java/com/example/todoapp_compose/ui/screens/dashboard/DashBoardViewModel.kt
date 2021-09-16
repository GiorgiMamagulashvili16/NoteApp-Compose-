package com.example.todoapp_compose.ui.screens.dashboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.repositories.NoteRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val noteRepo: NoteRepoImpl
) : ViewModel() {

    val query = mutableStateOf("")
    fun onQueryChange(newValue: String) = viewModelScope.launch {
        query.value = newValue
    }

    val noteQuantity = mutableStateOf(0)
    fun onNoteQuantityChange(newValue: Int) = viewModelScope.launch {
        noteQuantity.value = newValue
    }

    val noteList = mutableStateOf(UiState())

    init {
        getAllNote()
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepo.deleteNote(note)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        noteRepo.deleteAllNote()
    }


    private fun getAllNote() = viewModelScope.launch {
        noteList.value = UiState(isLoading = true)
        noteRepo.getAllNotes().collect {
            noteList.value = UiState(isLoading = false, notes = it)
        }
    }

    var searchedList = mutableStateOf<List<Note>>(listOf())

    fun searchNote(query: String) = viewModelScope.launch {
        noteRepo.searchNote("%$query%").collect {
            searchedList.value = it
        }
    }

    data class UiState(
        var isLoading: Boolean = false,
        var notes: List<Note> = listOf(),
    )
}