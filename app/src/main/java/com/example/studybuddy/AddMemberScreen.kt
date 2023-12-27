package com.example.studybuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberScreen(
    index: Int,
    navController: NavHostController,
    viewModel: StudyBuddyViewModel
) {
    val context = LocalContext.current
    val course = viewModel.courseList.value?.get(index)
    var users by remember { mutableStateOf(course?.users) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Member") },
                Modifier.background(MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(
                        onClick = {
                            if (users?.isEmpty() == true) {
                                Utils.showToast(context, "Users Cant be empty")
                                return@IconButton
                            } else {
                                if (course != null) {
                                    viewModel.postDataToFirebase(
                                        course.title,
                                        course.location,
                                        course.date,
                                        course.time,
                                        users!!
                                    )
                                }
                                Utils.showToast(context, "Successfully done")
                                navController.navigateUp()
                            }
                        },
                        content = { Icon(Icons.Default.Add, contentDescription = "Add") }
                    )
                }

            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(it)
                .background(Color.White, shape = RoundedCornerShape(15.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = users?:"",
                onValueChange = { users = it },
                label = { Text("Users") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black,
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .height(200.dp)
            )
        }
    }
}