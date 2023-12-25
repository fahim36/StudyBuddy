package com.example.studybuddy

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.bsproject.ui.theme.Screen
import com.example.studybuddy.ui.theme.CourseListScreen
import com.google.firebase.auth.FirebaseAuth

@Composable

fun Navigation(navController: NavController, mAuth: FirebaseAuth) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, mAuth)
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController = navController, mAuth)
        }
        composable(route = Screen.CourseListScreen.route) {
            CourseListScreen(navController = navController)
        }
    }
}