package com.example.todoapp_compose.ui.screens.edit_note

import android.util.Log.d
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.ui.activity.string
import com.example.todoapp_compose.ui.screens.add_note.validatePriority
import com.example.todoapp_compose.util.MainNavigation
import com.example.todoapp_compose.util.Priority

@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: Int,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val initialNote = Note(null, "", "", Priority.Low, "")
    val note = produceState<Note>(initialValue = initialNote) {
        value = viewModel.getNoteById(noteId)
    }.value
    viewModel.apply {
        onTitleChange(note.title ?: "title not found")
        onDescriptionChange(note.description ?: "Description not found")
    }
    val title by remember {
        viewModel.newTitle
    }
    val desc by remember {
        viewModel.newDesc
    }
    val newPriority by remember {
        viewModel.newPriority
    }
    val newColor by remember {
        viewModel.color
    }

    d("INITTTT", "$note")
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(15.dp))
        ScreenTopSection(navController = navController)
        Spacer(modifier = Modifier.height(15.dp))
        TitleContent()
        Spacer(modifier = Modifier.height(15.dp))
        PriorityChangerSection()
        Spacer(modifier = Modifier.height(10.dp))
        DescriptionSection()
        Spacer(modifier = Modifier.height(10.dp))
        BgColorChooser()
        Spacer(modifier = Modifier.height(25.dp))
        ButtonSection {
            if (!note.title.isNullOrBlank() || !note.description.isNullOrBlank()) {
                viewModel.updateNote(
                    Note(
                        noteId,
                        title,
                        desc,
                        validatePriority(newPriority),
                        newColor
                    )
                )
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun ScreenTopSection(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(start = 10.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Edit Note",
            color = MaterialTheme.colors.onBackground,
            fontSize = 19.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}

@Composable
fun TitleContent(
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val newTitle = remember {
        viewModel.newTitle
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 14.dp),
        value = newTitle.value,
        onValueChange = { viewModel.onTitleChange(it) },
        label = { Text("New Title") },
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
    )
}

@Composable
fun PriorityChangerSection(
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val priorityList = mutableListOf("High", "Medium", "Low")
    var priority by remember {
        viewModel.newPriority
    }
    val ctx = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = ctx.getString(string.select_priority),
            fontSize = 16.sp,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.width(14.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Row(
                Modifier
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = priority,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    priorityList.forEach { item ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            priority = item
                            viewModel.onPriorityChange(item)
                        }) {
                            Text(text = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionSection(
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val newDesc by remember {
        viewModel.newDesc
    }
    OutlinedTextField(
        value = newDesc,
        onValueChange = { viewModel.onDescriptionChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(start = 14.dp, end = 14.dp),
        label = { Text("Description") },
        textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
    )
}

@Composable
fun BgColorChooser(
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val colorList = mutableListOf("Green", "Yellow", "Blue","Gray")
    var color by remember {
        viewModel.color
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = color,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                colorList.forEach { it ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        color = it
                        viewModel.onColorChange(it)
                    }) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonSection(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 14.dp, end = 14.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        Text(
            text = "Save",
            fontSize = 21.sp,
            color = MaterialTheme.colors.onBackground
        )
    }
}