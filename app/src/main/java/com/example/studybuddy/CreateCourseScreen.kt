package com.example.studybuddy

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.studybuddy.Utils.convertMillisToDate
import com.example.studybuddy.ui.theme.UIBackground
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourseScreen(
    navController: NavHostController,
    viewModel: StudyBuddyViewModel
) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var users by remember { mutableStateOf("") }
    var isDateVisible by remember { mutableStateOf(false) }
    var isTimeVisible by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()
    val timeState = rememberTimePickerState()
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new course") },
                Modifier.background(MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(
                        onClick = {
                            if (ifFieldsAreEmpty(title, location, date, time, users, context))
                                return@IconButton
                            viewModel.postDataToFirebase(title, location, date, time, users)
                            Utils.showToast(context, "Successfully done")
                            navController.navigateUp()
                        },
                        content = { Icon(Icons.Default.Add, contentDescription = "Add") }
                    )
                }

            )
        }
    ) {
        Column(
            Modifier
                .background(UIBackground)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(it)
                    .background(Color.White, shape = RoundedCornerShape(15.dp)),
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        ),
                    modifier = Modifier.padding(5.dp)
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        ),
                    modifier = Modifier.padding(5.dp)
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        ),
                    readOnly = true,
                    modifier = Modifier
                        .padding(5.dp)
                        .onFocusChanged {
                            if (it.hasFocus)
                                isDateVisible = true
                        }
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        ),
                    modifier = Modifier
                        .padding(5.dp)
                        .onFocusChanged {
                            if (it.hasFocus)
                                isTimeVisible = true
                        }
                )

                OutlinedTextField(
                    value = users,
                    onValueChange = { users = it },
                    label = { Text("Users") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,

                        ),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(100.dp)
                )
            }
        }
    }
    if (isDateVisible) {
        DatePickerDialog(
            onDismissRequest = {
                isDateVisible = false
                focusManager.clearFocus(true)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDateVisible = false
                        isTimeVisible = true
                        dateState.selectedDateMillis?.let { date = convertMillisToDate(it) }
                        focusManager.clearFocus(true)
                        Log.d("TAG", "CreateCourseScreen: ${dateState.selectedDateMillis}")
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        focusManager.clearFocus(true)
                        isDateVisible = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(
                state = dateState,
            )
        }
    }

    if (isTimeVisible) {
        TimePickerDialog(onCancel = {
            focusManager.clearFocus(true)
            isTimeVisible = false
        }, onConfirm = {
            isTimeVisible = false
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
            cal.set(Calendar.MINUTE, timeState.minute)
            cal.isLenient = false
            time = Utils.convertMillisToTime(cal.timeInMillis)
            focusManager.clearFocus(true)

        }) {
            TimePicker(state = timeState,)
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
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