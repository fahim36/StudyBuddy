package com.example.studybuddy

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.StudyBuddyViewModel
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun CourseListScreen(navController: NavHostController, viewModel: StudyBuddyViewModel) {

    val isSuccess = viewModel.isSuccess.observeAsState()
    val courseList = viewModel.courseList.observeAsState()
    if (isSuccess.value == true) {
        Utils.showToast(LocalContext.current, "Post Successfully done")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Course List", style = TextStyle(fontSize = 24.sp))
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Menu Icon",
                modifier = Modifier.clickable {
                })

        }
        courseList.value?.let { CourseList(courses = it) }
    }
    viewModel.getStudyBuddyData()
}

@Composable
fun CourseList(courses: List<Course>) {
    LazyColumn {
        items(courses.size) { index ->
            val course = courses[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { /* Handle course selection */ })
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Column(Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(course.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Members", fontSize = 10.sp)
                    Text(course.users, fontWeight = FontWeight.Bold)
                    Text("Location")
                    Text(course.location, fontWeight = FontWeight.Bold)
                    Text("Time")
                    Text(course.date, fontWeight = FontWeight.Bold)
                    Text("Date")
                    Text(course.date, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
