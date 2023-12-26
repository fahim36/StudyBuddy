package com.example.studybuddy

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.studybuddy.ui.theme.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(navController: NavController, mAuth: FirebaseAuth) {
    val viewModel : StudyBuddyViewModel = hiltViewModel()
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, mAuth,viewModel)
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController = navController, mAuth , viewModel)
        }
        composable(route = Screen.CourseListScreen.route) {
            CourseListScreen(navController = navController, viewModel)
        }
    }
}