package com.example.bsproject.ui.theme

sealed class Screen(val route : String) {

    object LoginScreen : Screen("login_screen")
    object RegistrationScreen : Screen("registration_screen")
    object CourseListScreen : Screen("course_list_screen")
}