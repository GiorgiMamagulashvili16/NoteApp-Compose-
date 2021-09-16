package com.example.todoapp_compose.ui.screens.add_note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.ui.activity.string
import com.example.todoapp_compose.util.MainNavigation

@Composable
fun AddNote(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenTopSection(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            navController = navController
        )
    }
}

@Composable
fun ScreenTopSection(
    modifier: Modifier = Modifier,
    vm: AddNoteViewModel = hiltViewModel(),
    navController: NavController
) {
    val ctx = LocalContext.current
    val title = remember { vm.title }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            modifier = Modifier
                .wrapContentSize()
                .align(CenterHorizontally),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 21.sp
                    )
                ) {
                    append("HI!")
                }
                append(" Add New Note")
            },
            fontSize = 18.sp,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,

            )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp),
            value = title.value,
            onValueChange = { vm.onTitleChange(it) },
            label = { Text("Title") },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
        )
        Spacer(modifier = Modifier.height(10.dp))

        var expanded by remember {
            mutableStateOf(false)
        }
        val priorityList = mutableListOf("High", "Medium", "Low")
        var priority by remember {
            mutableStateOf(priorityList[0])
        }
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
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = TopStart) {
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
                            }) {
                                Text(text = item)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(13.dp))
        ScreenMainSection(priority = priority, title = title.value, navController = navController)
    }
}

@Composable
fun ScreenMainSection(
    modifier: Modifier = Modifier,
    priority: String,
    title: String,
    vm: AddNoteViewModel = hiltViewModel(),
    navController: NavController
) {
    val description = remember {
        vm.description
    }
    val ctx = LocalContext.current
    val isLoading = remember {
        vm.isLoading
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val colorList = mutableListOf("Green", "Yellow", "Blue", "Gray")
    var color by remember {
        mutableStateOf(colorList[0])
    }
    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = description.value,
            onValueChange = { vm.onDescChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(start = 14.dp, end = 14.dp),
            label = { Text("Description") },
            textStyle = TextStyle(color = MaterialTheme.colors.onBackground)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.12f),
            contentAlignment = Center
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
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },) {
                    colorList.forEach { it ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            color = it
                        }) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        val note =
            Note(
                title = title,
                description = description.value,
                priority = validatePriority(priority),
                bgColor = validateColor(color)
            )
        Button(
            onClick = {
                if (!note.title.isNullOrBlank() || !note.description.isNullOrBlank()) {
                    vm.addNote(note)
                }
                navController.navigate(MainNavigation.Dashboard.route) {
                    popUpTo(MainNavigation.AddNote.route) {
                        inclusive = true
                    }
                }
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
                text = ctx.getString(string.add),
                fontSize = 21.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (isLoading.value) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                CircularProgressIndicator()
            }
        }
        if (note.title.isNullOrBlank() || note.description.isNullOrBlank()) {
            Text(
                text = "Please fill All Field",
                color = Color.Red,
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }
}