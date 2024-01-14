package com.example.studybuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.ui.theme.Screen


@Composable
fun CourseListScreen(navController: NavHostController, viewModel: StudyBuddyViewModel) {

    val courseList = viewModel.courseList.observeAsState()
    val searchQuery = remember { mutableStateOf("") }
    val filteredCourseList = courseList.value?.filter {
        it.title.contains(searchQuery.value, ignoreCase = true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.course_list),
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(25.dp)
            )
            SearchTextField(searchQuery.value) {
                searchQuery.value = it
            }
//            Icon(
//                Icons.Filled.Menu,
//                contentDescription = "Menu Icon")

        }
        filteredCourseList?.let { CourseList(courses = it, navController) }
    }
    viewModel.getStudyBuddyData()
    if (filteredCourseList != null && filteredCourseList.isEmpty()) NoDataUI()

}

@Composable
fun CourseList(courses: List<Course>, navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreateCourseScreen.route) },
                content = { Icon(Icons.Filled.Add, stringResource(R.string.add)) },
                containerColor = Color.White,
                contentColor = Color.Black
            )
        }
    ) {
        LazyColumn {
            items(courses.size) { index ->
                val course = courses[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate(
                                Screen.AddMemberScreenWithoutParam.route + "/${
                                    courses.indexOf(
                                        course
                                    )
                                }"
                            )
                        })
                        .padding(it)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(course.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(stringResource(R.string.members), fontSize = 10.sp)
                        Text(course.users, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.location))
                        Text(course.location, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.time))
                        Text(course.time, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.date))
                        Text(course.date, fontWeight = FontWeight.Bold)
                        Text(
                            text = stringResource(R.string.created_at), fontSize = 10.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Text(
                            text = Utils.convertMillisToDate(course.createdAt.toLong()).plus(" ")
                                .plus(Utils.convertMillisToTime(course.createdAt.toLong())),
                            fontSize = 10.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoDataUI() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_data_available),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Composable
fun SearchTextField(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(color = Color.Black),
            placeholder = { Text(stringResource(R.string.search)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
            ),
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search),
            tint = Color.Black,
            modifier = Modifier.padding(top = 25.dp, end = 8.dp, start = 5.dp)
        )
    }
}