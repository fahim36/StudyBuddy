package com.example.studybuddy.ui.theme

sealed class Screen(val route : String) {

    object LoginScreen : Screen("login_screen")
    object RegistrationScreen : Screen("registration_screen")
    object CourseListScreen : Screen("course_list_screen")
    object CreateCourseScreen : Screen("create_course_screen")
}