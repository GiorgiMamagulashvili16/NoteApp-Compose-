package com.example.todoapp_compose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.todoapp_compose.R
import com.example.todoapp_compose.ui.screens.add_note.AddNote
import com.example.todoapp_compose.ui.screens.dashboard.DashboardScreen
import com.example.todoapp_compose.ui.screens.edit_note.EditNoteScreen
import com.example.todoapp_compose.ui.theme.TodoAppComposeTheme
import com.example.todoapp_compose.util.MainNavigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

typealias drawable = R.drawable
typealias string = R.string
typealias font = R.font

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberAnimatedNavController()
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = MainNavigation.Dashboard.route
    ) {
        composable(MainNavigation.Dashboard.route,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(800))
            }) {
            DashboardScreen(navController = navController)
        }
        composable(MainNavigation.AddNote.route,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(800))
            }) {
            AddNote(navController = navController)
        }
        composable(MainNavigation.EditNote.route,
            enterTransition = { _, _ ->
                fadeIn(animationSpec = tween(800))
            },
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )) {
            val noteId = remember {
                it.arguments?.getInt("id")
            }
            if (noteId != null) {
                EditNoteScreen(navController = navController, noteId = noteId)
            }
        }
    }
}

















