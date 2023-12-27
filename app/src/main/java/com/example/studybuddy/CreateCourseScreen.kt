package com.example.studybuddy

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.studybuddy.ui.theme.UIBackground
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourseScreen(
    navController: NavHostController,
    viewModel: StudyBuddyViewModel
) {

    val isSuccess = viewModel.isSuccess.observeAsState()
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var users by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (isSuccess.value == true) {
        Utils.showToast(LocalContext.current, "Post Successfully done")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new course") },
                Modifier.background(MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(
                        onClick = {
                            if(ifFieldsAreEmpty(title,location,date, time,users,context))
                                return@IconButton
                            viewModel.postDataToFirebase(title,location,date, time,users)
                            navController.popBackStack() },
                        content = { Icon(Icons.Default.Add, contentDescription = "Add") }
                    )
                }

            )
        }
    ) {
        Column(Modifier.background(UIBackground).fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(it)
                    .background(Color.White , shape = RoundedCornerShape(15.dp)),
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                    modifier = Modifier.padding(5.dp)
                )
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                    modifier = Modifier.padding(5.dp)
                )
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                    modifier = Modifier.padding(5.dp)
                )

                TextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                    modifier = Modifier.padding(5.dp)
                )

                TextField(
                    value = users,
                    onValueChange = { users = it },
                    label = { Text("Users") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                    ),
                    modifier = Modifier.padding(5.dp).height(100.dp)
                )
            }
        }
    }
}
fun ifFieldsAreEmpty(
    title: String,
    location: String,
    date: String,
    time: String,
    users: String,
    context: Context
): Boolean {
    var isEmpty = false
    if (title.isEmpty()) {
        Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
        isEmpty = true
    } else if (location.isEmpty()) {
        Toast.makeText(context, "Location cannot be empty", Toast.LENGTH_SHORT).show()
        isEmpty = true
    } else if (date.isEmpty()) {
        Toast.makeText(context, "Date cannot be empty", Toast.LENGTH_SHORT).show()
        isEmpty = true
    } else if (time.isEmpty()) {
        Toast.makeText(context, "Time cannot be empty", Toast.LENGTH_SHORT).show()
        isEmpty = true
    } else if (users.isEmpty()) {
        Toast.makeText(context, "Users cannot be empty", Toast.LENGTH_SHORT).show()
        isEmpty = true
    }

    return isEmpty
}