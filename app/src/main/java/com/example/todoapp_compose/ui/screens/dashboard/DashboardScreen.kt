package com.example.todoapp_compose.ui.screens.dashboard

import android.util.Log.d
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.ui.activity.string
import com.example.todoapp_compose.util.MainNavigation

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DashboardScreen(
    navController: NavController,
    vm: DashBoardViewModel = hiltViewModel()
) {
    val noteList by remember {
        vm.noteList
    }

    val query by remember {
        vm.query
    }
    val searchedList by remember {
        vm.searchedList
    }
    vm.onNoteQuantityChange(noteList.notes.size)
    if (query.isNotBlank()) {
        vm.searchNote(query)
    }
    Scaffold(
        topBar = { TopBar() },
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp)
        ) {
            NoteList(
                noteList = if (query.isEmpty()) noteList.notes else searchedList,
                navController = navController
            )
            FloatingButtonSide(
                navController = navController, modifier = Modifier
                    .align(BottomEnd)
                    .padding(end = 15.dp, bottom = 15.dp)
            )
            if (noteList.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NoteList(
    noteList: List<Note>,
    navController: NavController,
    vm: DashBoardViewModel = hiltViewModel()
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        itemsIndexed(items = noteList) { index, item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        vm.deleteNote(item)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = state, background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.EndToStart -> Color.Red
                        DismissDirection.StartToEnd -> Color.Transparent
                        null -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color)
                            .padding(8.dp)
                            .fillMaxHeight()
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.align(
                                CenterEnd
                            )
                        )
                    }
                },
                dismissContent = {
                    NoteCardItem(
                        note = item,
                        navController = navController
                    )
                },
                directions = setOf(DismissDirection.EndToStart)
            )
            Divider()
        }
    }
}

@Composable
fun FloatingButtonSide(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val ctx = LocalContext.current
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.AddCircle, "") },
        text = { Text(ctx.getString(string.add_new_note)) },
        onClick = {
            navController.navigate(MainNavigation.AddNote.route)
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier
    )
}

@Composable
fun TopBar(
    vm: DashBoardViewModel = hiltViewModel(),
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val query = remember { vm.query }
    val quantity = remember {
        vm.noteQuantity
    }
    val localFocus = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current
    val focus = remember { mutableStateOf(false) }

    TopAppBar(modifier = Modifier.background(Color.Transparent)) {
        Row(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = query.value,
                onValueChange = {
                    vm.onQueryChange(it)
                },
                modifier = Modifier
                    .wrapContentSize()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (focus.value != it.isFocused) {
                            focus.value = it.isFocused
                            if (!it.isFocused) {
                                inputService?.hideSoftwareKeyboard()
                            }
                        }
                    },
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                },
                trailingIcon = {
                    if (focus.value) {
                        IconButton(
                            onClick = {
                                vm.onQueryChange("") // Remove text from TextField when you press the 'X' icon
                                localFocus.clearFocus()
                                if (query.value.isBlank()) localFocus.clearFocus()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = quantity.value.toString(),
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically),
                fontSize = 14.sp,
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                Icons.Filled.StickyNote2,
                contentDescription = "",
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(CenterVertically),
                contentAlignment = TopEnd
            ) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            showMenu = !showMenu
                        }
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(onClick = {
                        vm.deleteAllNotes()
                    }) {
                        Text(
                            text = "Delete all Note",
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }
}