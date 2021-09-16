package com.example.todoapp_compose.ui.screens.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp_compose.entity.Note
import com.example.todoapp_compose.ui.screens.add_note.fromPriority
import com.example.todoapp_compose.util.MainNavigation

@ExperimentalFoundationApi
@Composable
fun NoteCardItem(
    modifier: Modifier = Modifier,
    note: Note,
    navController: NavController
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(5.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(
                Color(android.graphics.Color.parseColor(note.bgColor))
            )
            .clickable {
                navController.navigate("edit_note/${note.id}") {

                }
            }
    ) {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = note.title ?: "No Title",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 17.sp,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(start = 14.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = fromPriority(note.priority), color = MaterialTheme.colors.onBackground,
                    fontSize = 23.sp,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(end = 10.dp, top = 5.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 19.dp)
            ) {
                Text(
                    text = note.description ?: "No Description",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.body1,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp), color = Color.Black
            )
        }
    }
}